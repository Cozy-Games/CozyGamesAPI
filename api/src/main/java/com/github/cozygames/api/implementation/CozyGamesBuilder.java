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

package com.github.cozygames.api.implementation;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.plugin.CozyGamesAPIPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the api builder.
 * <p>
 * Used to help build an instance of the api.
 * <p>
 * To create an instance of the api, you can use
 * this builder within this maven project.
 * <pre>{@code
 * CozyGames cozyGames = new CozyGamesBuilder(plugin).build();
 * }</pre>
 */
@ApiStatus.Internal
public class CozyGamesBuilder {

    private final @NotNull CozyGamesAPIPlugin plugin;

    /**
     * Used to create a new cozy games api builder.
     *
     * @param plugin The instance of the cozy games
     *               api plugin.
     */
    @ApiStatus.Internal
    public CozyGamesBuilder(@NotNull CozyGamesAPIPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Used to get the instance of the cozy
     * games api plugin.
     *
     * @return The instance of the plugin.
     */
    public @NotNull CozyGamesAPIPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Used to build the instance of the cozy
     * games api implementation.
     *
     * @return The new instance of the cozy games api.
     */
    public @NotNull CozyGames build() {
        return new CozyGamesImpl(plugin);
    }
}
