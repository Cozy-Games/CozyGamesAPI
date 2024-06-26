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

import org.jetbrains.annotations.NotNull;

/**
 * The type of group.
 * <p>
 * This is hard coded into the class so the
 * record can convert it back into the correct class.
 */
public enum GroupType {
    BASIC(Group.class);

    private final @NotNull Class<? extends Group> clazz;

    /**
     * Used to create a group type.
     *
     * @param clazz The type of class the group type
     *              will be hard coded in.
     */
    GroupType(@NotNull Class<? extends Group> clazz) {
        this.clazz = clazz;
    }

    /**
     * Used to get the instance of the class this
     * group type is hard coded in.
     *
     * @return The class type.
     */
    public @NotNull Class<? extends Group> getGroupClass() {
        return this.clazz;
    }
}
