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
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import com.github.cozygames.bukkit.worldedit.WorldEditHelper;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandTypePool;
import com.github.cozyplugins.cozylibrary.location.Region;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a simple create map command type.
 * <p>
 * This will let players create maps.
 */
public class CreateMapCommand implements CommandType {

    private final @NotNull CozyGamesPlugin<?, ?, ?, ?> plugin;

    /**
     * Used to create a new create map command type.
     *
     * @param plugin The instance of the plugin that will register this command.
     */
    public CreateMapCommand(@NotNull CozyGamesPlugin<?, ?, ?, ?> plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "create";
    }

    @Override
    public @NotNull String getSyntax() {
        return "/[parent] [name] [map name]";
    }

    @Override
    public @Nullable String getDescription() {
        return "Used to create a new map.";
    }

    @Override
    public @Nullable CommandTypePool getSubCommandTypes() {
        return null;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return new CommandSuggestions().append(List.of("[map_name]"));
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {

        // Get the players selection region.
        final Region region = WorldEditHelper.getSelectionRegion(user).orElse(null);
        if (region == null) {
            user.sendMessage(section.getAdaptedString(
                    "region_undefined",
                    "\n",
                    "&ePlease select the maps region using world edit first."
            ));
            return new CommandStatus();
        }

        // Check if they have provided the map name.
        if (arguments.getArguments().isEmpty() || arguments.getArguments().get(0).isEmpty()) {
            user.sendMessage(section.getAdaptedString(
                    "map_name_undefined",
                    "\n",
                    "&ePlease enter the map name as the next command argument. {syntax}"
            ).replace("{syntax}", this.getSyntax()));
            return new CommandStatus();
        }

        // Get the map name.
        final String mapName = arguments.getArguments().get(0);

        // Create the new map.
        Map<?> map = this.plugin.getMapFactory().create(mapName);

        // Register and save the map instance.
        this.plugin.getApi().getMapManager().registerMap(map.getIdentifier());
        map.save();

        // Send the success message to the player.
        user.sendMessage(section.getAdaptedString(
                "map_created",
                "\n",
                "&aCreated a new map with identifier &f{identifier} &a."
        ).replace("{identifier}", map.getIdentifier()));
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
