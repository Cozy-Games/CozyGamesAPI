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

package com.github.cozygames.api.member;

/**
 * Called when a member was not found in the
 * plugin or the database.
 */
public class MemberNotFoundException extends RuntimeException {

    /**
     * Used to create the exception with a
     * custom message.
     *
     * @param message The instance of the message.
     */
    public MemberNotFoundException(String message) {
        super("The member could not be found.\nmessage={" + message + "}\n" +
                "This could be for a few reasons:\n" +
                "- The player was not removed from the database while they were online.\n" +
                "- The player was never registered in the database when they joined.");
    }

    /**
     * Used to create the exception with the
     * default message.
     */
    public MemberNotFoundException() {
        super("The member could not be found.\n" +
                "This could be for a few reasons:\n" +
                "- The player was not removed from the database while they were online.\n" +
                "- The player was never registered in the database when they joined.");
    }
}
