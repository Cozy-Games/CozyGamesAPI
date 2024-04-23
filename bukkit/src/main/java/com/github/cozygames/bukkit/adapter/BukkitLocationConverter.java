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

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.location.ServerLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Used to convert a server location to a location
 * and a location to a server location.
 */
public class BukkitLocationConverter implements ServerLocation.Converter<Location> {

    @Override
    public @NotNull ServerLocation convert(@NotNull Location location) {
        final CozyGames api = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(CozyGames.class)).getProvider();
        final String serverName = api.getServerName();

        return new ServerLocation(
                serverName,
                Objects.requireNonNull(location.getWorld()).getName(),
                new BukkitPositionConverter().convert(location)
        );
    }

    @Override
    public @NotNull Location convert(@NotNull ServerLocation location) {
        return new BukkitPositionConverter().convert(
                location.getPosition(),
                location.getWorldName()
        );
    }
}
