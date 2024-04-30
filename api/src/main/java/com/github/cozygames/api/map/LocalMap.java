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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a local map.
 * <p>
 * This simplifies the {@link Map} class for
 * creating new map types in mini-game plugins.
 *
 * @param <M> The highest map instance that should be used as a return value.
 *            This makes it easier to chane method calls.
 */
public abstract class LocalMap<M extends Map<M>> extends Map<M> {

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

    /**
     * The instance of the mini-game plugin.
     * <p>
     * This is used to complete {@link Map} methods.
     *
     * @return The instance of the mini-game plugin.
     */
    public abstract @NotNull CozyGamesPlugin<?, ?, M, ?> getPlugin();

    @Override
    public @NotNull CozyGames getApi() {
        return this.getPlugin().getAPI();
    }
}
