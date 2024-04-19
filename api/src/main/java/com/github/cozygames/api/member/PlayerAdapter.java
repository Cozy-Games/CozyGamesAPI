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

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Used to obtain the instance of the
 * platform's player class.
 * <p>
 * This implementation should be provided
 * in the cozy game's plugin.
 *
 * @param <P> The platform's player class.
 */
public interface PlayerAdapter<P> {

    /**
     * Used to get the instance of the platform's player class.
     *
     * @param member The instance of the member to convert.
     * @return The player instance.
     */
    Optional<P> getPlayer(@NotNull Member member);
}
