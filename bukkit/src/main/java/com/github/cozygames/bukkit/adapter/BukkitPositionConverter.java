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

package com.github.cozygames.bukkit.adapter;

import com.github.cozygames.api.location.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Used to convert positions into locations
 * and locations into positions.
 */
public class BukkitPositionConverter implements Position.Converter<Location> {

    @Override
    public @NotNull Position convert(@NotNull Location location) {
        return new Position(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    @Override
    public @NotNull Location convert(@NotNull Position position, @NotNull String worldName) {
        return new Location(
                Bukkit.getWorld(worldName),
                position.getX(),
                position.getY(),
                position.getZ(),
                position.getYaw(),
                position.getPitch()
        );
    }
}
