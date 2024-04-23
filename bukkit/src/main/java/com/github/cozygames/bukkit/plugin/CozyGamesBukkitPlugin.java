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

package com.github.cozygames.bukkit.plugin;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the bukkit cozy game's plugin.
 *
 * @param <L> The instance of the plugin loader class.
 */
public abstract class CozyGamesBukkitPlugin<L extends JavaPlugin, M extends Map<M>> extends CozyGamesPlugin<L, M> {

    private CozyGames apiPointer;

    /**
     * Used to create a cozy games bukkit plugin instance.
     *
     * @param loader The instance of the plugin loader class.
     */
    public CozyGamesBukkitPlugin(@NotNull L loader) {
        super(loader);
    }

    @Override
    public @NotNull CozyGamesPlugin<L, M> enable() {

        // Attempt to get the instance of the cozy games api.
        final RegisteredServiceProvider<CozyGames> gamesProvider = Bukkit.getServicesManager().getRegistration(CozyGames.class);

        // Check if the provider doesn't exist.
        if (gamesProvider == null) {
            throw new CozyGamesProvider.APINotLoadedException();
        }

        // Set the instance of the api.
        this.apiPointer = gamesProvider.getProvider();

        // enable super class.
        super.enable();
        return this;
    }

    @Override
    public @NotNull CozyGames getAPI() {
        return this.apiPointer;
    }
}
