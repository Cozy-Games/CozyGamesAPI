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

package com.github.cozygames.api.database.table;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.database.record.ArenaRecord;
import com.github.cozygames.api.map.Map;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ArenaTable extends TableAdapter<ArenaRecord> {

    @Override
    public @NotNull String getName() {
        return "arenas";
    }

    /**
     * Used to get an arena record from the arena
     * table given the arena's identifier.
     *
     * @param identifier The arena's identifier.
     * @return The optional arena record.
     */
    public @NotNull Optional<ArenaRecord> getArenaRecord(String identifier) {
        final String serverName = identifier.split(":")[0];
        final String gameIdentifier = identifier.split(":")[1];
        final String mapName = identifier.split(":")[2];
        final String worldName = identifier.split(":")[3];

        final String mapIdentifier = Map.getIdentifier(serverName, gameIdentifier, mapName);

        // Get the first record with the correct identifier.
        ArenaRecord record = this.getFirstRecord(new Query()
                .match("mapIdentifier", mapIdentifier)
                .match("worldName", worldName)
        );

        return Optional.ofNullable(record);
    }

    /**
     * Used to insert an arena instance into
     * the arena table.
     *
     * @param arena The instance of the arena.
     * @return This instance.
     */
    public @NotNull ArenaTable insertArena(Arena<?, ?> arena) {

        // Create the arena record.
        ArenaRecord record = new ArenaRecord();
        record.mapIdentifier = arena.getMapIdentifier();
        record.worldName = arena.getWorldName();

        arena.getGroupIdentifier().ifPresent(
                groupIdentifier -> record.groupIdentifier = groupIdentifier.toString()
        );

        // Insert the record.
        this.insertRecord(record);
        return this;
    }

    /**
     * Used to remove an arena from the database.
     *
     * @param identifier The arena's identifier.
     * @return This instance.
     */
    @SuppressWarnings("all")
    public @NotNull ArenaTable removeArena(@NotNull String identifier) {
        final String serverName = identifier.split(":")[0];
        final String gameIdentifier = identifier.split(":")[1];
        final String mapName = identifier.split(":")[2];
        final String worldName = identifier.split(":")[3];

        final String mapIdentifier = Map.getIdentifier(serverName, gameIdentifier, mapName);

        this.removeAllRecords(new Query()
                .match("mapIdentifier", mapIdentifier)
                .match("worldName", worldName)
        );
        return this;
    }
}
