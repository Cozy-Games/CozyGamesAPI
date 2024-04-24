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

package com.github.cozygames.api.map;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.arena.Arena;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a simple map where all the
 * variables and methods will not change.
 * <p>
 * This will be implemented by the {@link Map} class.
 *
 * @param <T> The top immutable map class.
 */
public abstract class ImmutableMap<T extends ImmutableMap<T>> {

    private final @NotNull String name;
    private final @NotNull String serverName;
    private final @NotNull String gameIdentifier;

    /**
     * Used to create a new instance of a unchangeable map.
     * <p>
     * A map can be used to create a new arena.
     *
     * @param name           The map's name.
     * @param serverName     The server the arena can be initialized on.
     * @param gameIdentifier The game identifier.
     */
    public ImmutableMap(@NotNull String name,
                        @NotNull String serverName,
                        @NotNull String gameIdentifier) {

        this.name = name;
        this.serverName = serverName;
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * Used to get the instance of the cozy games api.
     * <p>
     * This should be used within the map class so the
     * service manager on some platforms can be used.
     *
     * @return The cozy games api instance.
     */
    public abstract @NotNull CozyGames getAPI();

    /**
     * The base method used to activate the map.
     * <p>
     * This will create a session for the group of players and begin a game.
     * <p>
     * This will ignore if it should create a new game. Checking if a game
     * should begin should be checked before calling this method.
     *
     * @param groupIdentifier The group's identifier.
     * @return This instance.
     */
    public abstract @NotNull Arena<T> createArena(@NotNull String groupIdentifier);

    /**
     * The map's unique identifier.
     * <p>
     * To create this identifier the server name, game identifier
     * and map name is combined and split by colons.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium
     * }</pre>
     *
     * @return The arena's identifier.
     */
    public @NotNull String getIdentifier() {
        return Map.getIdentifier(this.serverName, this.gameIdentifier, this.name);
    }

    /**
     * The map's unique name.
     * <p>
     * The name is not unique between game types and servers.
     * To obtain a truly unique key, use the
     * {@link Map#getIdentifier()} method.
     *
     * @return The map's name.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * The server the map will be located on.
     *
     * @return The server's name.
     */
    public @NotNull String getServerName() {
        return this.serverName;
    }

    /**
     * The type of game that will be played
     * when the map is created.
     *
     * @return The game identifier.
     */
    public @NotNull String getGameIdentifier() {
        return this.gameIdentifier;
    }
}
