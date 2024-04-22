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
import com.github.cozygames.api.database.table.MemberTable;
import com.github.cozygames.api.member.Member;
import com.github.cozygames.api.member.MemberNotFoundException;
import com.github.cozygames.api.plugin.CozyGamesAPIPlugin;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.DatabaseBuilder;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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

    private final @NotNull Configuration connectionConfig;
    private final @NotNull Database database;

    /**
     * Used to create a new instance of the
     * cozy games implementation within this maven project.
     *
     * @param plugin The instance of the cozy games api plugin.
     */
    @ApiStatus.Internal
    public CozyGamesImpl(@NotNull CozyGamesAPIPlugin plugin) {
        this.plugin = plugin;

        // Create connection configuration instance.
        this.connectionConfig = new YamlConfiguration(this.plugin.getDataFolder(), "connection.yaml");
        this.connectionConfig.setDefaultPath("connection.yaml");
        this.connectionConfig.load();

        // Create database connection.
        this.database = new DatabaseBuilder(
                this.connectionConfig.getSection("database"),
                this.plugin.getDataFolder().getAbsolutePath()
        ).build();

        // Register this instance in the singleton provider.
        CozyGamesProvider.register(this);
    }

    @Override
    public @NotNull String getServerName() {
        final String serverName = this.connectionConfig.getString("server");

        // Check if the server name is null.
        if (serverName == null) {
            throw new RuntimeException("Server name is not defined in connection.yaml");
        }

        return serverName;
    }

    @Override
    public @NotNull CozyGamesAPIPlugin getPlugin() {
        return this.plugin;
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
