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

package com.github.cozygames.api;

import com.github.cozygames.api.member.Member;
import com.github.cozygames.api.member.MemberNotFoundException;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.kerbity.kerb.client.KerbClient;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The CozyGames API.
 * <p>
 * The API allows other plugins on the server to interact
 * with the mini-game system.
 * <p>
 * This interface is the base of the API package.
 * <p>
 * <h1>Implementation</h1>
 * <p>
 * If you're on a platform that has a service provider:
 * <pre>{@code
 * RegisteredServiceProvider<CozyGames> provider = Bukkit
 *         .getServicesManager()
 *         .getRegistration(CozyGames.class);
 *
 * if (provider != null) {
 *     CozyGames api = provider.getProvider();
 * }
 * }</pre>
 * <p>
 * Otherwise, you can obtain a static instance from the {@link CozyGamesProvider}.
 * <pre>{@code
 * CozyGames api = CozyGamesProvider.get();
 * }</pre>
 */
public interface CozyGames {

    /**
     * The name of the server the plugin is located on.
     * <p>
     * Found in the plugin's configuration file, this
     * name should match the name used in the {@link KerbClient#getName()}.
     * <p>
     * This may not match the actual servers name, but instead used
     * as the name for identifying servers in {@link KerbClient}
     * events and in the database.
     * <p>
     *
     * @return The name of the server.
     */
    @NotNull
    String getServerName();

    /**
     * Used to get the instance of the cozy game api plugin.
     * <p>
     * Where this isn't always an extension of the plugin class.
     * This interface contains infomation that is needed about
     * the specific api plugin.
     *
     * @return The api plugin.
     */
    @NotNull
    CozyGamesPlugin getPlugin();

    /**
     * Used to get the instance of the connection configuration file.
     * <p>
     * This configuration file contains infomation used to connect to
     * the kerb server and set up the database.
     *
     * @return The instance of the connection config.
     */
    @NotNull
    Configuration getConnectionConfig();

    /**
     * Used to get the instance of the cozy game's database.
     * <p>
     * The database is used to store global infomation about
     * the game's system.
     *
     * @return The instance of the database.
     */
    @NotNull
    Database getDatabase();

    /**
     * Used to get the instance of a member
     * given the player's uuid.
     * <p>
     * A member is a player adapter for the
     * cozy game's system.
     *
     * @param playerUuid The player's uuid.
     * @return The instance of the member.
     * @throws MemberNotFoundException Thrown when the member could not be
     *                                 found based on their player uuid.
     */
    @NotNull
    Member getMember(@NotNull UUID playerUuid);

    /**
     * Used to get the instance of a member
     * given the player's name.
     * <p>
     * A member is a player adapter for the
     * cozy game's system.
     *
     * @param playerName The player's name.
     * @return The instance of the member.
     * @throws MemberNotFoundException Thrown when the member could not be
     *                                 found based on their player name.
     */
    @NotNull
    Member getMember(@NotNull String playerName);
}