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

package com.github.cozygames.api.configuration;

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.map.MapFactory;
import com.github.smuddgge.squishyconfiguration.directory.SingleTypeConfigurationDirectory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents the map configuration directory.
 * <p>
 * Used to store the possible maps that will be
 * loaded when the plugin is loaded.
 *
 * @param <M> The map object that will be stored.
 */
public class MapConfiguration<M extends Map<M>> extends SingleTypeConfigurationDirectory<M> {

    private final @NotNull MapFactory<M> mapFactory;

    /**
     * Used to create a new map configuration directory.
     *
     * @param resourceClass The class used to load the resource files.
     * @param factory       The map factory.
     */
    public MapConfiguration(@NotNull Class<?> resourceClass, @NotNull MapFactory<M> factory) {
        super("maps", resourceClass);

        this.mapFactory = factory;
    }

    @Override
    public @NotNull M createEmpty(@NotNull String identifier) {
        return this.mapFactory.create(identifier);
    }

    @Override
    public @NotNull Optional<M> getType(@NotNull String name) {
        return super.getType(name);
    }

    @Override
    public @NotNull SingleTypeConfigurationDirectory<M> insertType(@NotNull String name, @NotNull M type) {
        return super.insertType(name, type);
    }

    @Override
    public @NotNull SingleTypeConfigurationDirectory<M> removeType(@NotNull String name) {
        return super.removeType(name);
    }
}
