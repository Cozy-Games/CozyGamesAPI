package com.github.cozygames.api.indicator;

import org.jetbrains.annotations.NotNull;

/**
 * Indicates if a class can be duplicated.
 *
 * @param <T> The final class where this interface is implemented.
 *            This will be the object returned when duplicated.
 */
public interface Replicable<T> {

    /**
     * Used to duplicate the class instance
     * which this is implemented in.
     *
     * @return The instance of the duplicated class.
     */
    @NotNull T duplicate();
}
