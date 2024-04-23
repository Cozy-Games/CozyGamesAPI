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

package com.github.cozygames.api.member;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.database.table.MemberTable;
import com.github.cozygames.api.event.member.MemberTeleportEvent;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.location.ServerLocation;
import com.github.kerbity.kerb.result.CompletableResultSet;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a player on the server
 * registered with the cozy game's system.
 * <p>
 * Each member should be stored in the database.
 * The database can be obtained with {@link CozyGames#getDatabase()}.
 */
public class Member implements Savable<Member> {

    private final @NotNull UUID uuid;
    private final @NotNull String name;

    /**
     * Used to create a new instance of a member.
     *
     * @param uuid The player's unique uuid.
     * @param name The player's unique name.
     */
    public Member(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Used to get the member's unique uuid.
     *
     * @return The member's uuid.
     */
    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    /**
     * Used to get the member's unique name.
     *
     * @return The member's name.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Used to attempt getting the instance of
     * this member as a player.
     *
     * @param clazz The instance of the class.
     * @param <P>   The player class.
     * @return The optional instance of the player class.
     * @throws IllegalArgumentException Thrown when the class requested doesnt match
     *                                  what the player adapter provided by the plugin.
     */
    @SuppressWarnings("all")
    public <P> @NotNull Optional<P> getPlayer(@NotNull Class<P> clazz) {

        // Attempt to get the instance of the player.
        Optional<?> optional = CozyGamesProvider.get()
                .getPlugin()
                .getPlayerAdapter()
                .getPlayer(this);

        // Check if the player was not found.
        if (optional.isEmpty()) return Optional.empty();
        final Object player = optional.get();

        // Check if they are not the same class type.
        if (!clazz.getName().equals(player.getClass().getName())) {
            throw new IllegalArgumentException(
                    "The provided player adapter converted the member into a "
                            + player.getClass().getName()
                            + ", yet the class requested was "
                            + clazz.getName() + "."
            );
        }

        return Optional.of((P) player);
    }

    /**
     * Used to teleport the member to a specific location
     * in a server in a world.
     *
     * @param location The location to teleport the player to.
     * @return The completable boolean. True if the member was teleported.
     */
    public @NotNull CompletableResultSet<Boolean> teleport(@NotNull ServerLocation location) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        // Check if the event contains a true value, they have the permission.
        new Thread(() -> result.addResult(
                CozyGamesProvider.get()
                        .callEvent(new MemberTeleportEvent(this, location))
                        .waitForComplete()
                        .containsSettable(true)
        )).start();

        return result;
    }

    @Override
    public @NotNull Member save() {
        CozyGamesProvider.get()
                .getDatabase()
                .getTable(MemberTable.class)
                .insertMember(this);

        return this;
    }
}
