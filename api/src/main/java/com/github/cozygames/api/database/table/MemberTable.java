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

package com.github.cozygames.api.database.table;

import com.github.cozygames.api.database.record.MemberRecord;
import com.github.cozygames.api.member.Member;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents the member table.
 * <p>
 * Contains all the members registered with
 * this cozy game's system.
 */
public class MemberTable extends TableAdapter<MemberRecord> {

    @Override
    public @NotNull String getName() {
        return "members";
    }

    /**
     * Used to get an instance of a member
     * from the member table.
     *
     * @param playerName The player's name to look for.
     * @return The optional member instance.
     */
    public @NotNull Optional<Member> getMember(@NotNull String playerName) {

        // Attempt to get the instance of the member record.
        MemberRecord record = this.getFirstRecord(
                new Query().match("name", playerName)
        );

        if (record == null) return Optional.empty();
        return Optional.of(record.convert());
    }

    /**
     * Used to get an instance of a member
     * from the member table.
     *
     * @param playerUuid The player's uuid to look for.
     * @return The optional member instance.
     */
    public @NotNull Optional<Member> getMember(@NotNull UUID playerUuid) {

        // Attempt to get the instance of the member record.
        MemberRecord record = this.getFirstRecord(
                new Query().match("uuid", playerUuid.toString())
        );

        if (record == null) return Optional.empty();
        return Optional.of(record.convert());
    }

    /**
     * Used to insert a member into this database table.
     *
     * @param member The instance of the member to insert.
     * @return This instance.
     */
    public @NotNull MemberTable insertMember(@NotNull Member member) {

        // Create a new member record.
        MemberRecord record = new MemberRecord();
        record.uuid = member.getUuid().toString();
        record.name = member.getName();

        // Insert the record into the database.
        this.insertRecord(record);

        // Return this instance.
        return this;
    }
}
