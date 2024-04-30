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

package com.github.cozygames.api.session;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.map.Map;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a cozy game plugin's session manager.
 * <p>
 * This is used within the plugin instance to manage
 * sessions within the plugin.
 *
 * @param <S> The session class.
 * @param <A> The arena class the session uses.
 * @param <M> The map class the session uses.
 */
public class SessionManager<S extends Session<A, M>, A extends Arena<A, M>, M extends Map<M>> {

    private final @NotNull List<S> sessionList;

    public SessionManager() {
        this.sessionList = new ArrayList<>();
    }

    public @NotNull Optional<S> getSession(@NotNull String arenaIdentifier) {
        for (S session : this.sessionList) {
            if (session.getArenaIdentifier().equals(arenaIdentifier)) {
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public @NotNull SessionManager<S, A, M> registerSession(@NotNull S session) {
        this.sessionList.add(session);
        return this;
    }

    public @NotNull SessionManager<S, A, M> unregisterSession(@NotNull S session) {
        this.sessionList.remove(session);
        return this;
    }

    public @NotNull SessionManager<S, A, M> unregisterSession(@NotNull String arenaIdentifier) {
        this.sessionList.removeIf(session -> session.getArenaIdentifier().equals(arenaIdentifier));
        return this;
    }

    public @NotNull SessionManager<S, A, M> stopAllSessions() {
        for (S session : this.sessionList) {
            session.stopAllComponents();
        }
        return this;
    }

    public @NotNull SessionManager<S, A, M> removeAllSessions() {
        this.sessionList.clear();
        return this;
    }
}
