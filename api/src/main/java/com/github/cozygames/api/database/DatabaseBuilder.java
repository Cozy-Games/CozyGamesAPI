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

package com.github.cozygames.api.database;

import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.implementation.mongo.MongoDatabase;
import com.github.smuddgge.squishydatabase.implementation.mysql.MySQLDatabase;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Represents the database builder.
 * <p>
 * This is used to build a database connection.
 * <p>
 * After obtaining an instance of the database,
 * the method {@link Database#setup()} can be called
 * to attempt to connect.
 */
public class DatabaseBuilder {

    private String type;
    private String path;
    private String connectionString;
    private String databaseName;

    public DatabaseBuilder() {
    }

    /**
     * Used to create a database builder.
     *
     * @param section The configuration section that contains
     *                the database connection infomation.
     * @param path The path that should be used for the sqlite
     *             database connection.
     */
    public DatabaseBuilder(@NotNull ConfigurationSection section, @NotNull String path) {
        this.type = section.getString("type").toUpperCase();
        this.path = path;
        this.connectionString = section.getString("connectionString");
        this.databaseName = section.getString("databaseName");
    }

    /**
     * Used to get the type of database as an
     * uppercase string.
     *
     * @return The type of database.
     */
    public @Nullable String getType() {
        return type;
    }

    /**
     * Used to get the path that's used to connect to
     * the sqlite database.
     *
     * @return The path to the sqlite database.
     */
    public @Nullable String getPath() {
        return path;
    }

    /**
     * Used to get the connection string.
     * <p>
     * This is the url used to connect to a database
     * such as Mongo or MySql.
     *
     * @return The connection string.
     */
    public @Nullable String getConnectionString() {
        return connectionString;
    }

    /**
     * Used to get the database's name.
     * <p>
     * This is used when connecting to a mongo database.
     *
     * @return The database's name.
     */
    public @Nullable String getDatabaseName() {
        return databaseName;
    }

    /**
     * Used to get the instance of the database factory
     * that corresponds to the database type specified
     * in the configuration.
     *
     * @return The instance of the database factory.
     */
    public @NotNull DatabaseFactory getFactory() {
        return DatabaseFactory.valueOf(this.getType());
    }

    /**
     * Used to build a connection to a database.
     *
     * @return The instance of the database.
     */
    public @NotNull Database build() {

        // Check if the database type is MYSQL.
        if (this.getFactory().equals(DatabaseFactory.MYSQL)) {
            if (this.getConnectionString() == null) throw new NullPointerException(
                    "You must specify a connection string for a MySQL database."
            );
            return new MySQLDatabase(this.getConnectionString());
        }

        // Check if the database type is MONGO.
        if (this.getFactory().equals(DatabaseFactory.MONGO)) {
            if (this.getConnectionString() == null) throw new NullPointerException(
                    "You must specify a connection string for a Mongo database."
            );
            if (this.getDatabaseName() == null) throw new NullPointerException(
                    "You must specify a database name for a Mongo database."
            );
            return new MongoDatabase(this.getConnectionString(), this.getDatabaseName());
        }

        // Check if the database type is SQLITE.
        if (this.getFactory().equals(DatabaseFactory.SQLITE)) {
            if (this.path == null) throw new NullPointerException(
                    "You must specify a path for a SQLite database."
            );
            return new SQLiteDatabase(new File(this.path));
        }

        // Otherwise, the database type is invalid.
        throw new NullPointerException("Unknown database type: " + this.getType());
    }
}
