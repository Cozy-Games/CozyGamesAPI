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

package com.github.cozygames.api.configuration;

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.smuddgge.squishyconfiguration.directory.ConfigurationDirectory;
import org.jetbrains.annotations.NotNull;

public class MapConfigurationListener<L, M extends Map<M>> implements ConfigurationDirectory.Listener {

    private final @NotNull CozyGamesPlugin<L, M> plugin;

    public MapConfigurationListener(@NotNull CozyGamesPlugin<L, M> plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onReload(@NotNull ConfigurationDirectory directory) {

        // Loop though all the stored maps.
        for (M map : this.plugin.getMapConfiguration().getAllTypes()) {

        }
    }
}
