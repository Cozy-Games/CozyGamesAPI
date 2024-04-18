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

import com.github.cozygames.api.database.record.GroupRecord;
import com.github.cozygames.api.group.Group;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the group table.
 * <p>
 * Used to read and write {@link GroupRecord}'s
 * to the database.
 */
public class GroupTable extends TableAdapter<GroupRecord> {

    @Override
    public @NotNull String getName() {
        return "groups";
    }

    /**
     * Used to insert a group into the
     * group database table.
     *
     * @param group The group instance.
     */
    public void insertGroup(@NotNull Group group) {

        GroupRecord record = new GroupRecord();
        record.identifier = group.getIdentifier().toString();
        record.gameIdentifier = group.getGameIdentifier();
        record.playerUuidList = new Gson().toJson(group.getMemberUuids());
        record.groupType = group.getType().name();
        record.groupJson = new Gson().toJson(group);

        this.insertRecord(record);
    }
}
