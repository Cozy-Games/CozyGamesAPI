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
import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.configuration.MapConfiguration;
import com.github.cozygames.api.database.table.MapTable;
import com.github.cozygames.api.indicator.Deletable;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.item.ItemMaterial;
import com.github.cozygames.api.location.Position;
import com.github.cozygames.api.member.MemberCapacity;
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
 * Can be used to create a new arena that will contain the map.
 *
 * @param <M> The highest map instance that should be used as a return value.
 *            This makes it easier to chane method calls.
 */
public abstract class Map<M extends Map<M>> implements ConfigurationConvertable<M>, Savable<M>, Deletable<M> {

    private final @NotNull String name;
    private final @NotNull String serverName;
    private final @NotNull String gameIdentifier;

    private int maximumSessionAmount;

    private @Nullable String permission;
    private @Nullable Schematic schematic;
    private @Nullable MemberCapacity capacity;
    private @Nullable ItemMaterial itemMaterial;
    private @Nullable Position spawnPoint;

    /**
     * Used to create a new map instance.
     *
     * @param name           The name of the map.
     * @param serverName     The server the map was registered on and can be created on.
     * @param gameIdentifier The game identifier that represents a game this map is used for.
     */
    public Map(@NotNull String name, @NotNull String serverName, @NotNull String gameIdentifier) {
        this.name = name;
        this.serverName = serverName;
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * The instance of the api that should be
     * used within this class.
     * <p>
     * This should be used in case there is a service
     * provider on the platform.
     * <p>
     * Mainly used in the {@link Map#save()} method.
     *
     * @return The api instance.
     */
    public abstract @NotNull CozyGames getApi();

    /**
     * The base method used to activate the map.
     * <p>
     * This will create a new arena using this map.
     * <p>
     * This will ignore if it should create a new arena. Checking if an arena
     * should be created should be checked before calling this method.
     * <p>
     * This will not create the world or the map build.
     *
     * @return This instance.
     */
    public abstract @NotNull Arena<?, M> createArena();

    /**
     * Used to save this instance to the local
     * {@link MapConfiguration} instance.
     *
     * @return This instance.
     */
    public abstract @NotNull M saveToLocalConfiguration();

    /**
     * Used to delete this from the local
     * {@link MapConfiguration} instance.
     *
     * @return This instance.
     */
    public abstract @NotNull M deleteFromLocalConfiguration();

    /**
     * Used to specifically save this map
     * to the database.
     *
     * @return This instance.
     */
    public @NotNull M saveToDatabase() {

        // Save the map to the database.
        this.getApi().getDatabase()
                .getTable(MapTable.class)
                .insertMap(this);

        return (M) this;
    }

    /**
     * Used to specifically delete this map
     * from the database.
     *
     * @return This instance.
     */
    public @NotNull M deleteFromDatabase() {

        // Delete the map from the database.
        this.getApi().getDatabase()
                .getTable(MapTable.class)
                .removeMap(this);

        return (M) this;
    }

    /**
     * Used to get the map's unique identifier.
     * <p>
     * To create this identifier the server name, game identifier
     * and map name is combined and split by colons.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium
     * }</pre>
     * <p>
     * You can also use the method {@link Map#getIdentifier(String, String, String)}
     * to generate the identifier.
     *
     * @return The instance of the map's identifier.
     */
    public @NotNull String getIdentifier() {
        return Map.getIdentifier(this.serverName, this.gameIdentifier, this.name);
    }

    /**
     * The name of the map.
     * <p>
     * This should only be unique within the server and game type.
     *
     * @return The name of the map.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * The name of the server that the map was registered on.
     * <p>
     * This is also the server that the arena and
     * session will be created.
     *
     * @return The name of the server.
     */
    public @NotNull String getServerName() {
        return this.serverName;
    }

    /**
     * The identifier of the game that will be
     * played on this map.
     *
     * @return The game identifier.
     */
    public @NotNull String getGameIdentifier() {
        return this.gameIdentifier;
    }

    /**
     * The maximum amount of sessions that are allowed to be
     * running at the same time for this map instance.
     * <p>
     * If set to <code>-1</code> there can be an unlimited amount of sessions
     * unless the global maximum is not unlimited.
     * <p>
     * This should be checked in addition to the global maximum
     * before creating a new arena and session.
     *
     * @return The maximum amount of sessions.
     */
    public int getMaximumSessionAmount() {
        return this.maximumSessionAmount;
    }

    /**
     * Used to get the permission that should be
     * checked before creating an arena.
     * <p>
     * A player/member must have this permission
     * to play the map.
     * <p>
     * This is good for disability maps but still
     * letting admins use them for testing.
     *
     * @return The optional permission.
     */
    public @NotNull Optional<String> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    /**
     * The schematic that will be used to build the map within
     * the arena/world.
     *
     * @return The optional schematic.
     */
    public @NotNull Optional<Schematic> getSchematic() {
        return Optional.ofNullable(this.schematic);
    }

    /**
     * The list of allowed amounts of members within a group
     * that can play on this map.
     *
     * @return The optional member capacity.
     */
    public @NotNull Optional<MemberCapacity> getCapacity() {
        return Optional.ofNullable(this.capacity);
    }

    /**
     * The display item's material that should be used in the
     * map selector gui.
     * <p>
     * The lore and item name will be added and formated within
     * the platform's api plugin.
     *
     * @return The optional item material.
     */
    public @NotNull Optional<ItemMaterial> getItemMaterial() {
        return Optional.ofNullable(this.itemMaterial);
    }

    /**
     * The first position within the arena's world the players
     * should be teleported to.
     *
     * @return The optional position.
     */
    public @NotNull Optional<Position> getSpawnPoint() {
        return Optional.ofNullable(this.spawnPoint);
    }

    /**
     * Used to change the maximum amount of sessions that
     * can be running at the same time for this map instance.
     * <p>
     * If set to <code>-1</code> there can be an unlimited amount of sessions
     * unless the global maximum is not unlimited.
     * <p>
     * This should be checked in addition to the global maximum
     * before creating a new arena and session.
     *
     * @param maximumSessionAmount The maximum amount of sessions.
     * @return This instance.
     */
    public @NotNull M setMaximumSessionAmount(int maximumSessionAmount) {
        this.maximumSessionAmount = maximumSessionAmount;
        return (M) this;
    }

    /**
     * Used to set the map permission.
     * <p>
     * The permission that is required by the member/player
     * to start a game on this map.
     *
     * @param permission The permission to set.
     * @return This instance.
     */
    public @NotNull M setPermission(@Nullable String permission) {
        this.permission = permission;
        return (M) this;
    }

    /**
     * Used to set the schematic instance that should be used to
     * load the map build into the arena.
     *
     * @param schematic The instance of a schematic.
     * @return This instance.
     */
    public @NotNull M setSchematic(@Nullable Schematic schematic) {
        this.schematic = schematic;
        return (M) this;
    }

    /**
     * Used to set the map's member capacity.
     *
     * @param capacity The member capacity.
     * @return This instance.
     */
    public @NotNull M setCapacity(@Nullable MemberCapacity capacity) {
        this.capacity = capacity;
        return (M) this;
    }

    /**
     * Used to set the display item's material that
     * should be used within the map selector gui.
     * <p>
     * The lore and item name will be added and formated within
     * the platform's api plugin.
     *
     * @param itemMaterial The display item's material.
     * @return This instance.
     */
    public @NotNull M setItemMaterial(@Nullable ItemMaterial itemMaterial) {
        this.itemMaterial = itemMaterial;
        return (M) this;
    }

    /**
     * Used to set the first position within the arena's world
     * the players should be teleported to.
     *
     * @param spawnPoint The spawn point position.
     * @return This instance.
     */
    public @NotNull M setSpawnPoint(@Nullable Position spawnPoint) {
        this.spawnPoint = spawnPoint;
        return (M) this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("identifier", this.getIdentifier());
        section.set("name", this.name);
        section.set("server_name", this.serverName);
        section.set("game_identifier", this.gameIdentifier);

        section.set("maximum_session_amount", this.maximumSessionAmount);

        if (this.permission != null) section.set("permission", this.permission);
        if (this.schematic != null) section.set("schematic", this.schematic.asMap());
        if (this.capacity != null) section.set("capacity", this.capacity.asMap());
        if (this.itemMaterial != null) section.set("item_material", this.itemMaterial.name());
        if (this.spawnPoint != null) section.set("spawn_point", this.spawnPoint.asMap());

        return section;
    }

    @Override
    public @NotNull M convert(@NotNull ConfigurationSection section) {

        this.maximumSessionAmount = section.getInteger("maximum_session_amount");
        this.permission = section.getString("permission", null);

        if (section.getKeys().contains("schematic")) {
            this.setSchematic(new Schematic().convert(section.getSection("schematic")));
        }

        if (section.getKeys().contains("capacity")) {
            this.setCapacity(new MemberCapacity().convert(section.getSection("capacity")));
        }

        if (section.getKeys().contains("item_material")) {
            this.setItemMaterial(ItemMaterial.valueOf(section.getString("item_material").toUpperCase()));
        }

        if (section.getKeys().contains("spawn_point")) {
            this.setSpawnPoint(new Position(0, 0, 0).convert(section.getSection("spawn_point")));
        }

        return (M) this;
    }

    /**
     * Used to update this map in the database
     * and local configuration.
     * <p>
     * If the map doesn't exist in the database it will
     * only save it to the local configuration.
     * <p>
     * For the map to be saved in the database, you must register
     * the map with the api using {@link MapManager#registerMap(Map)}.
     *
     * @return This instance.
     */
    @Override
    public @NotNull M save() {

        // Check if the map exists in the database.
        if (this.getApi().getMapManager().getMap(this.getIdentifier()).isPresent()) {

            // Save the map to the database.
            this.getApi().getDatabase()
                    .getTable(MapTable.class)
                    .insertMap(this);
        }

        // Save to the local configuration.
        this.saveToLocalConfiguration();
        return (M) this;
    }

    @Override
    public @NotNull M delete() {

        // Delete the map from the database.
        this.deleteFromDatabase();

        // Delete from the local configuration.
        this.deleteFromLocalConfiguration();
        return (M) this;
    }

    /**
     * Used to convert the three compound keys into a unique identifier.
     * <p>
     * To create this identifier the server name, game identifier
     * and map name is combined and split by colons.
     * <p>
     * Example:
     * <pre>{@code
     * server1:bedwars:aquarium
     * }</pre>
     *
     * @param serverName     The server's name.
     * @param gameIdentifier The game identifier.
     * @param mapName        The map's name.
     * @return The identifier.
     */
    public static @NotNull String getIdentifier(@NotNull String serverName, @NotNull String gameIdentifier, @NotNull String mapName) {
        return serverName + ":" + gameIdentifier + ":" + mapName;
    }

    /**
     * Used to get the map identifier from an arena identifier.
     *
     * @param arenaIdentifier The instance of the arena identifier.
     * @return The map identifier.
     */
    public static @NotNull String getIdentifier(@NotNull String arenaIdentifier) {
        return arenaIdentifier.split(":")[0] + ":" + arenaIdentifier.split(":")[1] + ":" + arenaIdentifier.split(":")[2];
    }

    /**
     * Used to get the maps name from the map identifier.
     *
     * @param mapIdentifier The map's identifier.
     * @return The map's name.
     */
    public static @NotNull String getName(@NotNull String mapIdentifier) {
        return mapIdentifier.split(":")[2];
    }
}
