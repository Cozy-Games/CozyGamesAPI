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
 * <h1>Types of Arenas</h1>
 * <p>
 * The {@link com.github.cozygames.api.arena.Arena} Represents the
 * base of an arena. Containing values that will be present in every arena.
 * <p>
 * The {@link com.github.cozygames.api.arena.GlobalArena} represents an arena
 * that could be on another server/api connection. It uses kerb events to call
 * the related method located in the registered plugin.
 * <p>
 * The {@link com.github.cozygames.api.arena.LocalArena} represents an arena
 * that is local to the plugin. It provides help when completing the arena methods.
 * <p>
 * <h1>Getting, Creating and Updating Arenas</h1>
 * <p>
 * Getting arenas can be done in two ways: {@link com.github.cozygames.api.CozyGames#getArenaManager()}
 * and {@link com.github.cozygames.api.CozyGames#getDatabase()}. However, it is recommended to
 * use the Arena manager.
 * <p>
 * Creating arenas is normally done internally though the platforms api.
 * The platform's api provides uses with an interface where they can
 * partake in steps to start a game in an arena. Otherwise, you can manually create arenas
 * by initialising the arena and using the {@link com.github.cozygames.api.arena.Arena#save()}
 * method to insert it into the database. There is no place arenas are registered because
 * normally an arena is created for 1 game then deleted.
 * <p>
 * Updating arenas can be done by using the {@link com.github.cozygames.api.arena.Arena#save()} method.
 * This will update the arena in the database and local configuration.
 * <p>
 * <h1>Local Storage</h1>
 * <p>
 * The local configuration {@link com.github.cozygames.api.arena.ArenaConfiguration} is primarily
 * used for variables that are added by the mini-game plugin as these variables will not be in
 * the database. Nevertheless, the database variables are also stored in the local
 * configuration to keep everything updated.
 */
package com.github.cozygames.api.arena;