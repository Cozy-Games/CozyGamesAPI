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

package com.github.cozygames.bukkit.arena;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.arena.LocalArena;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.session.Session;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

public abstract class LocalBukkitArena<S extends Session<A, M>, A extends Arena<A, M>, M extends Map<M>> extends LocalArena<S, A, M> {

    /**
     * Used to create a new local arena.
     *
     * @param mapIdentifier The map's identifier.
     * @param worldName     The name of the world this arena is located in.
     */
    public LocalBukkitArena(@NotNull String mapIdentifier, @NotNull String worldName) {
        super(mapIdentifier, worldName);
    }

    /**
     * Used to create a new local arena.
     *
     * @param identifier The arena's identifier.
     */
    public LocalBukkitArena(@NotNull String identifier) {
        super(identifier);
    }

    @Override
    public @NotNull A createWorld() {

        // Check if the world already exists.
        if (Bukkit.getWorlds().stream()
                .map(World::getName)
                .toList()
                .contains(this.getWorldName())) {

            return (A) this;
        }

        // Create the world.
        Bukkit.createWorld(new WorldCreator(this.getWorldName()).generator(new ChunkGenerator() {}));
        return (A) this;
    }

    @Override
    public @NotNull A deleteWorld() {

        final World world = Bukkit.getWorld(this.getWorldName());
        if (world == null) return (A) this;

        // Unload world.
        Bukkit.unloadWorld(world, false);

        // Delete world.
        world.getWorldFolder().delete();

        return (A) this;
    }
}
