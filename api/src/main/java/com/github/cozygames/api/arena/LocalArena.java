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
import com.github.cozygames.api.session.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a local arena.
 * <p>
 * This simplifies the {@link Arena} class for
 * creating new arena types in mini-game plugins.
 *
 * @param <S> The session class associated with this arena.
 * @param <A> The highest arena instance that should be used as a return value.
 *            This makes it easier to chane method calls.
 * @param <M> The map type this arena is created from.
 */
public abstract class LocalArena<S extends Session<A, M>, A extends Arena<A, M>, M extends Map<M>> extends Arena<A, M> {

    private final @NotNull SessionFactory<S, A, M> sessionFactory;

    /**
     * Used to create an instance of a local arena.
     *
     * @param mapIdentifier  The map identifier.
     * @param worldName      The name of the world the arena is located in.
     * @param sessionFactory The instance of the session factory.
     */
    public LocalArena(@NotNull String mapIdentifier, @NotNull String worldName, @NotNull SessionFactory<S, A, M> sessionFactory) {
        super(mapIdentifier, worldName);

        this.sessionFactory = sessionFactory;
    }

    /**
     * Used to create an instance of a local arena.
     *
     * @param identifier     The arena's identifier. This can be provided by
     *                       the {@link Arena#getIdentifier(String, String)} method.
     * @param sessionFactory The instance of the session factory.
     */
    public LocalArena(@NotNull String identifier, @NotNull SessionFactory<S, A, M> sessionFactory) {
        super(identifier);

        this.sessionFactory = sessionFactory;
    }

    /**
     * The instance of the mini-game plugin.
     * <p>
     * This is used to complete {@link Arena} methods.
     *
     * @return The instance of the mini-game plugin.
     */
    public abstract @NotNull CozyGamesPlugin<S, A, M, ?> getPlugin();

    @Override
    public @NotNull CozyGames getApi() {
        return this.getPlugin().getAPI();
    }

    @Override
    public @NotNull M getMap() {
        return this.getPlugin().getMapConfiguration()
                .getType(Map.getName(this.getMapIdentifier()))
                .orElseThrow();
    }

    @Override
    public @NotNull A activate(@NotNull UUID groupIdentifier) {

        // Set the group identifier.
        this.setGroupIdentifier(groupIdentifier);
        this.save();

        // Create a new session.
        S session = this.sessionFactory.createSession(this.getIdentifier());

        // Register session.
        this.getPlugin().getSessionManager().registerSession(session);

        // Teleport players.
        this.getGroup().orElseThrow().getMembers().forEach(
                member -> member.teleport(new ServerLocation(
                        this.getApi().getServerName(),
                        this.getWorldName(),
                        this.getMap().getSpawnPoint().orElseThrow()
                ))
        );
        return (A) this;
    }

    @Override
    public @NotNull A deactivate() {

        // Stop session if exists.
        this.getPlugin().getSessionManager()
                .getSession(this.getIdentifier())
                .ifPresent(Session::startAllComponents);

        // Unregister session if exists.
        this.getPlugin().getSessionManager()
                .unregisterSession(this.getIdentifier());

        // Delete world.
        this.deleteWorld();
        return (A) this;
    }
}
