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
import com.github.cozygames.api.arena.Arena;
import org.jetbrains.annotations.NotNull;

public class LocalMap<M extends Map<M>> extends Map<M> {
    /**
     * Used to create a new local map instance.
     *
     * @param name           The name of the map.
     * @param serverName     The server the map was registered on and can be created on.
     * @param gameIdentifier The game identifier that represents a game this map is used for.
     */
    public LocalMap(@NotNull String name, @NotNull String serverName, @NotNull String gameIdentifier) {
        super(name, serverName, gameIdentifier);
    }

    @Override
    public @NotNull CozyGames getApi() {
        return null;
    }

    @Override
    public @NotNull Arena<?, M> createArena() {
        return null;
    }

    @Override
    public @NotNull M saveToLocalConfiguration() {
        return null;
    }

    @Override
    public @NotNull M deleteFromLocalConfiguration() {
        return null;
    }
}
