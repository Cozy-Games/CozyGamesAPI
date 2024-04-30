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

package com.github.cozygames.api.implementation;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.event.internal.InternalEvent;
import com.github.cozygames.api.event.internal.arena.ArenaEvent;
import com.github.cozygames.api.event.internal.map.MapEvent;
import com.github.cozygames.api.map.Map;
import com.github.kerbity.kerb.client.listener.EventListener;
import com.github.kerbity.kerb.packet.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * When registered with a kerb client, this will
 * listen for {@link InternalEvent}s.
 */
public class CozyGamesInternalListener implements EventListener<InternalEvent> {

    private final @NotNull CozyGames api;

    /**
     * Used to create a new cozy games internal listener.
     *
     * @param api The instance of the api.
     */
    public CozyGamesInternalListener(@NotNull CozyGames api) {
        this.api = api;
    }

    @Override
    public @Nullable Event onEvent(InternalEvent event) {
        if (event instanceof MapEvent mapEvent) return this.onMapEvent(mapEvent);
        if (event instanceof ArenaEvent arenaEvent) return this.onArenaEvent(arenaEvent);
        return null;
    }

    private @NotNull MapEvent onMapEvent(@NotNull MapEvent mapEvent) {

        // Check if the api connection contains the map.
        for (String mapIdentifier : this.api.getMapManager().getLocalRegisteredMapList()) {
            if (!mapIdentifier.equals(mapEvent.getMapIdentifier())) continue;

            // Get the map instance.
            final Map<?> map = this.api.getMapManager().getLocalMap(mapIdentifier).orElseThrow();

            // Execute the method.
            mapEvent.executeMethod(map);

            // Complete the event.
            mapEvent.complete();
            return mapEvent;
        }

        return mapEvent;
    }

    private @NotNull ArenaEvent onArenaEvent(@NotNull ArenaEvent arenaEvent) {

        // Check if the arena is located on this connection.
        final Arena<?, ?> arena = this.api.getArenaManager().getLocalArena(arenaEvent.getArenaIdentifier()).orElse(null);
        if (arena == null) return arenaEvent;

        // Execute arena method.
        arenaEvent.executeMethod(arena);

        // Complete the event.
        arenaEvent.complete();
        return arenaEvent;
    }
}
