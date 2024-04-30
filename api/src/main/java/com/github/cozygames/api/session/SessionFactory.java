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

/**
 * The empty session factory.
 * <p>
 * Used by the {@link Arena} to create a new session.
 *
 * @param <S> The session that will be created.
 * @param <A> The arena type that is used in the session.
 * @param <M> The map type that is used in the arena.
 */
public interface SessionFactory<S extends Session<A, M>, A extends Arena<A, M>, M extends Map<M>> {

    /**
     * Used to create an empty session instance.
     *
     * @param arenaIdentifier The arena identifier that is creating the session.
     * @return The session instance.
     */
    S createSession(@NotNull String arenaIdentifier);
}
