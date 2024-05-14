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

package com.github.cozygames.bukkit.worldedit;

import com.github.cozygames.api.logger.Logger;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Contains methods used to simplify world edit actions.
 */
public final class WorldEditHelper {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WorldEditHelper.class);

    /**
     * Used to get a user's current world edit selection.
     *
     * @param user The instance of the user.
     * @return The user's world edit selection region.
     */
    public static @NotNull Optional<Region> getSelection(@NotNull PlayerUser user) {
        try {
            Player actor = BukkitAdapter.adapt(user.getPlayer());
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(actor);
            return Optional.ofNullable(session.getSelection());
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    /**
     * Used to clear a user's selection region.
     *
     * @param user The instance of the user.
     */
    public static void clearSelection(@NotNull PlayerUser user) {
        try {
            Player actor = BukkitAdapter.adapt(user.getPlayer());
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(actor);
            session.getRegionSelector(session.getSelection().getWorld()).clear();
        } catch (Exception ignored) {
        }
    }

    /**
     * Used to get a user's selection as a cozy bukkit
     * {@link com.github.cozyplugins.cozylibrary.location.Region}.
     *
     * @param user The instance of a user.
     * @return This requested region.
     */
    public static @NotNull Optional<com.github.cozyplugins.cozylibrary.location.Region> getSelectionRegion(@NotNull PlayerUser user) {
        final Player actor = BukkitAdapter.adapt(user.getPlayer());

        // Get the selection region.
        final Region region = getSelection(user).orElse(null);
        if (region == null) return Optional.empty();

        // Create the region.
        return Optional.of(new com.github.cozyplugins.cozylibrary.location.Region(
                new Location(
                        BukkitAdapter.asBukkitWorld(actor.getWorld()).getWorld(),
                        region.getMaximumPoint().x(),
                        region.getMaximumPoint().y(),
                        region.getMaximumPoint().z()
                ),
                new Location(
                        BukkitAdapter.asBukkitWorld(actor.getWorld()).getWorld(),
                        region.getMinimumPoint().x(),
                        region.getMinimumPoint().y(),
                        region.getMinimumPoint().z()
                )
        ));
    }

    /**
     * Used to get the correct instance of the world edit plugin.
     * <p>
     * This will check if ether the world edit plugin is installed
     * of the fast async world edit plugin is installed.
     *
     * @return The optional world edit plugin.
     */
    public static @NotNull Optional<Plugin> getWorldEditPlugin() {

        // Attempt to get the world edit plugin.
        final Plugin worldEdit = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (worldEdit != null) return Optional.of(worldEdit);

        // Attempt to get the fast async world edit plugin.
        final Plugin fastAsyncWorldEdit = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit");
        if (fastAsyncWorldEdit != null) return Optional.of(fastAsyncWorldEdit);
        return Optional.empty();
    }

    /**
     * Used to get the list of available schematic identifiers.
     * <li>Without the extensions</li>
     *
     * @return The list of schematic identifiers.
     */
    public static List<String> getSchematicList() {

        // Attempt to get the world edit plugin.
        final Plugin plugin = WorldEditHelper.getWorldEditPlugin().orElse(null);
        if (plugin == null) return new ArrayList<>();

        // Get the schematics folder.
        File folder = new File(plugin.getDataFolder() + "/schematics");

        // Get the list of file names.
        String[] fileNames = folder.list();
        if (fileNames == null) return new ArrayList<>();

        // Return the list without extensions.
        return Arrays.stream(fileNames)
                .map(name -> name.replace(".schematic", ""))
                .map(name -> name.replace(".schem", ""))
                .toList();
    }

    /**
     * Used to get the instance of a schematic file from its identifier.
     * <p>
     * The identifier should not include any extensions.
     *
     * @param identifier The schematic's identifier.
     * @return The optional schematic file.
     */
    public static @NotNull Optional<File> getSchematicFile(@NotNull String identifier) {

        // Attempt to get the world edit plugin.
        final Plugin plugin = WorldEditHelper.getWorldEditPlugin().orElse(null);
        if (plugin == null) return Optional.empty();

        // Check if the file exists where the extension is .schem
        final File schemFile = new File(plugin.getDataFolder() + "/schematics", identifier + ".schem");
        if (schemFile.exists()) return Optional.of(schemFile);

        // Check if the file exists where the extension is .schematic
        final File schematicFile = new File(plugin.getDataFolder() + "/schematics", identifier + ".schematic");
        if (schematicFile.exists()) return Optional.of(schematicFile);
        return Optional.empty();
    }

    /**
     * Used to get a schematic from world edit.
     *
     * @param identifier The schematic identifier without the file extension.
     * @return The requested clipboard.
     */
    public static @NotNull Optional<Clipboard> getSchematic(@NotNull Logger logger, @NotNull String identifier) {

        // Attempt to get the world edit plugin.
        final Plugin plugin = WorldEditHelper.getWorldEditPlugin().orElse(null);
        if (plugin == null) return Optional.empty();

        // Create file instance.
        final File file = WorldEditHelper.getSchematicFile(identifier).orElse(null);
        if (file == null) return Optional.empty();

        // Get clipboard format.
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        if (format == null) return Optional.empty();

        // Create clipboard.
        try (ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()))) {
            return Optional.ofNullable(reader.read());

        } catch (IOException exception) {
            logger.warn("Attempted to get the schematic &f" + identifier + "&7 but the file &f" + file.getAbsolutePath() + "&7could not be read.");
            throw new RuntimeException(exception);
        }
    }

    /**
     * Used to paste a clipboard into a world at a location.
     *
     * @param location  The location to paste the clipboard.
     * @param clipboard The instance of a clipboard.
     */
    public static void pasteClipboard(@NotNull Logger logger, @NotNull Location location, @NotNull Clipboard clipboard) {

        // Check if the world is null.
        if (location.getWorld() == null) {
            logger.warn("Attempted to paste a clipboard but the world is null. The location is " + location);
            return;
        }

        // Get the instance of the world edit world.
        World world = BukkitAdapter.adapt(location.getWorld());

        // Paste the clipboard.
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                    .build();

            Operations.complete(operation);

        } catch (Exception exception) {
            logger.warn("Attempted to paste a clipboard but something went wrong when completing the operation.");
            throw new RuntimeException(exception);
        }
    }
}
