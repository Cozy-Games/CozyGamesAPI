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

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.smuddgge.squishyconfiguration.directory.SingleTypeConfigurationDirectory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the arena configuration directory.
 * <p>
 * Used to store a specific arena type locally.
 *
 * @param <A> The arena object that is stored.
 * @param <M> The map class the arena uses.
 */
public class ArenaConfiguration<A extends Arena<A, M>, M extends Map<M>> extends SingleTypeConfigurationDirectory<A> {

    private final @NotNull ArenaFactory<A, M> arenaFactory;

    /**
     * Used to create a new arena configuration directory.
     *
     * @param plugin The instance of the plugin.
     */
    public ArenaConfiguration(@NotNull CozyGamesPlugin<?, A, M, ?> plugin) {
        super(plugin.getDataFolder().getAbsolutePath(), "arenas", plugin.getLoader().getClass());

        this.arenaFactory = plugin.getArenaFactory();
    }

    @Override
    public @NotNull A createEmpty(@NotNull String identifier) {
        return this.arenaFactory.create(identifier);
    }
}