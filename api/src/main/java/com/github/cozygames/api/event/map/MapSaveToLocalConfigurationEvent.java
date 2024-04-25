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

import com.github.cozygames.api.map.GlobalMap;
import com.github.kerbity.kerb.packet.event.Event;
import org.jetbrains.annotations.NotNull;

public class MapSaveToLocalConfigurationEvent extends Event implements MapEvent {

    private final @NotNull String mapIdentifier;
    private final @NotNull GlobalMap instance;

    /**
     * Used to create a new map save to local configuration event.
     *
     * @param instance The instance of the global map.
     */
    public MapSaveToLocalConfigurationEvent(@NotNull GlobalMap instance) {
        this.mapIdentifier = instance.getIdentifier();
        this.instance = instance;
    }

    @Override
    public @NotNull String getMapIdentifier() {
        return this.mapIdentifier;
    }

    public @NotNull GlobalMap getInstance() {
        return this.instance;
    }
}
