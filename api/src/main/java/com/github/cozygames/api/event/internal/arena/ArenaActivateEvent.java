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

package com.github.cozygames.api.event.internal.arena;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.arena.GlobalArena;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the arena activate event.
 * <p>
 * Called when the {@link GlobalArena#activate(UUID)}
 * method is called.
 */
public class ArenaActivateEvent extends ArenaEvent {

    private boolean isComplete;

    private final @NotNull String arenaIdentifier;
    private final @NotNull UUID groupIdentifier;

    /**
     * Used to create an arena activate event.
     *
     * @param arenaIdentifier The arena identifier.
     */
    public ArenaActivateEvent(@NotNull String arenaIdentifier, @NotNull UUID groupIdentifier) {
        this.arenaIdentifier = arenaIdentifier;
        this.groupIdentifier = groupIdentifier;
    }

    @Override
    public @NotNull String getArenaIdentifier() {
        return this.arenaIdentifier;
    }

    public @NotNull UUID getGroupIdentifier() {
        return this.groupIdentifier;
    }

    @Override
    public @NotNull ArenaEvent executeMethod(@NotNull Arena<?, ?> arena) {
        arena.activate(this.groupIdentifier);
        return this;
    }
}
