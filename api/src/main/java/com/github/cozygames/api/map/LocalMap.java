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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class LocalMap<A extends Arena<A, M>, M extends Map<M>> extends Map<M> {

    /**
     * Used to create a new instance of a map.
     * <p>
     * A map can be used to create a new arena.
     *
     * @param name           The map's name.
     * @param serverName     The server the arena can be initialized on.
     * @param gameIdentifier The game identifier.
     */
    public LocalMap(@NotNull String name, @NotNull String serverName, @NotNull String gameIdentifier) {
        super(name, serverName, gameIdentifier);
    }

    /**
     * Used to get the instance of the plugin that
     * will be using this local map.
     *
     * @return The instance of the plugin.
     */
    public abstract @NotNull CozyGamesPlugin<?, A, M, ?> getPlugin();

    /**
     * Used to create a new arena class.
     *
     * @return The new instance of an arena.
     */
    public abstract @NotNull A createEmptyArena(@NotNull String identifier, @NotNull String worldName);

    @Override
    public @NotNull CozyGames getAPI() {
        return this.getPlugin().getAPI();
    }

    @Override
    public @NotNull A createArena() {

        // Get world name.
        final String worldName = "todo";

        // Create a new arena.
        A arena = this.createEmptyArena(this.getIdentifier(), worldName);

        // Create world.
        arena.createWorld();

        // Register the arena with the api.
        this.getAPI().getArenaManager().registerArena(arena);
        return arena;
    }

    @Override
    public void saveToLocalConfiguration() {
        this.getPlugin().getMapConfiguration().insertType(this.getIdentifier(), (M) this);
    }
}
