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
 * To obtain an instance of the CozyGamesAPI interface, use the {@code getInstance()} method.
 * This ensures that only one instance of the API is created per server instance, maintaining
 * consistency and preventing duplication of resources.
 * <p>
 *
 * <p>For ease of use, and for platforms without a Service Manager, an instance
 * can also be obtained from the static singleton accessor in
 * {@link LuckPermsProvider}.</p>
 */
public interface CozyGamesAPI {

    @NotNull String getServerName();
}
