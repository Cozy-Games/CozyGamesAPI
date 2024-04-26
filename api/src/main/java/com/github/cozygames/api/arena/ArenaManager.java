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
import com.github.cozygames.api.database.record.ArenaRecord;
import com.github.cozygames.api.database.table.ArenaTable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the apis arena manager.
 * <p>
 * Used to register active {@link ImmutableArena}'s on the
 * cozy games network.
 * <p>
 * The {@link ImmutableArena} class is used to advise developers not to
 * use variables that could change while stored in the local list.
 */
public class ArenaManager {

    private final @NotNull CozyGames api;
    private final @NotNull List<ImmutableArena<?, ?>> localArenaList;

    /**
     * Used to create a new arena manager instance.
     *
     * @param api The api the arena manager is part of.
     */
    @ApiStatus.Internal
    public ArenaManager(@NotNull CozyGames api) {
        this.api = api;
        this.localArenaList = new ArrayList<>();
    }

    /**
     * Used to get the list of arena's that were registered on
     * this server/api connection/arena manager instance.
     * <p>
     * It is advised not to cast the {@link ImmutableArena} to any higher
     * arena class. This is due to the fact values higher could
     * have changed while the arena was stored.
     *
     * @return The list of immutable arena's.
     */
    public @NotNull List<ImmutableArena<?, ?>> getLocalArenaList() {
        return this.localArenaList;
    }

    /**
     * Used to get the list of locally registered
     * arena's with a certain game identifier.
     *
     * @param gameIdentifier The game identifier.
     * @return The list of filtered locally registered arenas.
     */
    public @NotNull List<ImmutableArena<?, ?>> getLocalArenaList(@NotNull String gameIdentifier) {
        return this.localArenaList.stream()
                .filter(arena -> arena.getMap().getGameIdentifier().equals(gameIdentifier))
                .toList();
    }

    /**
     * Used to register an arena within the
     * cozy game's network.
     * <p>
     * This will let other api connections call
     * methods within the arena class that were implemented.
     *
     * @param arena The instance of the arena.
     * @return This instance.
     */
    public @NotNull ArenaManager registerArena(@NotNull ImmutableArena<?, ?> arena) {
        this.localArenaList.add(arena);
        return this;
    }

    /**
     * Used to unregister an arena within the
     * cozy game's network.
     * <p>
     * This will remove stop the {@link GlobalArena} version
     * from using the implemented methods. This won't delete it from
     * the arena or local configuration/storage.
     *
     * @param arena The instance of the arena.
     * @return This instance.
     */
    public @NotNull ArenaManager unregisterArena(@NotNull ImmutableArena<?, ?> arena) {
        this.localArenaList.remove(arena);
        return this;
    }

    /**
     * Used to unregister an arena within the
     * cozy game's network.
     * <p>
     * This will remove stop the {@link GlobalArena} version
     * from using the implemented methods. This won't delete it from
     * the arena or local configuration/storage.
     *
     * @param identifier The arena's identifier.
     * @return This instance.
     */
    public @NotNull ArenaManager unregisterArena(@NotNull String identifier) {
        this.localArenaList.removeIf(map -> map.getIdentifier().equals(identifier));
        return this;
    }

    /**
     * Used to get the instance of a registered arena
     * within the cozy game's network.
     * <p>
     * This will return the related global arena instance which is used
     * to interact with the local arena though kerb events.
     *
     * @param identifier The arena's identifier.
     * @return The optional global arena.
     */
    public @NotNull Optional<GlobalArena> getGlobalArena(@NotNull String identifier) {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getArenaRecord(identifier)
                .map(ArenaRecord::convert);
    }

    /**
     * Used to get the instance of a registered arena
     * within the cozy game's network.
     * <p>
     * This will return the related global arena instance which is used
     * to interact with the local arena though kerb events.
     *
     * @param mapIdentifier The arena's map identifier.
     * @param worldName     The name of the world the arena is using.
     * @return The optional global arena.
     */
    public @NotNull Optional<GlobalArena> getGlobalArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        final String identifier = Arena.getIdentifier(mapIdentifier, worldName);
        return this.getGlobalArena(identifier);
    }

    /**
     * Used to get the list of registered arenas within
     * the cozy game's network.
     * <p>
     * This will return the list of global arena instances which are used
     * to interact with the local arena instances though kerb events.
     *
     * @return The list of global arenas.
     */
    public @NotNull List<GlobalArena> getGlobalArenaList() {
        return this.api.getDatabase()
                .getTable(ArenaTable.class)
                .getRecordList()
                .stream()
                .map(ArenaRecord::convert)
                .toList();
    }

    /**
     * Used to get the list of registered arenas within
     * the cozy game's network that run a specific game.
     * <p>
     * This will return the list of global arena instances which are used
     * to interact with the local arena instances though kerb events.
     *
     * @param gameIdentifier The game identifier.
     * @return The list of global arenas within the server.
     */
    public @NotNull List<GlobalArena> getGlobalArenaList(@NotNull String gameIdentifier) {
        return this.getGlobalArenaList().stream()
                .filter(arena -> arena.getMap().getGameIdentifier().equalsIgnoreCase(gameIdentifier))
                .toList();
    }

    /**
     * Used to get the list of registered arenas within
     * the cozy game's network that run a specific game
     * on a specific server.
     * <p>
     * This will return the list of global arena instances which are used
     * to interact with the local arena instances though kerb events.
     *
     * @param gameIdentifier The game identifier.
     * @param serverName     The name of the server.
     * @return The list of global arenas within the server.
     */
    public @NotNull List<GlobalArena> getGlobalArenaList(@NotNull String gameIdentifier, @NotNull String serverName) {
        return this.getGlobalArenaList().stream()
                .filter(arena -> arena.getMap().getServerName().equalsIgnoreCase(serverName)
                        && arena.getMap().getGameIdentifier().equalsIgnoreCase(gameIdentifier))
                .toList();
    }
}
