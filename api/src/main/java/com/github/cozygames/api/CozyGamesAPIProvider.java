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

package com.github.cozygames.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides static access to the {@link CozyGamesAPI} class.
 * <p>
 * It's advisable to utilize the service manager provided
 * by your platform if available.
 * Obtaining that instance is documented here {@link CozyGamesAPI}.
 * <p>
 * If your platform does not offer a service manager you
 * can obtain an instance of the API by calling the
 * {@link CozyGamesAPIProvider#get()} method.
 * <pre>{@code
 * CozyGamesAPI api = CozyGamesAPIProvider.get();
 * }</pre>
 * <p>
 * When the corresponding platform's api plugin has loaded it will
 * {@link CozyGamesAPIProvider#register(CozyGamesAPI)} the instance
 * of the api in this class.
 */
public class CozyGamesAPIProvider {

    private static @Nullable CozyGamesAPI instance;

    /**
     * Represents the exception thrown when the
     * {@link CozyGamesAPIProvider#get()} method was
     * called but the instance of the api is still null.
     */
    public static final class APINotLoadedException extends IllegalStateException {

        public APINotLoadedException() {
            super("The Cozy Games API has not loaded yet.\n" +
                    "This could be because of the following reasons:\n" +
                    "- The CozyGamesAPI is not installed on the server.\n" +
                    "- THe CozyGamesAPI has not been enabled yet.\n" +
                    "- The CozyGamesAPI failed to enable.\n" +
                    "\n" +
                    "Possible fixes:\n" +
                    "- Setting the CozyGamesAPI as a dependency in your plugin's configuration.\n"
            );
        }
    }

    /**
     * Used to get the instance of the {@link CozyGamesAPI}
     * that was registered when the corresponding platform's
     * api loaded.
     *
     * @return The instance of the api.
     * @throws APINotLoadedException When the platform has not yet
     *                               provided the instance of the api.
     */
    public static @NotNull CozyGamesAPI get() {

        // Check if the instance of the api
        // has not been provided.
        if (instance == null) {
            throw new APINotLoadedException();
        }

        // Otherwise, return the instance of the api.
        return instance;
    }

    /**
     * Used to register the instance of the api that should
     * be used by other plugins on the server the API plugin
     * is situated on.
     *
     * @param instance The instance of the api.
     */
    @ApiStatus.Internal
    public static void register(@NotNull CozyGamesAPI instance) {
        CozyGamesAPIProvider.instance = instance;
    }

    /**
     * Used to unregister the instance of the api.
     * This might be called when a fatal error occurs and the
     * instance of the api should not be used.
     */
    @ApiStatus.Internal
    public static void unregister() {
        CozyGamesAPIProvider.instance = null;
    }
}
