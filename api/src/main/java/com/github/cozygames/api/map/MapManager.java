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

package com.github.cozygames.api.map;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.database.record.MapRecord;
import com.github.cozygames.api.database.table.MapTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the map manager.
 * <p>
 * Used to register and unregister available maps.
 * <p>
 * Used to also get the instance of maps.
 */
public class MapManager {

    private final @NotNull CozyGames api;
    private final @NotNull List<ImmutableMap<?>> localMapList;

    /**
     * Used to create a new map manager
     * for an api connection.
     *
     * @param api The instance of the api connection.
     */
    public MapManager(@NotNull CozyGames api) {
        this.api = api;
        this.localMapList = new ArrayList<>();
    }

    public @NotNull List<ImmutableMap<?>> getLocalMapList() {
        return this.localMapList;
    }

    public @NotNull List<ImmutableMap<?>> getLocalMapList(@NotNull String gameIdentifier) {
        return this.localMapList.stream()
                .filter(map -> map.getGameIdentifier().equals(gameIdentifier))
                .toList();
    }

    public @NotNull MapManager registerMap(@NotNull ImmutableMap<?> map) {
        this.localMapList.add(map);
        return this;
    }

    public @NotNull MapManager unregisterMap(@NotNull ImmutableMap<?> map) {
        this.localMapList.remove(map);
        return this;
    }

    public @NotNull MapManager unregisterMap(@NotNull String identifier) {
        this.localMapList.removeIf(map -> map.getGameIdentifier().equals(identifier));
        return this;
    }

    public @NotNull Optional<GlobalMap> getGlobalMap(@NotNull String identifier) {
        return this.api.getDatabase()
                .getTable(MapTable.class)
                .getMapRecord(identifier)
                .map(MapRecord::convert);
    }

    public @NotNull Optional<GlobalMap> getGlobalMap(@NotNull String serverName, @NotNull String gameIdentifier, @NotNull String mapName) {
        final String identifier = Map.getIdentifier(serverName, gameIdentifier, mapName);
        return this.getGlobalMap(identifier);
    }

    public @NotNull List<GlobalMap> getGlobalMapList() {
        return this.api.getDatabase()
                .getTable(MapTable.class)
                .getRecordList()
                .stream()
                .map(MapRecord::convert)
                .toList();
    }

    public @NotNull List<GlobalMap> getGlobalMapList(@NotNull String serverName) {
        return this.getGlobalMapList().stream()
                .filter(map -> map.getServerName().equalsIgnoreCase(serverName))
                .toList();
    }

    public @NotNull List<GlobalMap> getGlobalMapList(@NotNull String serverName, @NotNull String gameIdentifier) {
        return this.getGlobalMapList().stream()
                .filter(map -> map.getServerName().equalsIgnoreCase(serverName)
                        && map.getGameIdentifier().equalsIgnoreCase(gameIdentifier))
                .toList();
    }
}
