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

import com.github.cozygames.api.database.table.ArenaTable;
import com.github.cozygames.api.database.table.MapTable;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.map.ImmutableMap;
import com.github.cozygames.api.map.Map;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Represents an arena.
 * <p>
 * A map that has been loaded into a world.
 *
 * @param <A> The top arena.
 * @param <M> The map type.
 */
public abstract class Arena<A extends ImmutableArena<A, M>, M extends ImmutableMap<M>>
        extends ImmutableArena<A, M>
        implements ConfigurationConvertable<A>, Savable<A> {

    private @Nullable String groupIdentifier;

    /**
     * Used to create a new arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public Arena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    /**
     * Used to save the arena to the location configuration.
     * <p>
     * This method is called in the {@link Arena#save()} method.
     */
    public abstract void saveToLocalConfiguration();

    public @NotNull Optional<String> getGroupIdentifier() {
        return Optional.ofNullable(groupIdentifier);
    }

    public @NotNull A setGroupIdentifier(@Nullable String groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
        return (A) this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("map_identifier", this.getMapIdentifier());
        section.set("world_name", this.getWorldName());

        if (this.groupIdentifier != null) section.set("group_identifier", this.groupIdentifier);

        return section;
    }

    @Override
    public @NotNull A convert(@NotNull ConfigurationSection section) {

        if (section.getKeys().contains("group_identifier")) this.groupIdentifier = section.getString("group_identifier");

        return (A) this;
    }

    @Override
    public @NotNull A save() {

        // Save to the database.
        this.getAPI().getDatabase()
                .getTable(ArenaTable.class)
                .insertArena(this);

        // Save to the plugin's local configuration
        // if it exists.
        this.saveToLocalConfiguration();
        return (A) this;
    }

    /**
     * Used to get the arena identifier associated with a
     * map identifier and world name.
     * <p>
     * This is the map identifier and world name
     * seperated with a colon.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium:world_bedwars_aquarium_1
     * }</pre>
     *
     * @param mapIdentifier The map identifier.
     * @param worldName     The world name.
     * @return The arena identifier.
     */
    public static @NotNull String getIdentifier(@NotNull String mapIdentifier, @NotNull String worldName) {
        return mapIdentifier + ":" + worldName;
    }
}
