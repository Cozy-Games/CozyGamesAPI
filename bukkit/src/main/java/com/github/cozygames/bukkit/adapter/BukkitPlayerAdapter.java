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

package com.github.cozygames.bukkit.adapter;

import com.github.cozygames.api.member.Member;
import com.github.cozygames.api.member.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents the bukkit player adapter.
 * <p>
 * Used to convert a member instance into
 * a bukkit player instance.
 */
public class BukkitPlayerAdapter implements PlayerAdapter<Player> {

    @Override
    public Optional<Player> getPlayer(@NotNull Member member) {
        final Player player = Bukkit.getPlayer(member.getUuid());
        return Optional.ofNullable(player);
    }
}
