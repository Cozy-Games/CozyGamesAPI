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

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CommandManager;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

public class CozyGamesAPIPlugin extends CozyPlugin<CozyGamesAPIBukkitLoader> {

    /**
     * Used to create a new cozy plugin instance.
     *
     * @param plugin The instance of the plugin loader.
     */
    public CozyGamesAPIPlugin(@NotNull CozyGamesAPIBukkitLoader plugin) {
        super(plugin);
    }

    @Override
    public boolean isCommandTypesEnabled() {
        return true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoadCommands(@NotNull CommandManager commandManager) {

    }

    @Override
    public void onLoadPlaceholders(@NotNull PlaceholderManager<CozyGamesAPIBukkitLoader> placeholderManager) {

    }
}
