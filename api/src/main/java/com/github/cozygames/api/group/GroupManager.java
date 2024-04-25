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

package com.github.cozygames.api.group;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.database.record.GroupRecord;
import com.github.cozygames.api.database.table.GroupTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GroupManager {

    private final @NotNull CozyGames api;

    public GroupManager(@NotNull CozyGames api) {
        this.api = api;
    }

    public @NotNull List<Group> getGroupList() {
        return this.api.getDatabase()
                .getTable(GroupTable.class)
                .getRecordList()
                .stream()
                .map(GroupRecord::convert)
                .toList();
    }

    /**
     * Used to get the list of a certain group type.
     *
     * @param clazz The group type.
     * @param <T>   The list of groups.
     * @return The list of the groups that are the certain type.
     */
    public <T extends Group> @NotNull List<T> getGroupList(@NotNull Class<T> clazz) {
        for (Group group : this.getGroupList()) {
            if (clazz.isAssignableFrom(group.getClass())) {
                return (List<T>) group;
            }
        }
        return new ArrayList<>();
    }

    public @NotNull Optional<Group> getGroup(@NotNull UUID identifier) {
        return this.api.getDatabase()
                .getTable(GroupTable.class)
                .getGroupRecord(identifier)
                .map(GroupRecord::convert);
    }
}
