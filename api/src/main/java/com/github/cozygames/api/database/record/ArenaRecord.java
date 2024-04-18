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

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.indicator.RecordConvertable;
import com.github.smuddgge.squishydatabase.record.Record;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the arena record.
 * <p>
 * Represents the exact data that will be stored
 * in each arena record in the database.
 */
public class ArenaRecord extends Record implements RecordConvertable<Arena> {

    /**
     * Final variables.
     * <p>
     * These variables will not change once created.
     */
    public String identifier;
    public String serverName;
    public String gameIdentifier;
    public String playerSizeClass;
    public String mapName;
    public String mapItemClass;

    /**
     * Changeable variables.
     * <p>
     * These variables may be changed in the database.
     */
    public String groupIdentifier;

    @Override
    public @NotNull Arena convert() {
        return null;
    }
}
