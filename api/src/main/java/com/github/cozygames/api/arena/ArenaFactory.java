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

import com.github.cozygames.api.map.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a map factory.
 *
 * @param <M> The map that will be created.
 */
public interface ArenaFactory<A extends Arena<A, M>, M extends Map<M>> {

    /**
     * Used to create a new empty instance of a map.
     *
     * @param identifier The name of the map to create.
     * @return The empty map instance.
     */
    @NotNull
    A create(@NotNull String identifier);
}
