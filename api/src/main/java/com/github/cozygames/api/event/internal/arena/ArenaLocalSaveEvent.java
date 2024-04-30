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

/**
 * Represents the arena save to local configuration event.
 * <p>
 * Called when the {@link GlobalArena#saveToLocalConfiguration()}
 * method is called.
 */
public class ArenaLocalSaveEvent extends ArenaEvent {

    private boolean isComplete;

    private final @NotNull GlobalArena instance;

    /**
     * Used to create an arena save to local
     * configuration event.
     *
     * @param instance The instance of the global arena.
     */
    public ArenaLocalSaveEvent(@NotNull GlobalArena instance) {
        this.instance = instance;
    }

    @Override
    public @NotNull String getArenaIdentifier() {
        return this.instance.getIdentifier();
    }

    @Override
    public @NotNull ArenaEvent executeMethod(@NotNull Arena<?, ?> arena) {
        arena.saveToLocalConfiguration();
        return this;
    }
}