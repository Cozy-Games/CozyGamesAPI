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

package com.github.cozygames.api.position;

import com.github.cozygames.api.indicator.Replicable;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

/**
 * Represents a position in minecraft without
 * a specific world.
 */
public class Position implements ConfigurationConvertable<Position>, Replicable<Position> {

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    /**
     * Used to create a new instance of a position.
     *
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param z     The z coordinate.
     * @param yaw   The rotation in the horizontal plane.
     * @param pitch The rotation in the vertical plane.
     */
    public Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Used to create a new instance of a position
     * without the yaw and pitch.
     * <p>
     * The yaw and pitch will be set to 0.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     */
    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    /**
     * Used to create a clone of a position.
     * <p>
     * This uses the {@link Position#duplicate()} method.
     *
     * @param position The instance of the position to clone.
     */
    public Position(@NotNull Position position) {
        Position clone = position.duplicate();
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
        this.yaw = clone.yaw;
        this.pitch = clone.pitch;
    }

    /**
     * Used to create a position based on a configuration section.
     * <p>
     * This will use the {@link Position#convert(ConfigurationSection)} method.
     *
     * @param section The instance of the configuration section.
     */
    public Position(@NotNull ConfigurationSection section) {
        this.convert(section);
    }

    /**
     * Used to convert locations into positions
     * and positions into locations.
     *
     * @param <T> The location class.
     */
    public interface Converter<T> {

        /**
         * Used to convert a location into a position.
         *
         * @param location The instance of the location.
         * @return The instance of the position.
         */
        @NotNull
        Position convert(@NotNull T location);

        /**
         * Used to convert a position and world name into a location.
         *
         * @param position  The position instance.
         * @param worldName The name of the world.
         * @return The location instance.
         */
        @NotNull
        T convert(@NotNull Position position, @NotNull String worldName);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public @NotNull Position setX(double x) {
        this.x = x;
        return this;
    }

    public @NotNull Position setY(double y) {
        this.y = y;
        return this;
    }

    public @NotNull Position setZ(double z) {
        this.z = z;
        return this;
    }

    public @NotNull Position setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public @NotNull Position setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    /**
     * Used to get the position as a location.
     * <p>
     * A location is similar but with a world instance.
     *
     * @param converter The instance of the platform's converter.
     * @param worldName The world's name.
     * @param <T>       The location class type.
     * @return The instance of the location.
     */
    public <T> @NotNull T getLocation(@NotNull Converter<T> converter, @NotNull String worldName) {
        return converter.convert(this, worldName);
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("x", this.x);
        section.set("y", this.y);
        section.set("z", this.z);

        section.set("yaw", this.yaw);
        section.set("pitch", this.pitch);

        return section;
    }

    @Override
    public @NotNull Position convert(@NotNull ConfigurationSection section) {

        this.x = section.getDouble("x");
        this.y = section.getDouble("y");
        this.z = section.getDouble("z");

        this.yaw = Float.parseFloat(Double.toString(section.getDouble("yaw")));
        this.pitch = Float.parseFloat(Double.toString(section.getDouble("pitch")));

        return this;
    }

    @Override
    public @NotNull Position duplicate() {
        return new Position(this.convert());
    }
}
