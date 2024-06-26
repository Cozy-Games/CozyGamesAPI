/*
 * DeveloperTools
 * A library of tools helpful in testing and development.
 *
 * Copyright (C) 2023  MineManiaUK Staff
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

package com.github.cozygames.api.testing;

import com.github.cozygames.api.logger.Console;
import com.github.cozygames.api.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a result checker.
 * Create a new instance of this class to
 * compare results.
 */
public class ResultChecker {

    private final Logger logger = new Logger(false, false).setBothPrefixes("&a[TEST] &r");
    private final List<Runnable> fallBackRunnableList = new ArrayList<>();

    /**
     * Used to check if a boolean value is true.
     * If false, the test will fail.
     *
     * @param condition The boolean value.
     * @return This instance.
     */
    public @NotNull ResultChecker expect(boolean condition) {
        if (condition) {
            this.logger.log("&aPassed");
            return this;
        }

        this.runFallBack();
        Assertions.assertTrue(condition);
        return this;
    }

    /**
     * Used to check if two values are the same.
     * If they are not equal the test will fail.
     *
     * @param value1 The first value.
     * @param value2 The second value.
     * @return This instance.
     */
    public @NotNull ResultChecker expect(Object value1, Object value2) {
        if (value1.equals(value2)) {
            this.logger.log("&aPassed");
            this.logger.log("&7Value 1 &r: &e" + value1);
            this.logger.log("&7Value 2 &r: &e" + value2);
            return this;
        }

        this.runFallBack();
        Assertions.assertEquals(value1, value2);
        return this;
    }

    /**
     * Used to add a runnable that will be
     * executed before an error occurs.
     *
     * @param runnable The instance of the runnable.
     * @return This instance.
     */
    public @NotNull ResultChecker fallBack(@NotNull Runnable runnable) {
        this.fallBackRunnableList.add(runnable);
        return this;
    }

    /**
     * Used to add a warning message that will
     * be sent to console before an error occurs.
     *
     * @param message The instance of the message.
     * @return This instance.
     */
    public @NotNull ResultChecker fallBack(@NotNull String message) {
        return this.fallBack(() -> Console.warn(message));
    }

    /**
     * Used to run the fallback lists.
     * This method should be called before an error occurs.
     *
     * @return This instance.
     */
    private @NotNull ResultChecker runFallBack() {
        for (Runnable runnable : this.fallBackRunnableList) {
            runnable.run();
        }
        return this;
    }

    /**
     * Used to run a runnable.
     *
     * @param runnable The instance of a runnable.
     * @return This instance.
     */
    public @NotNull ResultChecker then(@NotNull Runnable runnable) {
        runnable.run();
        return this;
    }

    /**
     * Used to log a message in console.
     *
     * @param message The instance of a message.
     * @return This instance.
     */
    public @NotNull ResultChecker then(@NotNull String message) {
        this.logger.log(message);
        return this;
    }
}
