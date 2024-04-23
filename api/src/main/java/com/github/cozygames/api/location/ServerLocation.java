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

package com.github.cozygames.api.location;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a location on a server in a specific world.
 */
public class ServerLocation {

    private final @NotNull String serverName;
    private final @NotNull String worldName;
    private final @NotNull Position position;

    /**
     * Used to create a new instance of a server location.
     *
     * @param serverName The server's name.
     * @param worldName  The world's name.
     * @param position   The position in the world.
     */
    public ServerLocation(@NotNull String serverName, @NotNull String worldName, @NotNull Position position) {
        this.serverName = serverName;
        this.worldName = worldName;
        this.position = position;
    }

    /**
     * The server location converter.
     *
     * @param <T> The type of class the server
     *            location can be converted to.
     */
    public interface Converter<T> {

        /**
         * Used to convert a location into a server location.
         *
         * @param location The location to convert.
         * @return The server location instance.
         */
        @NotNull
        ServerLocation convert(@NotNull T location);

        /**
         * Used to convert a server location into a location type.
         *
         * @param location The instance of the server location.
         * @return The location type.
         */
        @NotNull
        T convert(@NotNull ServerLocation location);
    }

    public @NotNull String getServerName() {
        return this.serverName;
    }

    public @NotNull String getWorldName() {
        return this.worldName;
    }

    public @NotNull Position getPosition() {
        return this.position;
    }

    /**
     * Used to convert this server location into
     * another location type.
     *
     * @param converter The instance of the location converter.
     * @return The instance of the location.
     * @param <T> The location type.
     */
    public <T> @NotNull T getLocation(@NotNull Converter<T> converter) {
        return converter.convert(this);
    }
}
