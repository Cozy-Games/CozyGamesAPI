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

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Represents the instance of the cozy games api plugin.
 */
public interface CozyGamesPlugin {

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
}
