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
import com.github.cozygames.api.indicator.Savable;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public @NotNull Member save() {
        CozyGamesProvider.get()
                .getDatabase()
                .getTable(MemberTable.class)
                .insertMember(this);

        return this;
    }
}
