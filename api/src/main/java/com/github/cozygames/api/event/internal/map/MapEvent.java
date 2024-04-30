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

package com.github.cozygames.api.event.internal.map;

import com.github.cozygames.api.event.internal.InternalEvent;
import com.github.cozygames.api.map.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a map event.
 * <p>
 * All map events should implement this interface.
 */
public abstract class MapEvent extends InternalEvent {

    private boolean isComplete = false;

    /**
     * Used to get the maps identifier which can
     * be used for retrieving the map.
     *
     * @return The map identifier.
     */
    public abstract @NotNull String getMapIdentifier();

    /**
     * Used to execute the method of the local {@link Map}.
     * <p>
     * This is what will be called by the server that
     * contains the local {@link Map}.
     *
     * @param map The instance of the local {@link Map}.
     * @return This instance.
     */
    public abstract @NotNull MapEvent executeMethod(@NotNull Map<?> map);

    @Override
    public @NotNull InternalEvent complete() {
        this.isComplete = true;
        return this;
    }

    @Override
    public @NotNull InternalEvent setComplete(boolean isComplete) {
        this.isComplete = isComplete;
        return this;
    }

    @Override
    public boolean isComplete() {
        return this.isComplete;
    }
}
