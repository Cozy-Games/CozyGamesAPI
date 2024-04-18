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

package com.github.cozygames.bukkit;

import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public class CozyGamesBukkitPlugin implements CozyGamesPlugin {

    private final @NotNull CozyGamesBukkitLoader loader;

    public CozyGamesBukkitPlugin(@NotNull CozyGamesBukkitLoader loader) {
        this.loader = loader;
    }

    @Override
    public @NotNull File getDataFolder() {
        return this.loader.getDataFolder();
    }

    @Override
    public @NotNull Optional<String> getPlayerName(@NotNull UUID playerUuid) {
        return Optional.ofNullable(Bukkit.getOfflinePlayer(playerUuid).getName());
    }

    @Override
    public @NotNull Optional<UUID> getPlayerUuid(@NotNull String playerName) {

        // Fist check if they are online as getting and
        // offline player by name is depreciated.
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return Optional.of(player.getUniqueId());
            }
        }

        // Otherwise, try getting the offline player.
        try {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            return Optional.of(offlinePlayer.getUniqueId());
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
