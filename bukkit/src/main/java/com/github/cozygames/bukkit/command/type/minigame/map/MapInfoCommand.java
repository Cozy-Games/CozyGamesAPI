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

package com.github.cozygames.bukkit.command.type.minigame.map;

import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandTypePool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapInfoCommand implements CommandType {

    private final @NotNull CozyGamesPlugin<?, ?, ?, ?> plugin;

    public MapInfoCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "info";
    }

    @Override
    public @Nullable String getSyntax() {
        return "/[parent] [name] [map_name]";
    }

    @Override
    public @Nullable String getDescription() {
        return "Used to get a map's infomation.";
    }

    @Override
    public @Nullable CommandTypePool getSubCommandTypes() {
        return null;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return new CommandSuggestions().append(this.plugin.getMapConfiguration().getKeys());
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

        // Initialise the message and map section.
        String message = section.getAdaptedString("message", "\n", "{name}");
        final ConfigurationSection mapSection = map.convert();

        // Loop though map values and apply them to the message.
        for (String key : mapSection.getKeys()) {
            message = message.replace("{" + key + "}", mapSection.get(key).toString());
        }

        // Replace all un-parsed placeholders.
        message = message.replaceAll("\\{([a-z]|_)*}", "null");

        // Send the message.
        user.sendMessage(message);
        return new CommandStatus();
    }

    @Override
    public @Nullable CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onConsole(@NotNull ConsoleUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }
}
