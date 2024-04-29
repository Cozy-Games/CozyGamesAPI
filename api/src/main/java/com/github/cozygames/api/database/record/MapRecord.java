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

import com.github.cozygames.api.indicator.RecordConvertable;
import com.github.cozygames.api.item.ItemMaterial;
import com.github.cozygames.api.location.Position;
import com.github.cozygames.api.map.GlobalMap;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.member.MemberCapacity;
import com.github.cozygames.api.schematic.Schematic;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Represents the map record.
 * <p>
 * Contains the general infomation about an
 * {@link Map} instance.
 */
public class MapRecord extends Record implements RecordConvertable<GlobalMap> {

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
    public int maximumSessionAmount;
    public String schematicClass;
    public String capacityClass;
    public String itemMaterialEnum;
    public String spawnPointPositionClass;

    @Override
    public @NotNull GlobalMap convert() {
        GlobalMap map = new GlobalMap(this.name, this.serverName, this.gameIdentifier);

        if (schematicClass != null) {
            map.setSchematic(new Schematic().convert(this.asConfigurationSection(schematicClass)));
        }

        if (capacityClass != null) {
            map.setCapacity(new MemberCapacity().convert(this.asConfigurationSection(capacityClass)));
        }

        if (itemMaterialEnum != null) {
            map.setItemMaterial(ItemMaterial.valueOf(this.itemMaterialEnum.toUpperCase()));
        }

        if (spawnPointPositionClass != null) {
            map.setSpawnPoint(new Position(0, 0, 0).convert(this.asConfigurationSection(spawnPointPositionClass)));
        }

        return map;
    }

    /**
     * Used to convert a json string into a configuration section.
     *
     * @param json The nullable json string.
     * @return The nullable configuration section.
     */
    private @Nullable ConfigurationSection asConfigurationSection(@Nullable String json) {
        if (json == null) return null;
        return new MemoryConfigurationSection(new Gson().fromJson(json, HashMap.class));
    }
}
