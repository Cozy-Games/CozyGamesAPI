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

package com.github.cozygames.api.database.record;

import com.github.cozygames.api.item.Item;
import com.github.cozygames.api.map.GlobalMap;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.indicator.RecordConvertable;
import com.github.cozygames.api.map.MemberCapacity;
import com.github.cozygames.api.schematic.Schematic;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Represents the arena record.
 * <p>
 * Represents the exact data that will be stored
 * in each arena record in the database.
 */
public class MapRecord extends Record implements RecordConvertable<Map> {

    /**
     * Final variables.
     * <p>
     * These variables will not change once created.
     */
    public String name;
    public String serverName;
    public String gameIdentifier;

    /**
     * Changeable variables.
     * <p>
     * These variables may be changed in the database.
     */
    public String schematicClass;
    public String capacityClass;
    public String itemClass;

    @Override
    public @NotNull Map<?> convert() {
        GlobalMap map = new GlobalMap(this.name, this.serverName, this.gameIdentifier);

        if (schematicClass != null) map.setSchematic(new Schematic().convert(this.asConfigurationSection(schematicClass)));
        if (capacityClass != null) map.setCapacity(new MemberCapacity().convert(this.asConfigurationSection(capacityClass)));
        if (itemClass != null) map.setItem(new Item().convert(this.asConfigurationSection(itemClass)));

        return map;
    }

    private @Nullable ConfigurationSection asConfigurationSection(@Nullable String json) {
        if (json == null) return null;
        return new MemoryConfigurationSection(new Gson().fromJson(json, HashMap.class));
    }
}