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

/**
 * Contains classes that are used to
 * create and manage arenas.
 * <p>
 * An arena is a loaded map that is contained on a world in a server.
 * <p>
 * The {@link com.github.cozygames.api.arena.ImmutableArena} Represents the
 * base of an {@link com.github.cozygames.api.arena.Arena}. Containing the
 * values that should not change though out the arena's lifetime.
 * <p>
 * The {@link com.github.cozygames.api.arena.Arena} inherits the
 * {@link com.github.cozygames.api.arena.ImmutableArena} but should
 * be thought as the base class. This instance contains the changeable
 * variables, and can be updated by using the
 * {@link com.github.cozygames.api.arena.Arena#save()} method. Note the method doesn't save the instance to the local configuration within the
 * <p>
 * The {@link com.github.cozygames.api.arena.GlobalArena} represents an arena
 * that could be on another server/api connection. It uses kerb events to call
 * the related method on the registered immutable arena instance.
 * <p>
 */
package com.github.cozygames.api.arena;