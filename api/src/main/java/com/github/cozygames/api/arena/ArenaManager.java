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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Represents the arena manager.
 * <p>
 * Used to get instances of arenas registered
 * with this api connection.
 * <p>
 * This is recommended over using the {@link CozyGames#getDatabase()}.
 * <p>
 * Unlike the {@link com.github.cozygames.api.map.MapManager} the arena
 * manager doesn't register arenas specifically. Instead, it relies
 * on the {@link Arena#save()} and {@link Arena#delete()} method to update
 * to the database when it should be available. This is because normally
 * an arena is created for 1 game then deleted. Therefor, there is no need to
 * check if an arena is available as they are created to match demand.
 */
public class ArenaManager {

    private final @NotNull CozyGames api;

    /**
     * Used to create a new arena manager.
     * <p>
     * This should only be created by the
     * internal api implementation.
     *
     * @param api The instance of the api.
     */
    @ApiStatus.Internal
    public ArenaManager(@NotNull CozyGames api) {
        this.api = api;
    }

    /**
     * Used to get the instance of a local {@link Arena}.
     * <p>
     * Checks each locally registered plugin if it contains
     * the {@link Arena} identifier.
     *
     * @param arenaIdentifier The arena identifier to look for.
     * @return The optional {@link Arena}.
     */
    public @NotNull Optional<Arena<?, ?>> getLocalArena(@NotNull String arenaIdentifier) {
        for (CozyGamesPlugin<?, ?, ?, ?> plugin : this.api.getLocalPlugins()) {
            for (Arena<?, ?> arena : plugin.getArenaConfiguration().getAllTypes()) {
                if (arena.getIdentifier().equals(arenaIdentifier)) return Optional.of(arena);
            }
        }
        return Optional.empty();
    }

    /**
     * Used to get the list of global arena's.
     *
     * @return The list of global arenas.
     */
    public @NotNull List<GlobalArena> getArenaList() {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getRecordList()
                .stream()
                .map(ArenaRecord::convert)
                .toList();
    }

    /**
     * Used to get a filtered list of global arenas.
     *
     * @param arenaFilter The filter that should be used.
     * @return The list of global arenas.
     */
    public @NotNull List<GlobalArena> getArenaList(@NotNull ArenaFilter arenaFilter) {
        return arenaFilter.filterArenas(this.getArenaList());
    }

    /**
     * Used to get an arena based on its identifier.
     * <p>
     * This is recommended over using a filter as it is
     * less memory costing.
     *
     * @param arenaIdentifier The arena's identifier.
     * @return The optional global arena.
     */
    public @NotNull Optional<GlobalArena> getArena(@NotNull String arenaIdentifier) {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getArenaRecord(arenaIdentifier)
                .map(ArenaRecord::convert);
    }

    /**
     * Used to get the first global arena using a filter.
     *
     * @param arenaFilter The filter that should be used.
     * @return The first global arena that matches the filter.
     */
    public @NotNull Optional<GlobalArena> getFirstArena(@NotNull ArenaFilter arenaFilter) {
        return Optional.ofNullable(arenaFilter.filterArenas(this.getArenaList()).get(0));
    }

    /**
     * Used to remove arenas with a specific game identifier.
     *
     * @param gameIdentifier The game identifier to filter.
     * @return This instance.
     */
    public @NotNull ArenaManager removeMapList(@NotNull String gameIdentifier) {
        for (ArenaRecord record : this.api.getDatabase().getTable(ArenaTable.class).getRecordList()) {
            final Arena<?, ?> arena = record.convert();

            // If the game identifier matches, delete the arena.
            if (arena.getMap().getGameIdentifier().equalsIgnoreCase(gameIdentifier)) arena.delete();
        }
        return this;
    }
}
