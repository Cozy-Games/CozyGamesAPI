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

import com.github.cozygames.api.map.MapFactory;
import com.github.cozygames.bukkit.arena.ExampleArena;
import com.github.cozygames.bukkit.command.CommandManager;
import com.github.cozygames.bukkit.map.ExampleMap;
import com.github.cozygames.bukkit.plugin.CozyGamesBukkitPlugin;
import com.github.cozygames.bukkit.session.ExampleSession;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the instance of the
 * bukkit example plugin.
 * <p>
 * This should be used to get all the base
 * functions within this plugin.
 */
public final class BukkitExamplePlugin extends CozyGamesBukkitPlugin<
        ExampleSession,
        ExampleArena,
        ExampleMap,
        BukkitExampleLoader> {

    private static BukkitExamplePlugin instance;

    /**
     * Used to create a new instance of the bukkit example plugin.
     * <p>
     * This should be only initialised within this plugin.
     *
     * @param loader The instance of the loader.
     */
    @ApiStatus.Internal
    public BukkitExamplePlugin(@NotNull BukkitExampleLoader loader) {
        super(loader);

        // Set up the singleton instance.
        BukkitExamplePlugin.instance = this;
    }

    @Override
    public @NotNull String getGameIdentifier() {
        return "BukkitExample";
    }

    @Override
    public @NotNull MapFactory<ExampleMap> getMapFactory() {
        return new ExampleMap.Factory();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean isCommandTypesEnabled() {
        return false;
    }

    @Override
    public void onLoadCommands(@NotNull CommandManager commandManager) {

    }

    @Override
    public void onLoadPlaceholders(@NotNull PlaceholderManager<BukkitExampleLoader> placeholderManager) {

    }

    /**
     * Used to get the instance of this plugin.
     *
     * @return The plugin instance.
     */
    public static @NotNull BukkitExamplePlugin getInstance() {
        return BukkitExamplePlugin.instance;
    }
}
