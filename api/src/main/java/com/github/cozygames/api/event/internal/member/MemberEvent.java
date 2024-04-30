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

package com.github.cozygames.api.event.internal.member;

import com.github.cozygames.api.event.internal.InternalEvent;
import com.github.cozygames.api.member.Member;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a member event.
 * <p>
 * Each member event should implement this interface.
 * <p>
 * This will allow connections to the kerb client to
 * listen to any member event.
 */
public abstract class MemberEvent extends InternalEvent {

    private boolean isComplete = false;

    /**
     * Used to get the instance of the member.
     *
     * @return The instance of the member.
     */
    public abstract @NotNull Member getMember();

    @Override
    public @NotNull InternalEvent complete() {
        this.isComplete = true;
        return this;
    }

    @Override
    public @NotNull InternalEvent setComplete(boolean isComplete) {
        this.isComplete = isComplete;
        return this;
    }

    @Override
    public boolean isComplete() {
        return this.isComplete;
    }
}
