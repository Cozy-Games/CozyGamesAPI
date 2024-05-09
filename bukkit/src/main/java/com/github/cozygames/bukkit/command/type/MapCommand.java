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

package com.github.cozygames.bukkit.command.type;

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.map.MapFilter;
import com.github.cozygames.api.member.MemberCapacity;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.ProgrammableCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MapCommand extends ProgrammableCommandType {

    private final @NotNull CozyGamesPlugin plugin;

    public MapCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        super("map");

        this.plugin = plugin;
    }

    @Override
    public @Nullable CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {

        // Create the list of maps to display.
        List<String> mapList = this.plugin.getApi().getMapManager().getMapListFormatted(this.plugin);

        // Create the list of commands.
        List<String> commandList = new ArrayList<>();

        // Loop though the list of command types.
        for (CommandType commandType : this.getSubCommandTypes()) {
            commandList.add("&7- &7id:&f" + commandType.getIdentifier()
                    + " &7" + commandType.getDescription() + "\n"
            );
        }

        // Send the message.
        user.sendMessage(section.getAdaptedString(
                                "message",
                                "\n",
                                "{maps}"
                        )
                        .replace("{maps}", String.join("", mapList))
                        .replace("{commands}", String.join("", commandList))
        );
        return new CommandStatus();
    }
}
