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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the map manager.
 * <p>
 * Used to register, unregister and get available {@link Map}'s.
 * <p>
 * When a map is registered the map will be available for
 * players to play.
 */
public class MapManager {

    private final @NotNull CozyGames api;
    private final @NotNull List<String> localRegisteredMapList;

    /**
     * Used to create a new arena manager.
     *
     * @param api The instance of the api.
     */
    public MapManager(@NotNull CozyGames api) {
        this.api = api;
        this.localRegisteredMapList = new ArrayList<>();
    }

    /**
     * The list of local arena identifier's that are available
     * for players to play.
     *
     * @return The local registered arenas.
     */
    public @NotNull List<String> getLocalRegisteredMapList() {
        return this.localRegisteredMapList;
    }

    /**
     * Used to get the instance of a locally registered map.
     * <p>
     * This will be obtained from a locally registered
     * plugins map configuration.
     *
     * @param mapIdentifier The map identifier to look for.
     * @return The optional map.
     */
    public @NotNull Optional<Map<?>> getLocalMap(@NotNull String mapIdentifier) {
        for (CozyGamesPlugin<?, ?, ?, ?> plugin : this.api.getLocalPlugins()) {
            for (Map<?> map : plugin.getMapConfiguration().getAllTypes()) {
                if (map.getIdentifier().equals(mapIdentifier)) return Optional.of(map);
            }
        }
        return Optional.empty();
    }

    /**
     * Used to register a map.
     * <p>
     * This will let players play the map.
     *
     * @param mapIdentifier The map's identifier.
     * @return This instance.
     */
    public @NotNull MapManager registerMap(@NotNull String mapIdentifier) {
        this.localRegisteredMapList.add(mapIdentifier);
        return this;
    }

    /**
     * Used to unregister a map if it exists.
     * <p>
     * This will stop player's from playing the map.
     *
     * @param mapIdentifier The map's identifier.
     * @return This instance.
     */
    public @NotNull MapManager unregisterMap(@NotNull String mapIdentifier) {
        this.localRegisteredMapList.remove(mapIdentifier);
        return this;
    }

    /**
     * Used to get the list of global maps.
     *
     * @return The list of global maps.
     */
    public @NotNull List<GlobalMap> getMapList() {
        return this.api.getDatabase()
                .getTable(MapTable.class)
                .getRecordList()
                .stream()
                .map(MapRecord::convert)
                .toList();
    }

    /**
     * Used to get a filtered list of global maps.
     *
     * @param mapFilter The filter that should be used.
     * @return The list of global maps.
     */
    public @NotNull List<GlobalMap> getMapList(@NotNull MapFilter mapFilter) {
        return mapFilter.filterMaps(this.getMapList());
    }

    /**
     * Used to get a map based on its identifier.
     * <p>
     * This is recommended over using a filter as it is
     * less memory costing.
     *
     * @param mapIdentifier The map's identifier.
     * @return The optional global map.
     */
    public @NotNull Optional<GlobalMap> getMap(@NotNull String mapIdentifier) {
        return this.api.getDatabase()
                .getTable(MapTable.class)
                .getMapRecord(mapIdentifier)
                .map(MapRecord::convert);
    }

    /**
     * Used to get the first global map using a filter.
     *
     * @param mapFilter The filter that should be used.
     * @return The first global map that matches the filter.
     */
    public @NotNull Optional<GlobalMap> getFirstMap(@NotNull MapFilter mapFilter) {
        return Optional.ofNullable(mapFilter.filterMaps(this.getMapList()).get(0));
    }
}