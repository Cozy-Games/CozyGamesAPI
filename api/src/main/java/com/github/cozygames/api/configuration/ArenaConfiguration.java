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

package com.github.cozygames.api.configuration;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.arena.ArenaFactory;
import com.github.cozygames.api.map.Map;
import com.github.smuddgge.squishyconfiguration.directory.SingleTypeConfigurationDirectory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the arena configuration directory.
 * <p>
 * Used to store specific arena type's locally.
 *
 * @param <A> The arena class.
 * @param <M> The map class the arena uses.
 */
public class ArenaConfiguration<A extends Arena<A, M>, M extends Map<M>> extends SingleTypeConfigurationDirectory<A> {

    private final @NotNull ArenaFactory<A, M> arenaFactory;

    /**
     * Used to create a new map configuration directory.
     *
     * @param resourceClass The class used to load the resource files.
     * @param arenaFactory  The arena factory.
     */
    public ArenaConfiguration(@NotNull Class<?> resourceClass, @NotNull ArenaFactory<A, M> arenaFactory) {
        super("maps", resourceClass);

        this.arenaFactory = arenaFactory;
    }

    @Override
    public @NotNull A createEmpty(@NotNull String identifier) {
        return arenaFactory.create(identifier);
    }
}