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

package com.github.cozygames.bukkit.plugin;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.api.session.Session;
import com.github.cozygames.bukkit.command.CommandManager;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import com.github.smuddgge.squishyconfiguration.directory.ConfigurationDirectory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents the bukkit cozy game's plugin.
 *
 * @param <L> The instance of the plugin loader class.
 */
public abstract class CozyGamesBukkitPlugin<
        S extends Session<A, M>,
        A extends Arena<A, M>,
        M extends Map<M>,
        L extends JavaPlugin> extends CozyGamesPlugin<S, A, M, L> {

    private CozyGames apiPointer;
    private CozyPlugin<L> cozyPlugin;

    /**
     * Used to create a cozy games bukkit plugin instance.
     *
     * @param loader The instance of the plugin loader class.
     */
    public CozyGamesBukkitPlugin(@NotNull L loader) {
        super(loader);

        this.cozyPlugin = new CozyPlugin<>(loader) {
            @Override
            public boolean isCommandTypesEnabled() {
                return this.isCommandTypesEnabled();
            }

            @Override
            public void onEnable() {

            }

            @Override
            public void onDisable() {

            }

            @Override
            public void onLoadCommands(com.github.cozyplugins.cozylibrary.command.@NotNull CommandManager commandManager) {
                this.onLoadCommands(commandManager);
            }

            @Override
            public void onLoadPlaceholders(@NotNull PlaceholderManager<L> placeholderManager) {
                this.onLoadPlaceholders(placeholderManager);
            }
        };
    }

    /**
     * Used to check if the command type configuration
     * directory should be generated.
     *
     * @return True if the command types are enabled.
     */
    public abstract boolean isCommandTypesEnabled();

    /**
     * Called when the plugins commands should be loaded.
     * <p>
     * This way of registering commands doesn't have to be used.
     * However, this way makes it allot easier.
     *
     * @param commandManager The instance of the command manager.
     */
    public abstract void onLoadCommands(@NotNull CommandManager commandManager);

    /**
     * Called when the placeholders should be loaded
     * into the manager.
     *
     * @param placeholderManager The instance of the placeholder manager.
     */
    public abstract void onLoadPlaceholders(@NotNull PlaceholderManager<L> placeholderManager);

    @Override
    public @NotNull CozyGamesPlugin<S, A, M, L> enable() {

        // Attempt to get the instance of the cozy games api.
        final RegisteredServiceProvider<CozyGames> gamesProvider = Bukkit.getServicesManager().getRegistration(CozyGames.class);

        // Check if the provider doesn't exist.
        if (gamesProvider == null) {
            throw new CozyGamesProvider.APINotLoadedException();
        }

        // Set the instance of the api.
        this.apiPointer = gamesProvider.getProvider();

        // Enable cozy plugin.
        this.cozyPlugin.enable();

        // Enable super class.
        super.enable();
        return this;
    }

    @Override
    public @NotNull CozyGamesPlugin<S, A, M, L> disable() {

        // Disable cozy plugin.
        this.cozyPlugin.disable();

        // disable super class.
        super.disable();
        return this;
    }

    @Override
    public @NotNull CozyGames getAPI() {
        return this.apiPointer;
    }

    public @NotNull com.github.cozyplugins.cozylibrary.command.CommandManager getCommandManager() {
        return this.cozyPlugin.getCommandManager();
    }

    public @NotNull Optional<PlaceholderManager<L>> getPlaceholderManager() {
        return this.cozyPlugin.getPlaceholderManager();
    }

    public @NotNull ConfigurationDirectory getCommandDirectory() {
        return this.cozyPlugin.getCommandDirectory();
    }
}
