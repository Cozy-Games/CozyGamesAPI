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

import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.database.table.MemberTable;
import com.github.cozygames.api.indicator.Savable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Member implements Savable<Member> {

    private final @NotNull UUID uuid;
    private final @NotNull String name;

    public Member(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public @NotNull UUID getUuid() {
        return this.uuid;
    }

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
