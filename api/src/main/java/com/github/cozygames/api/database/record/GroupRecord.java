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

import com.github.cozygames.api.group.Group;
import com.github.cozygames.api.group.GroupType;
import com.github.cozygames.api.indicator.RecordConvertable;
import com.github.smuddgge.squishydatabase.record.Record;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the group record.
 * <p>
 * The collection of players that are
 * waiting to play a game.
 * <p>
 * Contains the general infomation about a
 * {@link Group} instance.
 */
public class GroupRecord extends Record implements RecordConvertable<Group> {

    /**
     * Final variables.
     * <p>
     * These variables will not change once created.
     */
    public String identifier;
    public String gameIdentifier;

    /**
     * Changeable variables.
     * <p>
     * These variables may be changed in the database.
     */
    public String playerUuidList;

    /**
     * The group type and the instance of the group class as a json string.
     * <p>
     * You can convert this back into the specific group class
     * by first converting the json back into a map. Then you can convert
     * the map into the specific group class using the group factory.
     */
    public String groupType;
    public String groupJson;

    @Override
    public @NotNull Group convert() {
        GroupType type = GroupType.valueOf(groupType.toUpperCase());
        return new Gson().fromJson(groupJson, type.getGroupClass());
    }
}
