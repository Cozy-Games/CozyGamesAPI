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

package com.github.cozygames.bukkit.arena;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.bukkit.map.ExampleMap;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ExampleArena extends Arena<ExampleArena, ExampleMap> {

    /**
     * Used to create a new arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public ExampleArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    @Override
    public @NotNull CozyGames getAPI() {
        return null;
    }

    @Override
    public @NotNull Optional<ExampleMap> getMap() {
        return Optional.empty();
    }

    @Override
    public void activate(@NotNull String groupIdentifier) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void remove() {

    }
}
