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

package com.github.cozygames.api.arena;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.map.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Represents an unchangeable arena.
 * <p>
 * A map that has been loaded into a world.
 *
 * @param <A> The top arena.
 * @param <M> The map type using in this arena.
 */
public abstract class ImmutableArena<A extends ImmutableArena<A, M>, M extends ImmutableMap<M>> {

    private final @NotNull String mapIdentifier;
    private final @NotNull String worldName;

    /**
     * Used to create a new arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public ImmutableArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        this.mapIdentifier = mapIdentifier;
        this.worldName = worldName;
    }

    /**
     * Used to get the instance of the api to use.
     * <p>
     * This should be used in case there is a service
     * provider on the platform.
     * <p>
     * Mainly used in the {@link Arena#save()} method.
     *
     * @return The instance of the api.
     */
    public abstract @NotNull CozyGames getAPI();

    /**
     * Used to get the instance of the map.
     * <p>
     * This uses the storage in case the map is updated.
     *
     * @return The optional instance of the map.
     * @throws NoSuchElementException When the arena no longer exists.
     */
    public abstract @NotNull M getMap();

    /**
     * Used to create the world if it doesn't
     * exist on the server.
     *
     * @return This instance.
     */
    public abstract @NotNull A createWorld();

    /**
     * Used to delete the world if it exists
     * on the server.
     *
     * @return This instance.
     */
    public abstract @NotNull A deleteWorld();

    /**
     * Used to activate the arena and begin a game session.
     *
     * @param groupIdentifier The group identifier that will be
     *                        playing the game.
     */
    public abstract void activate(@NotNull UUID groupIdentifier);

    /**
     * Used to deactivate the arena.
     * <p>
     * This will stop any game session.
     */
    public abstract void deactivate();

    /**
     * Used to remove the arena from the cozy game
     * system.
     * <p>
     * This should also delete the world the arena
     * is located in.
     * <p>
     * This method should be used instead of the
     * {@link Arena#delete()} method.
     */
    public abstract void remove();

    /**
     * Used to get the arena's unique identifier.
     * <p>
     * Created using the map identifier and world
     * name seperated with colons.
     * <p>
     * You can create this using the method
     * {@link Arena#getIdentifier(String, String)}.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium:world_bedwars_aquarium_1
     * }</pre>
     *
     * @return The arena's identifier.
     */
    public @NotNull String getIdentifier() {
        return Arena.getIdentifier(this.getMapIdentifier(), this.getWorldName());
    }

    public @NotNull String getMapIdentifier() {
        return this.mapIdentifier;
    }

    public @NotNull String getWorldName() {
        return this.worldName;
    }
}
