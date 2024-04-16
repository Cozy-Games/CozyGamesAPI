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

import com.github.cozygames.api.server.Server;
import org.jetbrains.annotations.NotNull;

/**
 * The Cozy Games API.
 * <p>
 * The API allows other plugins on the server to interact
 * with the mini-game system.
 * <p>
 * This interface represents the base of the API package.
 * All functions are accessed via this interface.
 * <p>
 * <h1>Implementation</h1>
 * <p>
 * If you're on a platform that has a service provider:
 * <pre>{@code
 * RegisteredServiceProvider<CozyGamesAPI> provider = Bukkit
 *         .getServicesManager()
 *         .getRegistration(CozyGamesAPI.class);
 *
 * if (provider != null) {
 *     CozyGamesAPI api = provider.getProvider();
 * }
 * }</pre>
 * <p>
 * Otherwise, you can obtain a static instance from the {@link CozyGamesAPIProvider}.
 * <pre>{@code
 * CozyGamesAPI api = CozyGamesAPIProvider.get();
 * }</pre>
 */
public interface CozyGamesAPI {

    @NotNull Server getServer();
}
