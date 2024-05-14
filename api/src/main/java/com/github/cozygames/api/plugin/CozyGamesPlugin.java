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

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.arena.ArenaConfiguration;
import com.github.cozygames.api.arena.ArenaFactory;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.map.MapConfiguration;
import com.github.cozygames.api.map.MapFactory;
import com.github.cozygames.api.session.Session;
import com.github.cozygames.api.session.SessionFactory;
import com.github.cozygames.api.session.SessionManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents a foundation that can be used
 * in cozy game plugin's.
 * <p>
 * Designed to make it easier to create games.
 *
 * @param <L> The plugin loader.
 * @param <M> The map type.
 */
public abstract class CozyGamesPlugin<
        S extends Session<A, M>,
        A extends Arena<A, M>,
        M extends Map<M>,
        L> {

    private final @NotNull L loader;

    private MapConfiguration<M> mapConfiguration;
    private ArenaConfiguration<A, M> arenaConfiguration;
    private SessionManager<S, A, M> sessionManager;

    /**
     * Used to create a cozy games plugin instance.
     *
     * @param loader The instance of the plugin loader class.
     */
    public CozyGamesPlugin(@NotNull L loader) {
        this.loader = loader;
    }

    /**
     * Used to get the plugin's data folder.
     * <p>
     * The location where the configuration files should be stored.
     *
     * @return The data folder file.
     */
    public abstract @NotNull File getDataFolder();

    /**
     * Used to get the instance of the api on
     * the specific platform.
     * <p>
     * This should be implemented within the platforms
     * api plugin because there may be a service provider.
     *
     * @return The instance of the api.
     */
    public abstract @NotNull CozyGames getApi();

    /**
     * Used to get the game identifier this plugin
     * will be providing.
     * <p>
     * The string must be snake case without underscores.
     * This makes it easier for players type in certain commands.
     * <p>
     * Example
     * <pre>{@code
     * bedwars
     * }</pre>
     *
     * @return The game identifier.
     */
    public abstract @NotNull String getGameIdentifier();

    /**
     * Used to get the instance of the map factory.
     * <p>
     * This is used to create empty map instances.
     *
     * @return The map factory instance.
     */
    public abstract @NotNull MapFactory<M> getMapFactory();

    /**
     * Used to get the instance of the arena factory.
     * <p>
     * This is used to create empty arena instances.
     *
     * @return The arena factory instance.
     */
    public abstract @NotNull ArenaFactory<A, M> getArenaFactory();

    /**
     * Used to get the instance of the session factory.
     * <p>
     * This is used to create empty session instances.
     *
     * @return The session factory instance.
     */
    public abstract @NotNull SessionFactory<S, A, M> getSessionFactory();

    /**
     * Called when the plugin is enabled.
     * <p>
     * This is called after the super class
     * has finished enabling.
     */
    public abstract void onEnable();

    /**
     * Called when the plugin is disabled.
     * <p>
     * This is called before the super class
     * has finished disabling.
     */
    public abstract void onDisable();

    /**
     * Called when the loader is enabled.
     * <p>
     * This will register this plugin instance with the api.
     *
     * @return This instance.
     */
    public @NotNull CozyGamesPlugin<S, A, M, L> enable() {

        // Set up the map configuration directory.
        this.mapConfiguration = new MapConfiguration<>(this);
        this.mapConfiguration.reload();

        // Register all available maps.
        for (Map<M> map : this.getMapConfiguration().getAllTypes()) {
            this.getApi().getMapManager().registerMap(map);
        }

        // Set up the arena configuration directory.
        this.arenaConfiguration = new ArenaConfiguration<>(this);
        this.arenaConfiguration.reload();

        // Set up the session manager.
        this.sessionManager = new SessionManager<>();

        // Call the on enable method to indicate this
        // class has finished setting up.
        this.onEnable();

        // Register this plugin with the api.
        this.getApi().registerLocalPlugin(this);
        return this;
    }

    /**
     * Called when the loader is disabled.
     *
     * @return This instance.
     */
    public @NotNull CozyGamesPlugin<S, A, M, L> disable() {

        // Call the on disable method before disabling.
        this.onDisable();

        // Stop sessions.
        this.sessionManager.stopAllSessions();
        this.sessionManager.removeAllSessions();

        // Unregister maps and arenas.
        this.getApi().getMapManager().unregisterMapList(this.getGameIdentifier());
        this.getApi().getArenaManager().removeMapList(this.getGameIdentifier());

        // Remove arenas.
        for (Arena<A, M> arena : new ArrayList<>(this.getArenaConfiguration().getAllTypes())) {
            arena.deleteWorld();
            arena.delete();
        }

        // Ensure empty arena configuration.
        this.getArenaConfiguration().getDirectory().delete();

        // Unregister this plugin from the api.
        this.getApi().unregisterLocalPlugin(this);
        return this;
    }

    /**
     * Used to get the instance of the plugin loader.
     *
     * @return The instance of the plugin loader.
     */
    public @NotNull L getLoader() {
        return this.loader;
    }

    /**
     * Used to get the instance of the local map storage.
     * <p>
     * A single type configuration directory.
     *
     * @return The map configuration instance.
     */
    public @NotNull MapConfiguration<M> getMapConfiguration() {
        return this.mapConfiguration;
    }

    /**
     * Used to get the instance of the local arena storage.
     * <p>
     * A single type configuration directory.
     *
     * @return The arena configuration instance.
     */
    public @NotNull ArenaConfiguration<A, M> getArenaConfiguration() {
        return this.arenaConfiguration;
    }

    /**
     * Used to get the instance of this plugin's session manager.
     *
     * @return The instance of the session manager.
     */
    public @NotNull SessionManager<S, A, M> getSessionManager() {
        return this.sessionManager;
    }
}
