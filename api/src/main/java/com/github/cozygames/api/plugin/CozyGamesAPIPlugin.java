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

package com.github.cozygames.api.plugin;

import com.github.cozygames.api.member.PlayerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a instance of the cozy games api plugin.
 * <p>
 * This is used to get important infomation about the
 * plugin used to assist the api.
 */
public interface CozyGamesAPIPlugin {

    /**
     * The instance of the cozy games api plugin folder.
     * <p>
     * This folder is used to store all the plugin's configuration.
     * <p>
     * The data folder will be used to create and get the instance
     * of the connection configuration.
     *
     * @return The instance of the cozy games api plugin folder.
     */
    @NotNull
    File getDataFolder();

    /**
     * Used to get the instance of a players name
     * using the plugin's api.
     *
     * @param playerUuid The player's uuid.
     * @return The optional player's name.
     * This can be null if the plugin's api is unable
     * to get the player infomation.
     */
    @NotNull
    Optional<String> getPlayerName(@NotNull UUID playerUuid);

    /**
     * Used to get the instance of a players uuid
     * using the plugin's api.
     *
     * @param playerName The player's name.
     * @return The optional player's uuid.
     * This can be null if the plugin's api is unable
     * to get the player infomation.
     */
    @NotNull
    Optional<UUID> getPlayerUuid(@NotNull String playerName);

    /**
     * The player adapter is used to convert an instance of a
     * member to the platform specific player class.
     *
     * @return The instance of the player adapter.
     */
    @NotNull
    PlayerAdapter<?> getPlayerAdapter();
}
