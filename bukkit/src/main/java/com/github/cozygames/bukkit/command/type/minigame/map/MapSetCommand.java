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
import com.github.cozyplugins.cozylibrary.command.datatype.*;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapSetCommand implements CommandType {

    private final @NotNull CozyGamesPlugin<?, ?, ?, ?> plugin;
    private final @NotNull CommandTypePool pool;

    public MapSetCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        this.plugin = plugin;

        this.pool = new CommandTypePool();
    }

    public static abstract class SubCommand implements CommandType {

        protected final @NotNull CozyGamesPlugin<?, ?, ?, ?> plugin;

        public SubCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
            this.plugin = plugin;
        }

        public abstract @NotNull CommandStatus onExecute(
                @NotNull User user,
                @NotNull ConfigurationSection section,
                @NotNull CommandArguments arguments,
                @NotNull Map<?> map
        );

        @Override
        public @Nullable String getSyntax() {
            return "/[parent] [name] [map_name] [value]";
        }

        @Override
        public @Nullable String getDescription() {
            return "Used to set the " + this.getClass().getSimpleName() + " value.";
        }

        @Override
        public @Nullable CommandTypePool getSubCommandTypes() {
            return null;
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

            return this.onExecute(user, section, arguments, map);
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

    @Override
    public @NotNull String getIdentifier() {
        return "set";
    }

    @Override
    public @NotNull String getSyntax() {
        return "/[parent] [name] [key] [map_name] [value]";
    }

    @Override
    public @Nullable String getDescription() {
        return "Used to set a map field.";
    }

    @Override
    public @Nullable CommandTypePool getSubCommandTypes() {
        return this.pool;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {

        // Send key not specified.
        user.sendMessage(section.getAdaptedString(
                "key_not_specified",
                "\n",
                "&ePlease specify a map field to change. {syntax}"
        ).replace("{syntax}", this.getSyntax()));
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
