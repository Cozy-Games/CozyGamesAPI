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

package com.github.cozygames.api.location;

import com.github.cozygames.api.indicator.Replicable;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

/**
 * Represents a position region.
 * <p>
 * A region of locations that don't have
 * world instances.
 */
public class PositionRegion implements ConfigurationConvertable<PositionRegion>, Replicable<PositionRegion> {

    private @NotNull Position position1;
    private @NotNull Position position2;

    /**
     * Used to create a position region.
     *
     * @param position1 The first position.
     * @param position2 The second position.
     */
    public PositionRegion(@NotNull Position position1, @NotNull Position position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    /**
     * Used to create a position region based
     * on a configuration section.
     *
     * @param section The instance of a configuration section.
     */
    @SuppressWarnings("all")
    public PositionRegion(@NotNull ConfigurationSection section) {
        this.convert(section);

        if (position1 == null || position2 == null) {
            throw new IllegalArgumentException("Region positions must not be null.");
        }
    }

    /**
     * Used to convert position regions into regions
     * and regions into position regions.
     *
     * @param <T> The region type.
     */
    public interface Converter<T> {

        /**
         * Used to convert a region into a position region.
         *
         * @param region The instance of the region to convert.
         * @return The position region.
         */
        @NotNull
        PositionRegion convert(@NotNull T region);

        /**
         * Used to convert a position region into a
         * region by specifying a world.
         *
         * @param region    The instance of the region.
         * @param worldName The world's name.
         * @return The instance of the region.
         */
        @NotNull
        T convert(@NotNull PositionRegion region, @NotNull String worldName);
    }

    @Override
    public String toString() {
        return "{PositionRegion: {pos1: " + position1 + ", pos2: " + position2 + "}}";
    }

    public @NotNull Position getPosition1() {
        return this.position1;
    }

    public @NotNull Position getPosition2() {
        return this.position2;
    }

    public @NotNull PositionRegion setPosition1(@NotNull Position position1) {
        this.position1 = position1;
        return this;
    }

    public @NotNull PositionRegion setPosition2(@NotNull Position position2) {
        this.position2 = position2;
        return this;
    }

    /**
     * Used to get the minimum position.
     * <p>
     * A position created by getting the lowest
     * coordinate, yaw and pitch.
     *
     * @return The minimum position.
     */
    public @NotNull Position getMinPosition() {
        return new Position(
                Math.min(this.position1.getX(), this.position2.getX()),
                Math.min(this.position1.getY(), this.position2.getY()),
                Math.min(this.position1.getZ(), this.position2.getZ()),
                Math.min(this.position1.getYaw(), this.position2.getYaw()),
                Math.min(this.position1.getPitch(), this.position2.getPitch())
        );
    }

    /**
     * Used to get the maximum position.
     * <p>
     * A position created by getting the highest
     * coordinate, yaw and pitch.
     *
     * @return The maximum position.
     */
    public @NotNull Position getMaxPosition() {
        return new Position(
                Math.max(this.position1.getX(), this.position2.getX()),
                Math.max(this.position1.getY(), this.position2.getY()),
                Math.max(this.position1.getZ(), this.position2.getZ()),
                Math.max(this.position1.getYaw(), this.position2.getYaw()),
                Math.max(this.position1.getPitch(), this.position2.getPitch())
        );
    }

    /**
     * Used to get the center of the region.
     * <p>
     * A position created by getting the min and max
     * position's adding them together then dividing by 2.
     *
     * @return The center position.
     */
    public @NotNull Position getCenter() {
        return new Position(
                (this.getMinPosition().getX() + this.getMaxPosition().getX()) / 2,
                (this.getMinPosition().getY() + this.getMaxPosition().getY()) / 2,
                (this.getMinPosition().getZ() + this.getMaxPosition().getZ()) / 2,
                (this.getMinPosition().getYaw() + this.getMaxPosition().getYaw()) / 2,
                (this.getMinPosition().getPitch() + this.getMaxPosition().getPitch()) / 2
        );
    }

    /**
     * Used to get the distance from the center of the region.
     *
     * @param position The instance of the position.
     * @return The distance between the center and given location.
     */
    public double getDistanceFromCenter(@NotNull Position position) {
        return PositionRegion.getDistance(position, this.getCenter());
    }

    /**
     * Used to expand the region in all directions.
     * <p>
     * The minimum point will be decreased and the max will be increased.
     * <p>
     * This will change the location
     * of the 2 positions in this region.
     * <p>
     *
     * @param x The amount to expand in the x direction.
     * @param y The amount to expand in the y direction.
     * @param z The amount to expand in the z direction.
     * @return This instance.
     */
    public @NotNull PositionRegion expand(double x, double y, double z) {
        Position min = this.getMinPosition();
        Position max = this.getMaxPosition();

        min.add(-x, -y, -z);
        max.add(x, y, z);

        this.position1 = min;
        this.position2 = max;
        return this;
    }

    /**
     * Used to convert this position region into the
     * platform's region implementation.
     *
     * @param converter The instance of the converter.
     * @param worldName The world name.
     * @param <T>       The region type.
     * @return The instance of the region.
     */
    public <T> @NotNull T convert(@NotNull Converter<T> converter, @NotNull String worldName) {
        return converter.convert(this, worldName);
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new LinkedHashMap<>());

        section.set("position1", this.position1.convert().getMap());
        section.set("position2", this.position2.convert().getMap());

        return section;
    }

    @Override
    public @NotNull PositionRegion convert(@NotNull ConfigurationSection section) {

        this.position1 = new Position(section.getSection("position1"));
        this.position2 = new Position(section.getSection("position2"));

        return this;
    }

    @Override
    public @NotNull PositionRegion duplicate() {
        return new PositionRegion(convert());
    }

    /**
     * Used to get the distance between two positions
     * in 3 dimensions.
     *
     * @param position1 The first position.
     * @param position2 The second position.
     * @return The distance between the two locations.
     */
    public static double getDistance(@NotNull Position position1, @NotNull Position position2) {
        return Math.abs(Math.sqrt(
                Math.pow((position1.getX() - position2.getX()), 2) +
                        Math.pow((position1.getY() - position2.getY()), 2) +
                        Math.pow((position1.getZ() - position2.getZ()), 2)
        ));
    }
}
