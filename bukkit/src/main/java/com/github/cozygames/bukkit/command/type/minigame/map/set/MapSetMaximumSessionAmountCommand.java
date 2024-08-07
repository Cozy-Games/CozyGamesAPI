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

package com.github.cozygames.bukkit.command.type.minigame.map.set;

import com.github.cozygames.api.map.GlobalMap;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.map.MapFilter;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.bukkit.command.type.minigame.map.MapSetCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MapSetMaximumSessionAmountCommand extends MapSetCommand.SubCommand {

    public MapSetMaximumSessionAmountCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "maximum_session_amount";
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return new CommandSuggestions()
                .append(this.plugin.getMapConfiguration().getKeys())
                .append(List.of("[number]"));
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {

        // Ensure the player has provided a map name.
        if (arguments.getArguments().isEmpty() || arguments.getArguments().get(0).isEmpty()) {
            user.sendMessage(section.getAdaptedString(
                    "map_name_unidentified",
                    "\n",
                    "&ePlease enter the map name as the next command argument. {syntax}"
            ).replace("{syntax}", this.getSyntax()));
            return new CommandStatus();
        }

        // Get the map name.
        final String mapName = arguments.getArguments().get(0);

        // Check if the map currently exists.
        final Map<?> map = this.plugin.getMapConfiguration().getType(mapName).orElse(null);
        if (map == null) {
            user.sendMessage(section.getAdaptedString(
                    "map_unidentified",
                    "\n",
                    "&eThe map &f{map} &edoes not exist."
            ).replace("{map}", mapName));
            return new CommandStatus();
        }

        return null;
    }
}
