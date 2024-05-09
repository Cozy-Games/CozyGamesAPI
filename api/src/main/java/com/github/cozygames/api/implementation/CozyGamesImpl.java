/*
 * CozyGamesAPI - The api used to interface with the cozy game system.
 * Copyright (C) 2024 Smuddgge
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.cozygames.api.implementation;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.arena.ArenaManager;
import com.github.cozygames.api.console.Logger;
import com.github.cozygames.api.database.table.ArenaTable;
import com.github.cozygames.api.database.table.GroupTable;
import com.github.cozygames.api.database.table.MapTable;
import com.github.cozygames.api.database.table.MemberTable;
import com.github.cozygames.api.group.GroupManager;
import com.github.cozygames.api.map.MapManager;
import com.github.cozygames.api.member.Member;
import com.github.cozygames.api.member.MemberNotFoundException;
import com.github.cozygames.api.plugin.CozyGamesAPIPlugin;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.kerbity.kerb.client.KerbClient;
import com.github.kerbity.kerb.packet.event.Event;
import com.github.kerbity.kerb.packet.event.Priority;
import com.github.kerbity.kerb.result.CompletableResultSet;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.DatabaseBuilder;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a simple implementation of the cozy games api.
 * <p>
 * When initialized, this class will create an instance of
 * the connection configuration. This is used to set up the
 * kerb client and the database connection.
 * <p>
 * The instance of the cozy games plugin is provided to get
 * important plugin infomation to set up the api instance.
 * <p>
 * To create an instance of this implementation, use
 * the {@link CozyGamesBuilder} within this maven project.
 * <pre>{@code
 * CozyGames cozyGames = new CozyGamesBuilder(plugin).build();
 * }</pre>
 */
@ApiStatus.Internal
public class CozyGamesImpl implements CozyGames {

    private final @NotNull CozyGamesAPIPlugin plugin;
    private final @NotNull Logger logger;

    private final @NotNull Configuration connectionConfig;
    private final @NotNull Database database;
    private final @NotNull KerbClient kerb;
    private final @NotNull MapManager mapManager;
    private final @NotNull ArenaManager arenaManager;
    private final @NotNull GroupManager groupManager;

    private final @NotNull List<CozyGamesPlugin<?, ?, ?, ?>> localPluginList;

    /**
     * Used to create a new instance of the
     * cozy games implementation within this maven project.
     *
     * @param plugin The instance of the cozy games api plugin.
     */
    @ApiStatus.Internal
    public CozyGamesImpl(@NotNull CozyGamesAPIPlugin plugin, boolean debugMode) {
        this.plugin = plugin;
        this.logger = this.plugin.getLogger().duplicate().setDebugMode(debugMode);

        // Log start up.
        this.logger.log("Starting api setup.");

        // Create connection configuration instance.
        this.logger.debug("Setting up connection configuration.");
        this.connectionConfig = new YamlConfiguration(this.plugin.getDataFolder(), "connection.yaml");
        this.connectionConfig.setDefaultPath("connection.yaml");
        this.connectionConfig.load();
        this.logger.log("Completed setting up connection configuration.");

        // Create database connection.
        this.logger.debug("Setting up database.");
        this.database = new DatabaseBuilder(
                this.connectionConfig.getSection("database"),
                this.plugin.getDataFolder().getAbsolutePath()
        ).build();

        // Create the database tables.
        this.logger.debug("Creating database tables.");
        this.database.createTable(new ArenaTable());
        this.database.createTable(new GroupTable());
        this.database.createTable(new MapTable());
        this.database.createTable(new MemberTable());
        this.logger.log("Completed setting up database.");

        // Create kerb connection.
        this.logger.debug("Setting up kerb client.");
        this.kerb = new KerbClient(
                this.connectionConfig.getString("server_name"),
                this.connectionConfig.getInteger("kerb.server_port"),
                this.connectionConfig.getString("kerb.server_address"),
                new File(this.connectionConfig.getString("kerb.client_certificate_path")),
                new File(this.connectionConfig.getString("kerb.server_certificate_path")),
                this.connectionConfig.getString("kerb.password"),
                Duration.ofMillis(this.connectionConfig.getInteger("kerb.max_wait_time_millis")),
                this.connectionConfig.getBoolean("kerb.auto_reconnect"),
                Duration.ofMillis(this.connectionConfig.getInteger("kerb.reconnect_cooldown_millis")),
                this.connectionConfig.getInteger("kerb.max_reconnection_attempts")
        );

        // Attempt to connect to the kerb server.
        // If unable to check and attempt to reconnect.
        this.logger.debug("Connecting to kerb server.");
        if (!this.kerb.connect()) this.kerb.checkAndAttemptToReconnect();

        // Register cozy games internal listener.
        this.logger.debug("Registering internal kerb listeners.");
        this.kerb.registerListener(Priority.HIGH, new CozyGamesInternalListener(this));
        this.logger.log("Completed setting up kerb.");

        // Create the map manager.
        this.mapManager = new MapManager(this);
        this.logger.debug("Completed setting up map manager.");

        // Create the arena manager.
        this.arenaManager = new ArenaManager(this);
        this.logger.debug("Completed setting up arena manager.");

        // Create the group manager.
        this.groupManager = new GroupManager(this);
        this.logger.debug("Completed setting up group manager.");

        // Initialize the local plugin list.
        this.localPluginList = new ArrayList<>();

        // Register this instance in the singleton provider.
        CozyGamesProvider.register(this);
        this.logger.debug("Registered static instance with api provider.");

        // Log finished message.
        this.logger.log("Finished api setup.");

        // Log header.
        // Header is logged here because of potential other process spam.
        this.logHeader();
    }

    private void logHeader() {
        this.logger.log("&7");
        this.logger.log("&a    ____                ____");
        this.logger.log("&a   / ___|___ _____   _ / ___| __ _ _ __ ___   ___  ___");
        this.logger.log("&a  | |   / _ \\_  / | | | |  _ / _` | '_ ` _ \\ / _ \\/ __|");
        this.logger.log("&a  | |__| (_) / /| |_| | |_| | (_| | | | | | |  __/\\__ \\");
        this.logger.log("&a   \\____\\___/___|\\__, |\\____|\\__,_|_| |_| |_|\\___||___/");
        this.logger.log("&a                 |___/");
        this.logger.log("&7           By Smudge      &7Api Version &e" + this.getVersion());
        this.logger.log("&7");
        this.logger.log("&7debug_mode: &a" + this.logger.getDebugMode());
        this.logger.log("&7server_name: &a" + this.getServerName());
        this.logger.log("&7server_address: &a" + this.getServerAddress());
        this.logger.log("&7");
    }

    @Override
    public @NotNull String getVersion() {
        final String version = CozyGamesImpl.class.getPackage().getImplementationVersion();
        if (version == null) {
            this.logger.warn("&eUnable to find api version! Jar doesnt contain default implementation entries.");
            this.logger.warn("&7");
            this.logger.warn("&eDevelopers:");
            this.logger.warn("&eIf using maven try adding the default implementation entries with the maven-jar-plugin.");
            this.logger.warn("&eAn example of this is shown in the CozyGamesAPI-Bukkit version.");
            return "null";
        }
        return version;
    }

    @Override
    public @NotNull Logger getLogger() {
        return this.logger;
    }

    @Override
    public @NotNull String getServerName() {
        final String serverName = this.connectionConfig.getString("server_name");

        // Check if the server name is null.
        if (serverName == null) {
            throw new RuntimeException("Server name is not defined in connection.yaml");
        }

        return serverName;
    }

    @Override
    public @NotNull String getServerAddress() {
        final String serverAddress = this.connectionConfig.getString("server_address");

        // Check if the server name is null.
        if (serverAddress == null) {
            throw new RuntimeException("Server address is not defined in connection.yaml");
        }

        return serverAddress;
    }

    @Override
    public @NotNull CozyGamesAPIPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public @NotNull List<CozyGamesPlugin<?, ?, ?, ?>> getLocalPlugins() {
        return this.localPluginList;
    }

    @Override
    public @NotNull CozyGames registerLocalPlugin(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        this.localPluginList.add(plugin);
        return this;
    }

    @Override
    public @NotNull CozyGames unregisterLocalPlugin(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        this.localPluginList.remove(plugin);
        return this;
    }

    @Override
    public @NotNull Configuration getConnectionConfig() {
        return this.connectionConfig;
    }

    @Override
    public @NotNull Database getDatabase() {
        return this.database;
    }

    @Override
    public @NotNull KerbClient getKerbClient() {
        return this.kerb;
    }

    public @NotNull <E extends Event> CompletableResultSet<E> callEvent(E event) {
        return this.kerb.callEvent(event);
    }

    @Override
    public @NotNull MapManager getMapManager() {
        return this.mapManager;
    }

    @Override
    public @NotNull ArenaManager getArenaManager() {
        return this.arenaManager;
    }

    @Override
    public @NotNull GroupManager getGroupManager() {
        return this.groupManager;
    }

    @Override
    public @NotNull Member getMember(@NotNull UUID playerUuid) {

        // The optional player's name.
        final String playerName = this.getPlugin()
                .getPlayerName(playerUuid)
                .orElse(null);

        // Check if the players name was found.
        if (playerName != null) {
            return new Member(playerUuid, playerName);
        }

        // Attempt to get the members record.
        Member member = this.database
                .getTable(MemberTable.class)
                .getMember(playerUuid)
                .orElse(null);

        // Check if the member is null.
        if (member == null) {
            throw new MemberNotFoundException("Could not find player's uuid.");
        }

        return member;
    }

    @Override
    public @NotNull Member getMember(@NotNull String playerName) {

        // The optional player's name.
        final UUID playerUuid = this.getPlugin()
                .getPlayerUuid(playerName)
                .orElse(null);

        // Check if the players name was found.
        if (playerUuid != null) {
            return new Member(playerUuid, playerName);
        }

        // Attempt to get the members record.
        Member member = this.database
                .getTable(MemberTable.class)
                .getMember(playerName)
                .orElse(null);

        // Check if the member is null.
        if (member == null) {
            throw new MemberNotFoundException("Could not find player's name.");
        }

        return member;
    }
}
