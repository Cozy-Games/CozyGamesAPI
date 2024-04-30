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

package com.github.cozygames.bukkit.teleport;

import com.github.cozygames.api.event.internal.member.MemberTeleportEvent;
import com.github.cozygames.bukkit.adapter.BukkitLocationConverter;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a teleport manager.
 * <p>
 * Handles making sure players get teleport to the
 * correct world and location when a kerb teleport
 * event is received.
 */
public class TeleportManager implements Listener {

    private final @NotNull List<MemberTeleportEvent> teleportEventsToProcess;

    /**
     * Used to create a new teleport manager.
     */
    public TeleportManager() {
        this.teleportEventsToProcess = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        for (MemberTeleportEvent teleportEvent : this.teleportEventsToProcess) {
            if (!teleportEvent.getMember().getUuid().equals(event.getPlayer().getUniqueId())) continue;
            PlayerUser user = new PlayerUser(event.getPlayer());
            user.forceTeleport(teleportEvent.getLocation().getLocation(new BukkitLocationConverter()));
        }
    }

    /**
     * Used to get the list of teleport
     * events that need processing.
     *
     * @return The list of teleport events.
     */
    public @NotNull List<MemberTeleportEvent> getTeleportEventsToProcess() {
        return this.teleportEventsToProcess;
    }

    /**
     * Used to add a new teleport event to the process list.
     *
     * @param event The instance of the teleport event.
     * @return This instance.
     */
    public @NotNull TeleportManager addTeleportEvent(@NotNull MemberTeleportEvent event) {
        this.teleportEventsToProcess.add(event);
        return this;
    }
}
