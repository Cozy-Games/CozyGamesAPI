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
import com.github.cozygames.api.console.Logger;
import com.github.cozygames.api.database.record.MapRecord;
import com.github.cozygames.api.database.table.MapTable;
import com.github.cozygames.api.member.MemberCapacity;
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
    private final @NotNull Logger logger;
    private final @NotNull List<String> localRegisteredMapList;

    /**
     * Used to create a new arena manager.
     *
     * @param api The instance of the api.
     */
    public MapManager(@NotNull CozyGames api) {
        this.api = api;
        this.logger = api.getPlugin().getLogger().createExtension("&7[MapManager] &5");
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
     * <p>
     * This is how the map should be added to the database.
     *
     * @param map The map instance.
     * @return This instance.
     */
    public @NotNull MapManager registerMap(@NotNull Map<?> map) {
        this.localRegisteredMapList.add(map.getIdentifier());
        map.saveToDatabase();
        this.logger.log("Registered map &f" + map.getIdentifier());
        return this;
    }

    /**
     * Used to unregister a map if it exists.
     * <p>
     * This will stop player's from playing the map
     * by removing it from the database.
     *
     * @param mapIdentifier The map's identifier.
     * @return This instance.
     */
    public @NotNull MapManager unregisterMap(@NotNull String mapIdentifier) {
        this.localRegisteredMapList.remove(mapIdentifier);
        final Map<?> map = this.getMap(mapIdentifier).orElse(null);
        if (map == null) return this;
        map.deleteFromDatabase();
        this.logger.log("Unregistered map &f" + map.getIdentifier());
        return this;
    }

    /**
     * Used to unregister maps with a specific game identifier.
     * <p>
     * This will also remove the map from the database.
     *
     * @param gameIdentifier The game identifier to filter.
     * @return This instance.
     */
    public @NotNull MapManager unregisterMapList(@NotNull String gameIdentifier) {
        this.localRegisteredMapList.removeIf(identifier -> {

            // Check if the map still exists.
            final Map<?> map = this.getMap(identifier).orElse(null);
            if (map == null) return true;

            // Check if the map equals the specific game identifier.
            if (!map.getGameIdentifier().equals(gameIdentifier)) return false;

            // Remove the map from the database.
            map.deleteFromDatabase();
            this.logger.log("Unregistered map &f" + map.getIdentifier());
            return true;
        });
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
     * Used to get the plugin's map list as a formatted
     * list to send to a member.
     *
     * @param plugin The instance of the plugin.
     * @return The formatted list of maps in the plugin.
     */
    public @NotNull List<String> getMapListFormatted(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {

        // Create the list of maps to display.
        List<String> mapList = new ArrayList<>();

        // Loop though the list of maps.
        for (Map<?> map : plugin.getApi().getMapManager().getMapList(new MapFilter()
                .setServerNameFilter(plugin.getApi().getServerName())
                .setGameIdentifierFilter(plugin.getGameIdentifier())
        )) {
            mapList.add("&7- &f" + map.getName()
                    + " &7" + map.getCapacity().map(MemberCapacity::toString).orElse("(0)")
                    + " &a" + map.getMaximumSessionAmount()
                    + " &7" + map.getPermission().orElse("No Permission Required") + "\n"
            );
        }

        return mapList;
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
