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

package com.github.cozygames.api.arena;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.location.ServerLocation;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.api.session.Session;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a local arena.
 * <p>
 * This simplifies the {@link Arena} class for
 * creating new arena types in mini-game plugins.
 *
 * @param <S> The session class assosicated with this arena.
 * @param <A> The top arena class.
 * @param <M> The map type using in this arena.
 */
public abstract class LocalArena<S extends Session<A, M>, A extends Arena<A, M>, M extends Map<M>> extends Arena<A, M> {

    /**
     * Used to create a new local arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public LocalArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    /**
     * Used to create a new local arena.
     *
     * @param identifier The arena's identifier.
     */
    public LocalArena(@NotNull String identifier) {
        super(identifier);
    }

    /**
     * Used to get the instance of the cozy game plugin
     * where this arena is used.
     *
     * @return The instance of the plugin.
     */
    public abstract @NotNull CozyGamesPlugin<S, A, M, ?> getPlugin();

    /**
     * Used to create a new instance of a session
     * that can be used in this arena.
     *
     * @return The instance of the new session.
     */
    public abstract @NotNull S createSession();

    @Override
    public @NotNull CozyGames getAPI() {
        return this.getPlugin().getAPI();
    }

    @Override
    public @NotNull M getMap() {
        return this.getPlugin().getMapConfiguration().getType(this.getMapIdentifier()).orElseThrow();
    }

    @Override
    public void activate(@NotNull UUID groupIdentifier) {

        // Set the group identifier.
        this.setGroupIdentifier(groupIdentifier);
        this.save();

        // Create a new session.
        S session = this.createSession();

        // Register session.
        this.getPlugin().getSessionManager().registerSession(session);

        // Teleport players.
        this.getGroup().orElseThrow().getMembers().forEach(
                member -> member.teleport(new ServerLocation(
                        this.getAPI().getServerName(),
                        this.getWorldName(),
                        this.getMap().getSpawnPoint().orElseThrow()
                ))
        );
    }

    @Override
    public void deactivate() {

        // Stop session if exists.
        this.getPlugin().getSessionManager()
                .getSession(this.getIdentifier())
                .ifPresent(Session::startAllComponents);

        // Unregister session if exists.
        this.getPlugin().getSessionManager()
                .unregisterSession(this.getIdentifier());

        // Delete world.
        this.deleteWorld();
    }

    @Override
    public void remove() {

        // Remove world.

        // Unregister arena.
        this.getAPI().getArenaManager().unregisterArena(this.getIdentifier());

        // Delete arena.
        this.delete();
    }

    @Override
    public void saveToLocalConfiguration() {

    }

    @Override
    public void deleteFromLocalConfiguration() {

    }
}
