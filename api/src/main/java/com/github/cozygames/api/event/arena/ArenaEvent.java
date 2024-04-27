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

package com.github.cozygames.api.event.arena;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an arena event.
 * <p>
 * Each arena event should implement this interface.
 * <p>
 * This will allow connections to the kerb client to
 * listen to any arena event.
 */
public interface ArenaEvent {

    /**
     * Used to get the arena's identifier
     * related with this event.
     *
     * @return The arena identifier.
     */
    @NotNull
    String getArenaIdentifier();
}
