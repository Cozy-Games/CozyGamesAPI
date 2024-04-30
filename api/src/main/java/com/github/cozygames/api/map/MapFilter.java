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

import com.github.cozygames.api.member.MemberCapacity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The map filter.
 * <p>
 * Used to filter {@link Map}'s and get a filtered list.
 */
public class MapFilter {

    protected @Nullable String mapIdentifierFilter;
    protected @Nullable String mapNameFilter;
    protected @Nullable String serverNameFilter;
    protected @Nullable String gameIdentifierFilter;
    protected @Nullable Integer maximumSessionAmountFilter;
    protected @Nullable MemberCapacity memberCapacityFilter;

    public @NotNull MapFilter setMapIdentifierFilter(@Nullable String mapIdentifierFilter) {
        this.mapIdentifierFilter = mapIdentifierFilter;
        return this;
    }

    public @NotNull MapFilter setMapNameFilter(@Nullable String mapNameFilter) {
        this.mapNameFilter = mapNameFilter;
        return this;
    }

    public @NotNull MapFilter setServerNameFilter(@Nullable String serverNameFilter) {
        this.serverNameFilter = serverNameFilter;
        return this;
    }

    public @NotNull MapFilter setGameIdentifierFilter(@Nullable String gameIdentifierFilter) {
        this.gameIdentifierFilter = gameIdentifierFilter;
        return this;
    }

    public @NotNull MapFilter setMaximumSessionAmountFilter(@Nullable Integer maximumSessionAmountFilter) {
        this.maximumSessionAmountFilter = maximumSessionAmountFilter;
        return this;
    }

    public @NotNull MapFilter setMemberCapacityFilter(@Nullable MemberCapacity memberCapacityFilter) {
        this.memberCapacityFilter = memberCapacityFilter;
        return this;
    }

    /**
     * Used to filter a list of maps.
     *
     * @param mapList The map list to filter.
     * @param <M>     The map type that is being filtered.
     * @return The filtered list.
     */
    public <M extends Map<?>> @NotNull List<M> filterMaps(@NotNull List<M> mapList) {
        return mapList.stream().filter(map -> {
            if (this.mapIdentifierFilter != null && !map.getIdentifier().equalsIgnoreCase(this.mapIdentifierFilter)) {
                return false;
            }
            if (this.mapNameFilter != null && !map.getName().equalsIgnoreCase(this.mapNameFilter)) {
                return false;
            }
            if (this.serverNameFilter != null && !map.getServerName().equalsIgnoreCase(this.serverNameFilter)) {
                return false;
            }
            if (this.gameIdentifierFilter != null && !map.getGameIdentifier().equalsIgnoreCase(this.gameIdentifierFilter)) {
                return false;
            }
            if (this.maximumSessionAmountFilter != null && map.getMaximumSessionAmount() != this.maximumSessionAmountFilter) {
                return false;
            }
            return this.memberCapacityFilter == null || (map.getCapacity().isPresent() && map.getCapacity().orElseThrow().contains(this.memberCapacityFilter));
        }).toList();
    }
}
