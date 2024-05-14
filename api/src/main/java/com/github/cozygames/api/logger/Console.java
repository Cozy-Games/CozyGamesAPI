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

package com.github.cozygames.api.logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a utility class to send messages
 * with color to the console.
 * <p>
 * Contains static methods that are used by processes
 * that don't need a specialized logger.
 * <p>
 * Primarily used by the {@link com.github.cozygames.api.testing.ResultChecker}.
 */
public final class Console {

    private static @Nullable String logPrefix = "&7[&aLOG&7] ";
    private static @Nullable String warnPrefix = "&7[&eWARN&7] ";

    /**
     * Used to log a message in console.
     * This will support replacing color codes.
     *
     * @param message The instance of the message.
     */
    public static void log(@NotNull String message) {
        System.out.println(ConsoleColor.parse(
                (logPrefix == null ? "" : logPrefix) + message
        ));
    }

    /**
     * Used to log a warning in the console.
     * This will support replacing color codes.
     *
     * @param message The instance of the message.
     */
    public static void warn(@NotNull String message) {
        System.out.println(ConsoleColor.parse(
                (warnPrefix == null ? "" : warnPrefix) + message
        ));
    }

    public static @Nullable String getLogPrefix() {
        return Console.logPrefix;
    }

    public static @Nullable String getWarnPrefix() {
        return Console.warnPrefix;
    }

    /**
     * Used to set the log prefix.
     * This will support replacing color codes.
     *
     * @param prefix The instance of the prefix.
     */
    public static void setLogPrefix(@Nullable String prefix) {
        Console.logPrefix = prefix;
    }

    /**
     * Used to set the warning prefix.
     * This will support replacing color codes.
     *
     * @param prefix The instance of the prefix.
     */
    public static void setWarnPrefix(@Nullable String prefix) {
        Console.warnPrefix = prefix;
    }

    /**
     * Used to set both the log and warn prefix.
     * This will support replacing color codes.
     *
     * @param prefix The instance of the prefix.
     */
    public static void setBothPrefixes(@Nullable String prefix) {
        Console.logPrefix = prefix;
        Console.warnPrefix = prefix;
    }
}
