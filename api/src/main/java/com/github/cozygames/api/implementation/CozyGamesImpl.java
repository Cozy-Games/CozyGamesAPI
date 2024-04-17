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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.DatabaseBuilder;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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
public class CozyGamesImpl implements CozyGames {

    private final @NotNull CozyGamesPlugin plugin;

    private final @NotNull Configuration connectionConfig;
    private final @NotNull Database database;

    /**
     * Used to create a new instance of the
     * cozy games implementation within this maven project.
     *
     * @param plugin The instance of the cozy games api plugin.
     */
    @ApiStatus.Internal
    public CozyGamesImpl(@NotNull CozyGamesPlugin plugin) {
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
    public @NotNull CozyGamesPlugin getPlugin() {
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
}
