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

import com.github.cozygames.api.arena.GlobalArena;
import com.github.kerbity.kerb.packet.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the arena delete world event.
 * <p>
 * Called when the {@link GlobalArena#deleteWorld()}
 * method is called.
 */
public class ArenaDeleteWorldEvent extends Event implements ArenaEvent {

    private final @NotNull String arenaIdentifier;
    private final @NotNull String worldName;

    /**
     * Used to create an arena delete world event.
     *
     * @param arenaIdentifier The arena identifier.
     */
    public ArenaDeleteWorldEvent(@NotNull String arenaIdentifier, @NotNull String worldName) {
        this.arenaIdentifier = arenaIdentifier;
        this.worldName = worldName;
    }

    @Override
    public @NotNull String getArenaIdentifier() {
        return this.arenaIdentifier;
    }

    /**
     * Used to get the name of the world
     * that should be created.
     *
     * @return The world name.
     */
    public @NotNull String getWorldName() {
        return this.worldName;
    }
}
