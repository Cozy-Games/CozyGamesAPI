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

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.database.table.MapTable;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.item.Item;
import com.github.cozygames.api.location.Position;
import com.github.cozygames.api.schematic.Schematic;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Represents the base map class.
 * <p>
 * Represents a mini-game map that can be created.
 * <p>
 * Can be used to create a new arena.
 *
 * @param <T> The top map class.
 */
public abstract class Map<T extends Map<T>> implements Savable<T>, ConfigurationConvertable<T> {

    private final @NotNull String name;
    private final @NotNull String serverName;
    private final @NotNull String gameIdentifier;

    private @Nullable Schematic schematic;
    private @Nullable MemberCapacity capacity;
    private @Nullable Item item;

    private @Nullable Position spawnPoint;

    /**
     * Used to create a new instance of a map.
     * <p>
     * A map can be used to create a new arena.
     *
     * @param name           The map's name.
     * @param serverName     The server the arena can be initialized on.
     * @param gameIdentifier The game identifier.
     */
    public Map(@NotNull String name,
               @NotNull String serverName,
               @NotNull String gameIdentifier) {

        this.name = name;
        this.serverName = serverName;
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * Used to get the instance of the cozy games api.
     * <p>
     * This should be used within the map class so the
     * service manager on some platforms can be used.
     *
     * @return The cozy games api instance.
     */
    public abstract @NotNull CozyGames getAPI();

    /**
     * The base method used to activate the map.
     * <p>
     * This will create a session for the group of players and begin a game.
     * <p>
     * This will ignore if it should create a new game. Checking if a game
     * should begin should be checked before calling this method.
     *
     * @param groupIdentifier The group's identifier.
     * @return This instance.
     */
    public abstract @NotNull T createSession(@NotNull String groupIdentifier);

    /**
     * Used to save the map to the location configuration.
     * <p>
     * This method is called in the {@link Map#save()} method.
     */
    public abstract void saveToLocalConfiguration();

    /**
     * The map's unique identifier.
     * <p>
     * To create this identifier the server name, game identifier
     * and map name is combined and split by colons.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium
     * }</pre>
     *
     * @return The arena's identifier.
     */
    public @NotNull String getIdentifier() {
        return this.serverName + ":" + this.gameIdentifier + ":" + this.name;
    }

    /**
     * The map's unique name.
     * <p>
     * The name is not unique between game types and servers.
     * To obtain a truly unique key, use the
     * {@link Map#getIdentifier()} method.
     *
     * @return The map's name.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * The server the map will be located on.
     *
     * @return The server's name.
     */
    public @NotNull String getServerName() {
        return this.serverName;
    }

    /**
     * The type of game that will be played
     * when the map is created.
     *
     * @return The game identifier.
     */
    public @NotNull String getGameIdentifier() {
        return this.gameIdentifier;
    }

    /**
     * Used to get the instance of the map's schematic.
     * <p>
     * This can be used to build the map in a specific
     * location on the server.
     *
     * @return The optional schematic.
     */
    public @NotNull Optional<Schematic> getSchematic() {
        return Optional.ofNullable(this.schematic);
    }

    /**
     * The group sizes that the map can accommodate.
     *
     * @return The optional capacity.
     */
    public @NotNull Optional<MemberCapacity> getCapacity() {
        return Optional.ofNullable(this.capacity);
    }

    /**
     * Used to get the display item.
     * <p>
     * This item can be used in a gui to represent the map.
     *
     * @return The optional item.
     */
    public @NotNull Optional<Item> getItem() {
        return Optional.ofNullable(this.item);
    }

    /**
     * Used to set the map's schematic.
     * <p>
     * The schematic is used to build the map in a specific location on the server.
     *
     * @param schematic The instance of the schematic.
     * @return This instance.
     */
    public @NotNull T setSchematic(@Nullable Schematic schematic) {
        this.schematic = schematic;
        return (T) this;
    }

    /**
     * Used to set the map's accommodating group sizes.
     * <p>
     * This determines if a group of a specific size
     * can use this map.
     *
     * @param capacity The member capacity.
     * @return This instance.
     */
    public @NotNull T setCapacity(@Nullable MemberCapacity capacity) {
        this.capacity = capacity;
        return (T) this;
    }

    /**
     * Used to set the display item.
     * <p>
     * This item can be used in a gui to represent the map.
     *
     * @param item The instance of the item.
     * @return The instance of the map.
     */
    public @NotNull T setItem(@Nullable Item item) {
        this.item = item;
        return (T) this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("name", this.name);
        section.set("serverName", this.serverName);
        section.set("gameIdentifier", this.gameIdentifier);

        if (this.schematic != null) section.set("schematic", this.schematic.convert().getMap());
        if (this.capacity != null) section.set("capacity", this.capacity.convert().getMap());
        if (this.item != null) section.set("item", this.item.convert().getMap());

        return section;
    }

    @Override
    public @NotNull T convert(@NotNull ConfigurationSection section) {

        if (section.getKeys().contains("schematic"))
            this.schematic = new Schematic().convert(section.getSection("schematic"));
        if (section.getKeys().contains("capacity"))
            this.capacity = new MemberCapacity().convert(section.getSection("capacity"));
        if (section.getKeys().contains("item")) this.item = new Item().convert(section.getSection("item"));

        return (T) this;
    }

    @Override
    public @NotNull T save() {

        // Save to the database.
        this.getAPI().getDatabase()
                .getTable(MapTable.class)
                .insertMap(this);

        // Save to the plugin's local configuration
        // if it exists.
        this.saveToLocalConfiguration();
        return (T) this;
    }
}