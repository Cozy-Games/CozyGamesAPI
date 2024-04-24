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

import com.github.cozygames.api.map.Map;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Used to get a specific type of arena given an identifier.
 * <p>
 * This can be implemented in the cozy game plugin to let
 * a session obtain the instance of the arena.
 *
 * @param <A> The arena that will be returned.
 * @param <M> The map type the arena is using.
 */
public interface ArenaGetter<A extends Arena<A, M>, M extends Map<M>> {

    /**
     * Used to get an instance of an arena.
     *
     * @param identifier The arena identifier.
     * @return The optional arena.
     */
    @NotNull
    Optional<Arena<A, M>> getArena(@NotNull String identifier);
}
