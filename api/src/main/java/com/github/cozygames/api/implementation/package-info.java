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
 * Provides implementations of the core api {@link com.github.cozygames.api.CozyGames}.
 * <p>
 * These classes are to only be used within the api respiratory.
 * <p>
 * To create an instance of {@link com.github.cozygames.api.implementation.CozyGamesImpl}, use
 * the {@link com.github.cozygames.api.implementation.CozyGamesBuilder} within this maven project.
 * <pre>{@code
 * CozyGames cozyGames = new CozyGamesBuilder(plugin).build();
 * }</pre>
 */
package com.github.cozygames.api.implementation;