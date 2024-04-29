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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a component of a session.
 * <p>
 * Components can be used to organise different functions of a mini-game.
 *
 * @param <A> The arena class that is used in the session.
 * @param <M> The map class that is used in the arena.
 */
public interface SessionComponent<A extends ImmutableArena<A, M>, M extends Map<M>> {

    /**
     * Used to get the instance of the session this
     * component is part of.
     *
     * @return The instance of the session.
     */
    @NotNull
    Session<A, M> getSession();

    /**
     * Used to start the session component.
     */
    void start();

    /**
     * Used to stop the session component.
     */
    void stop();
}
