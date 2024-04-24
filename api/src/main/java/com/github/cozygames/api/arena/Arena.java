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
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.map.ImmutableMap;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents an arena.
 * <p>
 * A map that has been loaded into a world.
 *
 * @param <A> The top arena.
 * @param <M> The map type.
 */
public abstract class Arena<A extends Arena<A, M>, M extends ImmutableMap<M>> implements ConfigurationConvertable<A>, Savable<A> {

    private final @NotNull String mapIdentifier;
    private final @NotNull String worldName;

    /**
     * Used to create a new arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public Arena(@NotNull String mapIdentifier, @NotNull String worldName) {
        this.mapIdentifier = mapIdentifier;
        this.worldName = worldName;
    }

    /**
     * Used to get the instance of the api to use.
     * <p>
     * This should be used in case there is a service
     * provider on the platform.
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
     */
    public abstract @NotNull Optional<M> getMap();

    /**
     * Used to activate the arena and begin a game session.
     *
     * @param groupIdentifier The group identifier that will be playing the game.
     */
    public abstract void activate(@NotNull String groupIdentifier);

    /**
     * Used to deactivate the arena.
     * <p>
     * This will stop any game session.
     */
    public abstract void deactivate();

    /**
     * Used to remove the arena from the cozy game system.
     * <p>
     * This should also delete the world the arena is located in.
     */
    public abstract void remove();

    public @NotNull String getMapIdentifier() {
        return this.mapIdentifier;
    }

    public @NotNull String getWorldName() {
        return this.worldName;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        return null;
    }

    @Override
    public @NotNull A convert(@NotNull ConfigurationSection configurationSection) {
        return null;
    }

    @Override
    public @NotNull A save() {
        return null;
    }
}
