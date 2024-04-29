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

import com.github.cozygames.api.database.record.MapRecord;
import com.github.cozygames.api.map.Map;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents the arena table.
 * <p>
 * Used to read and write {@link MapRecord}'s
 * to the database.
 */
public class MapTable extends TableAdapter<MapRecord> {

    @Override
    public @NotNull String getName() {
        return "maps";
    }

    /**
     * Used to get a map record.
     * <p>
     * Example identifier:
     * <pre>{@code
     * server1:bedwars:aquarium
     * }</pre>
     *
     * @param identifier The map identifier.
     * @return The map record instance.
     */
    public @NotNull Optional<MapRecord> getMapRecord(@NotNull String identifier) {
        final String serverName = identifier.split(":")[0];
        final String gameIdentifier = identifier.split(":")[1];
        final String mapName = identifier.split(":")[2];

        // Get the first record with the correct identifier.
        MapRecord record = this.getFirstRecord(new Query()
                .match("name", mapName)
                .match("serverName", serverName)
                .match("gameIdentifier", gameIdentifier)
        );

        return Optional.ofNullable(record);
    }

    /**
     * Used to insert a map into the database table.
     *
     * @param map The instance of the map.
     * @return This instance.
     */
    public @NotNull MapTable insertMap(Map<?> map) {

        // Create the map record.
        MapRecord record = new MapRecord();
        record.name = map.getName();
        record.serverName = map.getServerName();
        record.gameIdentifier = map.getGameIdentifier();

        record.maximumSessionAmount = map.getMaximumSessionAmount();
        record.schematicClass = this.asJson(map.getSchematic().orElse(null));
        record.capacityClass = this.asJson(map.getCapacity().orElse(null));
        map.getItemMaterial().ifPresent(material -> record.itemMaterialEnum = material.name());
        record.spawnPointPositionClass = this.asJson(map.getSpawnPoint().orElse(null));

        // Insert the record.
        this.insertRecord(record);
        return this;
    }

    private @Nullable String asJson(@Nullable ConfigurationConvertable<?> convertable) {
        if (convertable == null) return null;
        return new Gson().toJson(convertable.asMap());
    }

    /**
     * Used to remove a map from the database.
     *
     * @param map The map instance to remove.
     * @return This instance.
     */
    public @NotNull MapTable removeMap(Map<?> map) {

        // Get the first record with the correct identifier.
        this.removeAllRecords(new Query()
                .match("name", map.getName())
                .match("serverName", map.getServerName())
                .match("gameIdentifier", map.getGameIdentifier())
        );

        return this;
    }
}
