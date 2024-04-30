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

package com.github.cozygames.api.event.internal;

import com.github.kerbity.kerb.indicator.Completable;
import com.github.kerbity.kerb.packet.event.Event;
import org.jetbrains.annotations.ApiStatus;

/**
 * Indicates if a class is an internal event.
 * <p>
 * Internal events are used within the api to
 * bridge api connections.
 */
@ApiStatus.Internal
public abstract class InternalEvent extends Event implements Completable<InternalEvent> {
}
