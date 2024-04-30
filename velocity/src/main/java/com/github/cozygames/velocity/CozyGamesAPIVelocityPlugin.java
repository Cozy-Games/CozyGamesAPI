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

package com.github.cozygames.velocity;

import com.github.cozygames.api.member.PlayerAdapter;
import com.github.cozygames.api.plugin.CozyGamesAPIPlugin;
import com.github.cozygames.velocity.adapter.VelocityPlayerAdapter;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Plugin(
        id = "cozygamesapi",
        name = "CozyGamesAPI",
        version = "@version@",
        description = "The api used to interface with the cozy game system.",
        authors = {"Smuddgge"}
)
public class CozyGamesAPIVelocityPlugin implements CozyGamesAPIPlugin {

    public ProxyServer proxy;
    public File dataFolder;

    @Inject
    public CozyGamesAPIVelocityPlugin(ProxyServer proxy, @DataDirectory final Path folder) {
        this.proxy = proxy;
        this.dataFolder = folder.toFile();
    }

    @Override
    public @NotNull File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public @NotNull Optional<String> getPlayerName(@NotNull UUID playerUuid) {
        return this.proxy.getPlayer(playerUuid).map(Player::getUsername);
    }

    @Override
    public @NotNull Optional<UUID> getPlayerUuid(@NotNull String playerName) {
        return this.proxy.getPlayer(playerName).map(Player::getUniqueId);
    }

    @Override
    public @NotNull PlayerAdapter<?> getPlayerAdapter() {
        return new VelocityPlayerAdapter(this.proxy);
    }
}
