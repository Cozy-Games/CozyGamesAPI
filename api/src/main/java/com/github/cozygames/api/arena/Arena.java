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

import com.github.cozygames.api.group.GroupSize;
import com.github.cozygames.api.indicator.Savable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents the base arena class.
 * <p>
 * Represents a mini-game arena that can be created.
 */
public abstract class Arena implements Savable<Arena> {

    private @NotNull String identifier;
    private @NotNull String serverName;
    private @NotNull String gameIdentifier;

    private @Nullable GroupSize groupSize;
    private @Nullable String mapName;
    private @Nullable ConfigurationSection mapItemConfiguration;

    /**
     * Used to create a new instance of an arena.
     *
     * @param identifier     The arena's identifier.
     * @param serverName     The server the arena can be initialized on.
     * @param gameIdentifier The game identifier.
     */
    public Arena(@NotNull String identifier,
                 @NotNull String serverName,
                 @NotNull String gameIdentifier) {

        this.identifier = identifier;
        this.serverName = serverName;
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * The arena type's unique identifier.
     *
     * @return
     */
    public @NotNull String getIdentifier() {
        return this.identifier;
    }

    public @NotNull String getServerName() {
        return this.serverName;
    }

    public @NotNull String getGameIdentifier() {
        return this.gameIdentifier;
    }

    public @NotNull Optional<GroupSize> getGroupSize() {
        return Optional.ofNullable(this.groupSize);
    }

    public @NotNull Optional<String> getMapName() {
        return Optional.ofNullable(this.mapName);
    }

    public @NotNull Optional<ConfigurationSection> getMapItemConfiguration() {
        return Optional.ofNullable(this.mapItemConfiguration);
    }

    public @NotNull Arena setGroupSize(@NotNull GroupSize groupSize) {
        this.groupSize = groupSize;
        return this;
    }

    public @NotNull Arena setMapName(@NotNull String mapName) {
        this.mapName = mapName;
        return this;
    }

    public @NotNull Arena setMapItemConfiguration(@NotNull ConfigurationSection mapItemConfiguration) {
        this.mapItemConfiguration = mapItemConfiguration;
        return this;
    }
}