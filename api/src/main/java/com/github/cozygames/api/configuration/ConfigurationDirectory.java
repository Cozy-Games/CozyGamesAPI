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

package com.github.cozygames.api.configuration;

import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A directory that contains configuration files.
 * <p>
 * This is used to view all the configuration files
 * in the directory as one big configuration directory.
 */
public class ConfigurationDirectory extends MemoryConfigurationSection {

    /**
     * The file extension used to store data about the
     * configuration files in the configuration directory.
     * <p>
     * Files with this extension will not be parsed when the
     * {@link ConfigurationDirectory#reload()} method is called.
     * <pre>{@code
     * DATA_FILE_EXTENSION = ".squishystore"
     * }</pre>
     */
    public static final @NotNull String DATA_FILE_EXTENSION = ".squishystore";

    private final @NotNull File directory;
    private final @NotNull String directoryName;

    private final @NotNull List<String> resourceNames;
    private final @NotNull Class<?> resourceClass;

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The path to the directory will just be the directory's name.
     * <pre>{@code
     * /<directoryName>
     * }</pre>
     *
     * @param directoryName The name of the directory.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = new File(directoryName);
        this.directoryName = directoryName;
        this.resourceNames = new ArrayList<>();
        this.resourceClass = resourceClass;
    }

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The parent path is combined with the directory's name to create the path.
     * <pre>{@code
     * /<parentPath>/<directoryName>
     * }</pre>
     *
     * @param parentPath    The parent path.
     * @param directoryName The directory's name.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull String parentPath, @NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = new File(parentPath + File.separator + directoryName);
        this.directoryName = directoryName;
        this.resourceNames = new ArrayList<>();
        this.resourceClass = resourceClass;
    }

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The name of the directory will be set to the file's
     * name without the extensions.
     *
     * @param directory     The instance of the directory as a file.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull File directory, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = directory;
        this.directoryName = directory.getName().split("\\.")[0];
        this.resourceNames = new ArrayList<>();
        this.resourceClass = resourceClass;
    }

    public @NotNull File getDirectory() {
        return this.directory;
    }

    public @NotNull String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * The list of default resource file names.
     * <p>
     * Resources in the list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     *
     * @return The list of resource names.
     */
    public @NotNull List<String> getResourceNames() {
        return this.resourceNames;
    }

    /**
     * The class instance that is used to load
     * the resource files.
     *
     * @return The resource class instance.
     */
    public @NotNull Class<?> getResourceClass() {
        return this.resourceClass;
    }

    /**
     * Used to get the list of files in this directory.
     * <p>
     * This will also return the file's in each folder of the directory.
     *
     * @return The file's in the directory.
     */
    public @NotNull List<File> getFiles() {
        return this.getFiles0(this.getDirectory());
    }

    private @NotNull List<File> getFiles0(@NotNull File folder) {
        File[] fileList = folder.listFiles();
        if (fileList == null) return new ArrayList<>();

        List<File> finalFileList = new ArrayList<>();
        for (File file : fileList) {

            // Ignore system files.
            if (file.getName().equals(".DS_Store")) continue;
            if (file.getName().endsWith(ConfigurationDirectory.DATA_FILE_EXTENSION)) continue;

            List<File> filesInFile = this.getFiles0(file);
            if (filesInFile.isEmpty()) {
                finalFileList.add(file);
                continue;
            }

            finalFileList.addAll(filesInFile);
        }

        return finalFileList;
    }

    /**
     * Used to get the names of the file's from the
     * {@link ConfigurationDirectory#getFiles()} method
     * with extensions.
     *
     * @return The list of file name's with extensions.
     */
    public @NotNull List<String> getFileNames() {
        return this.getFiles().stream().map(File::getName).toList();
    }

    /**
     * Used to get the names of the file's from the
     * {@link ConfigurationDirectory#getFiles()} method
     * and then remove the file extensions.
     *
     * @return The list of file name's without extensions.
     */
    public @NotNull List<String> getFileNamesWithoutExtension() {
        return this.getFiles().stream()
                .map(file -> file.getName().split("\\.")[0])
                .toList();
    }

    /**
     * Used to add a new default resource name.
     * <p>
     * Resources added to this list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     *
     * @param fileName The resource file's name.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory addResourceName(@NotNull String fileName) {
        this.resourceNames.add(fileName);
        return this;
    }

    /**
     * Used to add a new default resource file names.
     * <p>
     * Resources added to this list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     *
     * @param fileNames The list of file names.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory addResourceNames(@NotNull List<String> fileNames) {
        this.resourceNames.addAll(fileNames);
        return this;
    }

    /**
     * Used to append a configuration section to
     * this configuration section.
     * <p>
     * This will override any duplicated keys.
     *
     * @param section The instance of the configuration section.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory appendConfiguration(@NotNull ConfigurationSection section) {
        this.data.putAll(section.getMap());
        return this;
    }

    /**
     * Used to reset the class's internal map and then load
     * the data from the configuration files located in the
     * directory into the internal map.
     * <p>
     * If the directory doesn't exist, it will be created.
     * <p>
     * If the directory is empty, the default resource files
     * will be created.
     *
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory reload() {

        // Reset the configuration section data.
        this.data = new LinkedHashMap<>();

        return this;
    }
}
