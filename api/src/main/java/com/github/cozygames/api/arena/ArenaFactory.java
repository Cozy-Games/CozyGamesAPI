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

import com.github.cozygames.api.configuration.ArenaConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * The empty arena factory.
 * <p>
 * Used by the {@link ArenaConfiguration} to create an empty
 * arena instance. When the {@link ArenaConfiguration#getType(String)}
 * method is called, an empty instance is created and then converted
 * to obtain the type requested.
 *
 * @param <A> The arena that will be created.
 * @param <M> The map that is used within the arena.
 */
public interface ArenaFactory<A extends Arena<A, M>, M extends Map<M>> {

    /**
     * Used to create a new empty instance of the arena.
     *
     * @param identifier The arena's identifier.
     * @return The empty arena instance.
     */
    @NotNull
    A create(@NotNull String identifier);
}
