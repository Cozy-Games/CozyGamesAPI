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
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.map.GlobalMap;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a global arena.
 * <p>
 * This arena class is used to represents an arena
 * that could be on a different server.
 * <p>
 * Using kerb events when you call a method within this
 * class, and it will find the locally registered server and
 * call the same method.
 */
public class GlobalArena extends ImmutableArena<GlobalArena, GlobalMap> {

    /**
     * Used to create a new global arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public GlobalArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    /**
     * Used to create a new global arena.
     *
     * @param identifier The arena's identifier. This can be provided by
     *                   the {@link Arena#getIdentifier(String, String)} method.
     */
    public GlobalArena(@NotNull String identifier) {
        super(identifier);
    }

    @Override
    public @NotNull CozyGames getAPI() {
        return CozyGamesProvider.get();
    }

    @Override
    public @NotNull GlobalMap getMap() {
        return this.getAPI().getMapManager().getGlobalMap(this.getMapIdentifier()).orElseThrow();
    }

    @Override
    public @NotNull GlobalArena createWorld() {
        return this;
    }

    @Override
    public @NotNull GlobalArena deleteWorld() {
        return this;
    }

    @Override
    public void activate(@NotNull UUID groupIdentifier) {

    }

    @Override
    public void deactivate() {
    }

    @Override
    public void remove() {

    }
}
