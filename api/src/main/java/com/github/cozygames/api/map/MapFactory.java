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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a map factory.
 *
 * @param <M> The map that will be created.
 */
public interface MapFactory<M extends Map<M>> {

    /**
     * Used to create a new empty instance of a map.
     *
     * @param name The name of the map to create.
     * @return The empty map instance.
     */
    @NotNull
    M create(@NotNull String name);
}
