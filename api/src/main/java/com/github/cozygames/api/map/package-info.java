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
 * create and manage maps.
 * <p>
 * A map represents a configured build that can be loaded in an arena.
 * <p>
 * The {@link com.github.cozygames.api.map.Map} Represents the
 * base of a map. Containing values that will be present in every map.
 * <p>
 * The {@link com.github.cozygames.api.map.GlobalMap} represents a map
 * that could be on another server/api connection. It uses kerb events to call
 * the related method located in the registered plugin.
 * <p>
 * The {@link com.github.cozygames.api.map.LocalMap} represents a map
 * that is local to the plugin. It provides help when completing the map methods.
 */
package com.github.cozygames.api.map;