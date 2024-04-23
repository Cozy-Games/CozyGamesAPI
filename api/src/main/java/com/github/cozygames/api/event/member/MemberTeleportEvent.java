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

package com.github.cozygames.api.event.member;

import com.github.cozygames.api.location.ServerLocation;
import com.github.cozygames.api.member.Member;
import com.github.kerbity.kerb.packet.event.CompletableEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a member teleport event.
 * <p>
 * When called, the api plugin's should work together
 * to teleport the member to the specific server and location.
 */
public class MemberTeleportEvent extends CompletableEvent implements MemberEvent {

    private final @NotNull Member member;
    private final @NotNull ServerLocation location;

    /**
     * Used to create a member teleport event.
     *
     * @param member   The instance of the member.
     * @param location The location to teleport to.
     */
    public MemberTeleportEvent(@NotNull Member member, @NotNull ServerLocation location) {
        this.member = member;
        this.location = location;
    }

    @Override
    public @NotNull Member getMember() {
        return this.member;
    }

    /**
     * Used to get the instance of the server location.
     *
     * @return The instance of the location.
     */
    public @NotNull ServerLocation getLocation() {
        return this.location;
    }
}
