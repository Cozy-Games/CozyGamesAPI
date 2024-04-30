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
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.event.internal.arena.*;
import com.github.cozygames.api.map.GlobalMap;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a global arena.
 * <p>
 * This arena class is used to represent an arena
 * that could be on a different server.
 * <p>
 * This uses kerb events when you call a method within this
 * class, and it will find the locally registered plugin with
 * the same arena to call the related method.
 */
public class GlobalArena extends Arena<GlobalArena, GlobalMap> {

    /**
     * Used to create an instance of a global arena.
     *
     * @param mapIdentifier The map identifier.
     * @param worldName     The name of the world the arena is located in.
     */
    public GlobalArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    /**
     * Used to create an instance of a global arena.
     *
     * @param identifier The arena's identifier. This can be provided by
     *                   the {@link Arena#getIdentifier(String, String)} method.
     */
    public GlobalArena(@NotNull String identifier) {
        super(identifier);
    }

    @Override
    public @NotNull CozyGames getApi() {
        return CozyGamesProvider.get();
    }

    @Override
    public @NotNull GlobalMap getMap() {
        return this.getApi().getMapManager()
                .getMap(this.getMapIdentifier())
                .orElseThrow();
    }

    @Override
    public @NotNull GlobalArena createWorld() {
        this.getApi().callEvent(new ArenaWorldCreateEvent(this.getIdentifier(), this.getWorldName()));
        return this;
    }

    @Override
    public @NotNull GlobalArena deleteWorld() {
        this.getApi().callEvent(new ArenaWorldDeleteEvent(this.getIdentifier(), this.getWorldName()));
        return this;
    }

    @Override
    public @NotNull GlobalArena activate(@NotNull UUID groupIdentifier) {
        this.getApi().callEvent(new ArenaActivateEvent(this.getIdentifier()));
        return this;
    }

    @Override
    public @NotNull GlobalArena deactivate() {
        this.getApi().callEvent(new ArenaDeactivateEvent(this.getIdentifier()));
        return this;
    }

    @Override
    public @NotNull GlobalArena saveToLocalConfiguration() {
        this.getApi().callEvent(new ArenaLocalSaveEvent(this));
        return this;
    }

    @Override
    public @NotNull GlobalArena deleteFromLocalConfiguration() {
        this.getApi().callEvent(new ArenaLocalDeleteEvent(this.getIdentifier()));
        return this;
    }
}
