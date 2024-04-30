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
 * Contains classes to represent different kinds of locations.
 * <p>
 * The {@link com.github.cozygames.api.location.Vector} is the simplest
 * with only x, zy and z variables.
 * <p>
 * The {@link com.github.cozygames.api.location.Position} expands on the
 * vector class by adding yaw and pitch.
 * <p>
 * You can also create a position region with the
 * {@link com.github.cozygames.api.location.PositionRegion} class.
 * <p>
 * The {@link com.github.cozygames.api.location.ServerLocation} uses the position
 * class, a world and server name to represent a location on a server.
 */
package com.github.cozygames.api.location;