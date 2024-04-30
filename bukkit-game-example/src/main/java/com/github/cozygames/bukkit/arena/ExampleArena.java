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

import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.bukkit.BukkitExamplePlugin;
import com.github.cozygames.bukkit.adapter.BukkitPositionConverter;
import com.github.cozygames.bukkit.map.ExampleMap;
import com.github.cozygames.bukkit.session.ExampleSession;
import com.github.cozygames.bukkit.session.ExampleSessionFactory;
import com.github.cozyplugins.cozylibrary.indicator.LocationConvertable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ExampleArena extends LocalBukkitArena<ExampleSession, ExampleArena, ExampleMap> implements LocationConvertable {

    private final @NotNull Location spawnPoint;

    public ExampleArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName, new ExampleSessionFactory());
        this.spawnPoint = this.getMap().getSpawnPoint().orElseThrow()
                .getLocation(new BukkitPositionConverter(), worldName);
    }

    public ExampleArena(@NotNull String identifier) {
        super(identifier, new ExampleSessionFactory());
        this.spawnPoint = this.getMap().getSpawnPoint().orElseThrow()
                .getLocation(new BukkitPositionConverter(), this.getWorldName());
    }

    @Override
    public @NotNull CozyGamesPlugin<ExampleSession, ExampleArena, ExampleMap, ?> getPlugin() {
        return BukkitExamplePlugin.getInstance();
    }

    public @NotNull Location getSpawnPoint() {
        return this.spawnPoint;
    }

    @Override
    public @NotNull ExampleArena saveToLocalConfiguration() {
        this.getPlugin().getArenaConfiguration().insertType(this.getIdentifier(), this);
        return this;
    }

    @Override
    public @NotNull ExampleArena deleteFromLocalConfiguration() {
        this.getPlugin().getArenaConfiguration().removeType(this.getIdentifier());
        return this;
    }
}
