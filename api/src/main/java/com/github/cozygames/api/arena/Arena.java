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

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.configuration.ArenaConfiguration;
import com.github.cozygames.api.database.table.ArenaTable;
import com.github.cozygames.api.group.Group;
import com.github.cozygames.api.indicator.Deletable;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.map.Map;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the base arena.
 * <p>
 * A map that has been loaded into a world on a server.
 *
 * @param <A> The highest arena instance that should be used as a return value.
 *            This makes it easier to chane method calls.
 * @param <M> The map type this arena is created from.
 */
public abstract class Arena<A extends Arena<A, M>, M extends Map<M>>
        implements ConfigurationConvertable<A>, Savable<A>, Deletable<A> {

    private final @NotNull String mapIdentifier;
    private final @NotNull String worldName;

    private @Nullable UUID groupIdentifier;

    /**
     * Used to create an instance of an arena.
     *
     * @param mapIdentifier The map identifier.
     * @param worldName     The name of the world the arena is located in.
     */
    public Arena(@NotNull String mapIdentifier, @NotNull String worldName) {
        this.mapIdentifier = mapIdentifier;
        this.worldName = worldName;
    }

    /**
     * Used to create an instance of an arena.
     *
     * @param identifier The arena's identifier. This can be provided by
     *                   the {@link Arena#getIdentifier(String, String)} method.
     */
    public Arena(@NotNull String identifier) {
        this(Map.getIdentifier(identifier), identifier.split(":")[3]);
    }

    /**
     * The instance of the api that should be
     * used within this class.
     * <p>
     * This should be used in case there is a service
     * provider on the platform.
     * <p>
     * Mainly used in the {@link Arena#save()} method.
     *
     * @return The api instance.
     */
    public abstract @NotNull CozyGames getApi();

    /**
     * Used to get the instance of the map
     * associated with this arena.
     * <p>
     * This uses the storage in case the map is updated.
     *
     * @return The instance of the map.
     * @throws NoSuchElementException When the map no longer exists.
     */
    public abstract @NotNull M getMap();

    /**
     * Used to create the world if it doesn't
     * exist on the server.
     *
     * @return This instance.
     */
    public abstract @NotNull A createWorld();

    /**
     * Used to delete the world if it exists
     * on the server.
     *
     * @return This instance.
     */
    public abstract @NotNull A deleteWorld();

    /**
     * Used to activate the arena and begin a game session.
     *
     * @param groupIdentifier The group identifier that will be
     *                        playing the game.
     */
    public abstract @NotNull A activate(@NotNull UUID groupIdentifier);

    /**
     * Used to deactivate the arena.
     * <p>
     * This will stop the game session.
     */
    public abstract @NotNull A deactivate();

    /**
     * Used to save this instance to the local
     * {@link ArenaConfiguration} instance.
     *
     * @return This instance.
     */
    public abstract @NotNull A saveToLocalConfiguration();

    /**
     * Used to delete this from the local
     * {@link ArenaConfiguration} instance.
     *
     * @return This instance.
     */
    public abstract @NotNull A deleteFromLocalConfiguration();

    /**
     * Used to get the arena's unique identifier.
     * <p>
     * Created using the map identifier and world
     * name seperated with colons.
     * <p>
     * You can create this using the method
     * {@link Arena#getIdentifier(String, String)}.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium:world_bedwars_aquarium_1
     * }</pre>
     *
     * @return The arena's identifier.
     */
    public @NotNull String getIdentifier() {
        return Arena.getIdentifier(this.mapIdentifier, this.worldName);
    }

    /**
     * Used to get the map identifier.
     * <p>
     * This can be used to get the map instance.
     *
     * @return The map identifier.
     */
    public @NotNull String getMapIdentifier() {
        return this.mapIdentifier;
    }

    /**
     * Used to get the world name.
     *
     * @return The world name.
     */
    public @NotNull String getWorldName() {
        return this.worldName;
    }

    /**
     * Used to get the group's identifier that is using the arena.
     *
     * @return The optional group identifier.
     */
    public @NotNull Optional<UUID> getGroupIdentifier() {
        return Optional.ofNullable(this.groupIdentifier);
    }

    /**
     * Used to get the instance of the group using
     * {@link CozyGames#getGroupManager()#getGroup()}.
     *
     * @return The optional group instance.
     */
    public @NotNull Optional<Group> getGroup() {
        if (this.groupIdentifier == null) return Optional.empty();
        return this.getApi().getGroupManager().getGroup(this.groupIdentifier);
    }

    /**
     * Used to set the group identifier.
     * <p>
     * Setting this to null will render the arena free.
     *
     * @param groupIdentifier The new group identifier.
     * @return This instance.
     */
    public @NotNull A setGroupIdentifier(@Nullable UUID groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
        return (A) this;
    }

    /**
     * Used to check if the arena is active.
     *
     * @return True if the arena is active.
     */
    public boolean isActive() {
        return this.groupIdentifier != null;
    }

    /**
     * Used to check if the arena is not active.
     *
     * @return True if not active.
     */
    public boolean isDeactivated() {
        return this.groupIdentifier == null;
    }

    /**
     * Used to get the arena identifier that represents
     * a map identifier and world name.
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

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("map_identifier", this.mapIdentifier);
        section.set("world_name", this.worldName);

        if (this.groupIdentifier != null) {
            section.set("group_identifier", this.groupIdentifier.toString());
        }

        return section;
    }

    @Override
    public @NotNull A convert(@NotNull ConfigurationSection section) {

        if (section.getKeys().contains("group_identifier")) {
            this.setGroupIdentifier(UUID.fromString(section.getString("group_identifier")));
        }

        return (A) this;
    }

    @Override
    public @NotNull A save() {

        // Save to the database.
        this.getApi().getDatabase()
                .getTable(ArenaTable.class)
                .insertArena(this);

        // Save to local configuration.
        this.saveToLocalConfiguration();
        return (A) this;
    }

    @Override
    public @NotNull A delete() {

        // Delete from the database.
        this.getApi().getDatabase()
                .getTable(ArenaTable.class)
                .removeArena(this.getIdentifier());

        // Delete from local configuration.
        this.deleteFromLocalConfiguration();
        return (A) this;
    }
}
