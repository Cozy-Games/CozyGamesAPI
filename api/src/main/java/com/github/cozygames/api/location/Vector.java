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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a basic vector.
 */
public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public @NotNull Vector setX(double x) {
        this.x = x;
        return this;
    }

    public @NotNull Vector setY(double y) {
        this.y = y;
        return this;
    }

    public @NotNull Vector setZ(double z) {
        this.z = z;
        return this;
    }

    public @NotNull Vector add(Vector vector) {
        return new Vector(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    public @NotNull Vector add(double x, double y, double z) {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public @NotNull Vector add(double amount) {
        return new Vector(this.x + amount, this.y + amount, this.z + amount);
    }

    public @NotNull Vector subtract(Vector vector) {
        return new Vector(this.x - vector.getX(), this.y - vector.getY(), this.z - vector.getZ());
    }

    public @NotNull Vector subtract(double x, double y, double z) {
        return new Vector(this.x - x, this.y - y, this.z - z);
    }

    public @NotNull Vector subtract(double amount) {
        return new Vector(this.x - amount, this.y - amount, this.z - amount);
    }

    public @NotNull Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public @NotNull Vector multiply(double x, double y, double z) {
        return new Vector(this.x * x, this.y * y, this.z * z);
    }

    public @NotNull Vector divide(double scalar) {
        return new Vector(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public @NotNull Vector divide(double x, double y, double z) {
        return new Vector(this.x / x, this.y / y, this.z / z);
    }
}
