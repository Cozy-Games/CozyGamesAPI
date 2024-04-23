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

import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the member amounts that can play the map.
 * <p>
 * If the amount of members is not listed in this class
 * they should not be able to play the map.
 */
public class MemberCapacity implements ConfigurationConvertable<MemberCapacity> {

    private final @NotNull List<Integer> possibleCapacityList;

    /**
     * Used to create a new empty member
     * capacity class.
     */
    public MemberCapacity() {
        this.possibleCapacityList = new ArrayList<>();
    }

    /**
     * Used to create a member capacity class.
     *
     * @param possibleCapacityList The list of possible capacity's to
     *                             initialise the list with. This list
     *                             will be cloned.
     */
    public MemberCapacity(@NotNull List<Integer> possibleCapacityList) {
        this.possibleCapacityList = new ArrayList<>(possibleCapacityList);
    }

    /**
     * Get the list of possible member capacity's the map can take.
     *
     * @return The list of possible capacity's.
     */
    public @NotNull List<Integer> getPossibleCapacityList() {
        return this.possibleCapacityList;
    }

    /**
     * Used to add a possible capacity to the list.
     *
     * @param capacity The capacity to add.
     * @return This instance.
     */
    public @NotNull MemberCapacity addPossibleCapacity(int capacity) {
        this.possibleCapacityList.add(capacity);
        return this;
    }

    /**
     * Used to add a possible capacity list to the
     * current list of possible capacity's.
     *
     * @param possibleCapacityList The possible capacity's to add.
     * @return This instance.
     */
    public @NotNull MemberCapacity addPossibleCapacityList(@NotNull List<Integer> possibleCapacityList) {
        this.possibleCapacityList.addAll(possibleCapacityList);
        return this;
    }

    /**
     * Used to remove a possible capacity.
     *
     * @param capacity The capacity to remove.
     * @return This instance.
     */
    public @NotNull MemberCapacity removePossibleCapacity(int capacity) {
        this.possibleCapacityList.remove(capacity);
        return this;
    }

    /**
     * Used to remove a collection of possible capacity's.
     *
     * @param possibleCapacityList The list of possible capacity's to remove.
     * @return This instance.
     */
    public @NotNull MemberCapacity removePossibleCapacityList(@NotNull List<Integer> possibleCapacityList) {
        this.possibleCapacityList.removeAll(possibleCapacityList);
        return this;
    }

    /**
     * Used to check if an amount of members is possible.
     *
     * @param memberAmount The amount of member's to check.
     * @return True if the member amount is possible.
     */
    public boolean isPossible(int memberAmount) {
        return this.possibleCapacityList.contains(memberAmount);
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        return null;
    }

    @Override
    public @NotNull MemberCapacity convert(@NotNull ConfigurationSection configurationSection) {
        return null;
    }
}
