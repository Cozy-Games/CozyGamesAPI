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

import java.util.Optional;

public class GlobalArena extends Arena<GlobalArena, GlobalMap>  {

    /**
     * Used to create a new arena.
     *
     * @param mapIdentifier   The map's identifier.
     * @param groupIdentifier The group that is using the arena.
     */
    public GlobalArena(@NotNull String mapIdentifier, @NotNull String groupIdentifier) {
        super(mapIdentifier, groupIdentifier);
    }

    @Override
    public @NotNull CozyGames getAPI() {
        return CozyGamesProvider.get();
    }

    @Override
    public @NotNull Optional<GlobalMap> getMap() {
        return this.getAPI().getMapManager().getGlobalMap(this.getMapIdentifier());
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
