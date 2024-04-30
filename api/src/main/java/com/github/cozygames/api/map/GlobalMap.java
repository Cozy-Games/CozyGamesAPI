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
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.arena.GlobalArena;
import com.github.cozygames.api.event.internal.map.MapCreateArenaEvent;
import com.github.cozygames.api.event.internal.map.MapLocalDeleteEvent;
import com.github.cozygames.api.event.internal.map.MapLocalSaveEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a global map.
 * <p>
 * This map class is used to represent a map
 * that could be registered on a different server.
 * <p>
 * This uses kerb events when you call a method within this
 * class, and it will find the locally registered plugin with
 * the same map to call the related method.
 */
public class GlobalMap extends Map<GlobalMap> {

    /**
     * Used to create a new global map instance.
     *
     * @param name           The name of the map.
     * @param serverName     The server the map was registered on and can be created on.
     * @param gameIdentifier The game identifier that represents a game this map is used for.
     */
    public GlobalMap(@NotNull String name, @NotNull String serverName, @NotNull String gameIdentifier) {
        super(name, serverName, gameIdentifier);
    }

    @Override
    public @NotNull CozyGames getApi() {
        return CozyGamesProvider.get();
    }

    @Override
    public @NotNull Arena<?, GlobalMap> createArena() {
        final String worldName = "ToDo";
        this.getApi().callEvent(new MapCreateArenaEvent(this.getIdentifier(), worldName));
        return new GlobalArena(this.getIdentifier(), worldName);
    }

    @Override
    public @NotNull GlobalMap saveToLocalConfiguration() {
        this.getApi().callEvent(new MapLocalSaveEvent(this));
        return this;
    }

    @Override
    public @NotNull GlobalMap deleteFromLocalConfiguration() {
        this.getApi().callEvent(new MapLocalDeleteEvent(this.getIdentifier()));
        return this;
    }
}
