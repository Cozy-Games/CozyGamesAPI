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

package com.github.cozygames.api.event.internal.map;

import com.github.cozygames.api.map.GlobalMap;
import com.github.cozygames.api.map.LocalMap;
import com.github.cozygames.api.map.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the map create arena event.
 * <p>
 * This is called when the {@link GlobalMap#createArena()}
 * method is called.
 * <p>
 * The server with the instance of the local
 * map should call {@link LocalMap#createArena()}.
 */
public class MapCreateArenaEvent extends MapEvent {

    private boolean isComplete;

    private final @NotNull String mapIdentifier;
    private final @NotNull String worldName;

    /**
     * Used to create a new map create arena event.
     *
     * @param mapIdentifier The map's identifier.
     */
    public MapCreateArenaEvent(@NotNull String mapIdentifier, @NotNull String worldName) {
        this.mapIdentifier = mapIdentifier;
        this.worldName = worldName;
    }

    @Override
    public @NotNull String getMapIdentifier() {
        return this.mapIdentifier;
    }

    @Override
    public @NotNull MapEvent executeMethod(@NotNull Map<?> map) {
        map.createArena();
        return this;
    }

    public @NotNull String getWorldName() {
        return this.worldName;
    }
}
