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

package com.github.cozygames.api.group;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.database.table.GroupTable;
import com.github.cozygames.api.indicator.Savable;
import com.github.cozygames.api.member.Member;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Represents a group of players waiting
 * to play a game.
 * <p>
 * This is exclusively stored in the database.
 * The database can be obtained with {@link CozyGames#getDatabase()}.
 */
public class Group implements ConfigurationConvertable<Group>, Savable<Group> {

    private final @NotNull UUID identifier;
    private @NotNull List<Member> memberList;
    private @NotNull String gameIdentifier;

    /**
     * Used to create a new instance of a group.
     *
     * @param identifier     The group's identifier.
     * @param gameIdentifier The game identifier.
     */
    public Group(@NotNull UUID identifier, @NotNull String gameIdentifier) {
        this.identifier = identifier;
        this.memberList = new ArrayList<>();
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * Used to get the group's identifier.
     *
     * @return The group's identifier.
     */
    public @NotNull UUID getIdentifier() {
        return this.identifier;
    }

    /**
     * Used to get the type of group.
     *
     * @return The type of group.
     */
    public @NotNull GroupType getType() {
        return GroupType.BASIC;
    }

    /**
     * Used to get the list of members in
     * this group.
     *
     * @return The list of members.
     */
    public @NotNull List<Member> getMembers() {
        return this.memberList;
    }

    /**
     * Used to get the list of member
     * uuid's in this group.
     *
     * @return The list of uuid's.
     */
    public @NotNull List<UUID> getMemberUuids() {
        return this.memberList
                .stream()
                .map(Member::getUuid)
                .toList();
    }

    /**
     * The identifier of the game the group of
     * members are waiting to play.
     *
     * @return The game identifier.
     */
    public @NotNull String getGameIdentifier() {
        return this.gameIdentifier;
    }

    /**
     * Used to add a list of members to this group.
     *
     * @param members The list of members.
     * @return This instance.
     */
    public @NotNull Group addMemberList(@NotNull List<Member> members) {
        this.memberList.addAll(members);
        return this;
    }

    /**
     * Used to add a member instance to this group.
     *
     * @param member The member instance.
     * @return This instance.
     */
    public @NotNull Group addMember(@NotNull Member member) {
        this.memberList.add(member);
        return this;
    }

    /**
     * Used to add a member to this group via the
     * player's uuid.
     * <p>
     * This will use {@link CozyGames#getMember(UUID)} to
     * get the instance of the member.
     *
     * @param playerUuid The player's uuid.
     * @return This instance.
     */
    public @NotNull Group addMember(@NotNull UUID playerUuid) {
        this.memberList.add(
                CozyGamesProvider.get().getMember(playerUuid)
        );
        return this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("members", this.getMemberUuids());
        section.set("game_identifier", this.getGameIdentifier());

        return section;
    }

    @Override
    public @NotNull Group convert(@NotNull ConfigurationSection section) {

        this.memberList.addAll(
                section.getListString("members", new ArrayList<>())
                        .stream()
                        .map(uuidString -> CozyGamesProvider.get()
                                .getMember(UUID.fromString(uuidString))
                        )
                        .toList()
        );

        this.gameIdentifier = section.getString("game_identifier");

        return this;
    }

    @Override
    public @NotNull Group save() {

        CozyGamesProvider.get()
                .getDatabase()
                .getTable(GroupTable.class)
                .insertGroup(this);

        return this;
    }
}
