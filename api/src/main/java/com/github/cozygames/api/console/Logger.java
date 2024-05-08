package com.github.cozygames.api.console;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a console logger.
 * Used to log messages and warnings in
 * the console with custom prefixes.
 */
public class Logger {

    private boolean hasGlobalPrefix;
    private @Nullable String logPrefix = "";
    private @Nullable String warnPrefix = "";

    /**
     * Used to create a new instance of
     * a console logger.
     *
     * @param hasGlobalPrefix True if the global prefix should be used.
     */
    public Logger(boolean hasGlobalPrefix) {
        this.hasGlobalPrefix = hasGlobalPrefix;
    }

    public @Nullable String getLogPrefix() {
        return this.logPrefix;
    }

    public @Nullable String getWarnPrefix() {
        return this.warnPrefix;
    }

    public @NotNull Logger setLogPrefix(@Nullable String localLogPrefix) {
        this.logPrefix = localLogPrefix;
        return this;
    }

    public @NotNull Logger setWarnPrefix(@Nullable String localWarnPrefix) {
        this.warnPrefix = localWarnPrefix;
        return this;
    }

    public @NotNull Logger setBothPrefixes(@Nullable String localPrefix) {
        this.logPrefix = localPrefix;
        this.warnPrefix = localPrefix;
        return this;
    }

    /**
     * Used to log a message in console
     * using this instance of the local console
     * with the custom prefixes.
     *
     * @param message The message to log.
     * @return This instance.
     */
    public @NotNull Logger log(@NotNull String message) {
        System.out.println(ConsoleColor.parse(this.getMessageAsLog(message)));
        return this;
    }

    /**
     * Used to get the message as a log with prefixes.
     *
     * @param message The instance of the message.
     * @return The message with prefixes.
     */
    public @NotNull String getMessageAsLog(@NotNull String message) {
        String prefix = (this.hasGlobalPrefix ? Console.getLogPrefix() : "")
                + (this.logPrefix == null ? "" : this.logPrefix);

        return prefix + message;
    }

    /**
     * Used to log a warning in console
     * using this instance of the local console
     * with the custom prefixes.
     *
     * @param message The message to log.
     * @return This instance.
     */
    public @NotNull Logger warn(@NotNull String message) {
        System.out.println(ConsoleColor.parse(this.getMessageAsWarn(message)));
        return this;
    }

    /**
     * Used to get the message as a warning.
     * Adds the prefixes to the message.
     *
     * @param message The instance of the message.
     * @return The message with prefixes.
     */
    public @NotNull String getMessageAsWarn(@NotNull String message) {
        String prefix = (this.hasGlobalPrefix ? Console.getWarnPrefix() : "")
                + (this.warnPrefix == null ? "" : this.warnPrefix);

        return prefix + message;
    }

    public boolean hasGlobalPrefix() {
        return this.hasGlobalPrefix;
    }

    /**
     * Used to create a new instance of a local console
     * class with an extension to the prefixes.
     *
     * @param extensionPrefix The extension to add.
     * @return The new instance of the local console logger.
     */
    public @NotNull Logger createExtension(@Nullable String extensionPrefix) {
        Logger clone = this.clone();
        clone.setLogPrefix(clone.logPrefix + extensionPrefix);
        clone.setWarnPrefix(clone.warnPrefix + extensionPrefix);
        return clone;
    }

    @Override
    protected @NotNull Logger clone() {
        return new Logger(this.hasGlobalPrefix)
                .setLogPrefix(this.logPrefix)
                .setWarnPrefix(this.warnPrefix);
    }
}
