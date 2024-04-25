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
import com.github.cozygames.api.database.record.ArenaRecord;
import com.github.cozygames.api.database.table.ArenaTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the apis arena manager.
 * <p>
 * Used to register active {@link Arena}'s on the
 * cozy games network.
 */
public class ArenaManager {

    private final @NotNull CozyGames api;
    private final @NotNull List<ImmutableArena<?, ?>> localArenaList;

    public ArenaManager(@NotNull CozyGames api) {
        this.api = api;
        this.localArenaList = new ArrayList<>();
    }

    public @NotNull List<ImmutableArena<?, ?>> getLocalArenaList() {
        return this.localArenaList;
    }

    public @NotNull List<ImmutableArena<?, ?>> getLocalArenaList(@NotNull String gameIdentifier) {
        return this.localArenaList.stream()
                .filter(arena -> arena.getMap().getGameIdentifier().equals(gameIdentifier))
                .toList();
    }

    public @NotNull ArenaManager registerArena(@NotNull ImmutableArena<?, ?> arena) {
        this.localArenaList.add(arena);
        return this;
    }

    public @NotNull ArenaManager unregisterArena(@NotNull ImmutableArena<?, ?> arena) {
        this.localArenaList.remove(arena);
        return this;
    }

    public @NotNull ArenaManager unregisterArena(@NotNull String identifier) {
        this.localArenaList.removeIf(map -> map.getIdentifier().equals(identifier));
        return this;
    }

    public @NotNull Optional<GlobalArena> getGlobalArena(@NotNull String identifier) {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getArenaRecord(identifier)
                .map(ArenaRecord::convert);
    }

    public @NotNull Optional<GlobalArena> getGlobalArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        final String identifier = Arena.getIdentifier(mapIdentifier, worldName);
        return this.getGlobalArena(identifier);
    }

    public @NotNull List<GlobalArena> getGlobalArenaList() {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getRecordList()
                .stream()
                .map(ArenaRecord::convert)
                .toList();
    }

    public @NotNull List<GlobalArena> getGlobalArenaList(@NotNull String serverName) {
        return this.getGlobalArenaList().stream()
                .filter(arena -> arena.getMap().getServerName().equalsIgnoreCase(serverName))
                .toList();
    }

    public @NotNull List<GlobalArena> getGlobalArenaList(@NotNull String serverName, @NotNull String gameIdentifier) {
        return this.getGlobalArenaList().stream()
                .filter(arena -> arena.getMap().getServerName().equalsIgnoreCase(serverName)
                        && arena.getMap().getGameIdentifier().equalsIgnoreCase(gameIdentifier))
                .toList();
    }
}
