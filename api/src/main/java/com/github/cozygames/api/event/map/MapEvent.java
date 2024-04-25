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

package com.github.cozygames.api.event.map;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a map event.
 * <p>
 * All map events should implement this interface.
 */
public interface MapEvent {

    /**
     * Used to get the maps identifier which can
     * be used for retrieving the map.
     *
     * @return The map identifier.
     */
    @NotNull
    String getMapIdentifier();
}
