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

package com.github.cozygames.bukkit.command.type.minigame;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.ProgrammableCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends ProgrammableCommandType {

    public MainCommand() {
        super("main");
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {

        // Create the list of commands.
        List<String> commandList = new ArrayList<>();

        // Loop though the list of command types.
        for (CommandType commandType : this.getSubCommandTypes() == null ? new ArrayList<CommandType>() : this.getSubCommandTypes()) {
            commandList.add("&7- &7id:&f" + commandType.getIdentifier()
                    + " &7" + commandType.getDescription() + "\n"
            );
        }

        // Send the message.
        user.sendMessage(section.getAdaptedString(
                "message",
                "\n",
                "{commands}"
        ).replace("{commands}", String.join("", commandList)));
        return new CommandStatus();
    }
}
