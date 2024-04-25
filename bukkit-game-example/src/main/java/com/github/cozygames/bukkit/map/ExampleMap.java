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

package com.github.cozygames.bukkit.map;

import com.github.cozygames.api.map.LocalMap;
import com.github.cozygames.api.map.MapFactory;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.api.schematic.Schematic;
import com.github.cozygames.bukkit.BukkitExamplePlugin;
import com.github.cozygames.bukkit.arena.ExampleArena;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * The example map.
 */
public class ExampleMap extends LocalMap<ExampleArena, ExampleMap> {

    public ExampleMap(@NotNull String name) {
        super(
                name,
                BukkitExamplePlugin.getInstance().getAPI().getServerName(),
                BukkitExamplePlugin.getInstance().getGameIdentifier()
        );
    }

    @Override
    public @NotNull CozyGamesPlugin<?, ExampleArena, ExampleMap, ?> getPlugin() {
        return BukkitExamplePlugin.getInstance();
    }

    @Override
    public @NotNull ExampleArena createEmptyArena(@NotNull String identifier, @NotNull String worldName) {
        return new ExampleArena(identifier, worldName);
    }
}
