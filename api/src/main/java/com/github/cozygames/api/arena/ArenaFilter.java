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

package com.github.cozygames.api.arena;

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.map.MapFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The arena filter.
 * <p>
 * Used to filter {@link Arena}'s, {@link Map}'s
 * and get a filtered list.
 * <p>
 * Setters in this class are used to set a specific filter
 * that will be used to match with arenas and maps.
 */
public class ArenaFilter extends MapFilter {

    protected @Nullable String arenaIdentifierFilter;
    protected @Nullable String worldNameFilter;
    protected @Nullable UUID groupIdentifierFilter;

    public @NotNull ArenaFilter setArenaIdentifierFilter(@Nullable String arenaIdentifierFilter) {
        this.arenaIdentifierFilter = arenaIdentifierFilter;
        return this;
    }

    public @NotNull ArenaFilter setWorldNameFilter(@Nullable String worldNameFilter) {
        this.worldNameFilter = worldNameFilter;
        return this;
    }

    public @NotNull ArenaFilter setGroupIdentifierFilter(@Nullable UUID groupIdentifierFilter) {
        this.groupIdentifierFilter = groupIdentifierFilter;
        return this;
    }

    /**
     * Used to filter a list of arenas.
     * <p>
     * This method will go through each arena in the list and filter
     * arenas that do no match teh filters set in this class.
     *
     * @param arenaList The arena list to filter.
     * @param <A>       The arena class that is being filtered and returned.
     * @return The new list of filtered arenas.
     */
    public <A extends Arena<A, ?>> @NotNull List<A> filterArenas(@NotNull List<A> arenaList) {
        return new ArrayList<>(arenaList).stream().filter(arena -> {

            // Check each arena variable against initialized filters.
            if (this.mapIdentifierFilter != null && !arena.getMap().getIdentifier().equalsIgnoreCase(this.mapIdentifierFilter)) {
                return false;
            }
            if (this.mapNameFilter != null && !arena.getMap().getName().equalsIgnoreCase(this.mapNameFilter)) {
                return false;
            }
            if (this.serverNameFilter != null && !arena.getMap().getServerName().equalsIgnoreCase(this.serverNameFilter)) {
                return false;
            }
            if (this.gameIdentifierFilter != null && !arena.getMap().getGameIdentifier().equalsIgnoreCase(this.gameIdentifierFilter)) {
                return false;
            }
            if (this.maximumSessionAmountFilter != null && arena.getMap().getMaximumSessionAmount() != this.maximumSessionAmountFilter) {
                return false;
            }
            if (this.memberCapacityFilter != null && (arena.getMap().getCapacity().isEmpty() || !arena.getMap().getCapacity().orElseThrow().contains(this.memberCapacityFilter))) {
                return false;
            }
            if (this.arenaIdentifierFilter != null && !arena.getIdentifier().equalsIgnoreCase(this.arenaIdentifierFilter)) {
                return false;
            }
            if (this.worldNameFilter != null && !arena.getWorldName().equalsIgnoreCase(this.worldNameFilter)) {
                return false;
            }
            return this.groupIdentifierFilter == null || (arena.getGroupIdentifier().isPresent() && !arena.getGroupIdentifier().orElseThrow().equals(this.groupIdentifierFilter));
        }).toList();
    }
}
