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

package com.github.cozygames.bukkit;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.event.member.MemberTeleportEvent;
import com.github.cozygames.api.implementation.CozyGamesBuilder;
import com.github.cozygames.bukkit.listener.KerbEventListener;
import com.github.cozygames.bukkit.teleport.TeleportManager;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.kerbity.kerb.client.listener.EventListener;
import com.github.kerbity.kerb.packet.event.Event;
import com.github.kerbity.kerb.packet.event.Priority;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the bukkit plugin api loader.
 */
public final class CozyGamesAPIBukkitLoader extends CozyPlugin {

    @Override
    public boolean enableCommandDirectory() {
        return true;
    }

    @Override
    public void onCozyEnable() {
        CozyGamesAPIBukkitPlugin plugin = new CozyGamesAPIBukkitPlugin(this);

        // Create a new instance of the api.
        CozyGames api = new CozyGamesBuilder(plugin).build();

        // Register the instance in the bukkit service manager.
        Bukkit.getServicesManager().register(
                CozyGames.class,
                api,
                this,
                ServicePriority.Normal
        );

        // Also register the instance in the provider.
        CozyGamesProvider.register(api);

        // Register spigot events.
        this.getServer().getPluginManager().registerEvents(plugin.getTeleportManager(), this);

        // Register local kerb listeners.
        api.getKerbClient().registerListener(
                Priority.MEDIUM,
                (EventListener<MemberTeleportEvent>) event -> new KerbEventListener().onMemberTeleportEvent(event)
        );
    }
}
