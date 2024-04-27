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

package com.github.cozygames.api.session;

import com.github.cozygames.api.arena.Arena;
import com.github.cozygames.api.map.Map;
import com.github.cozygames.api.plugin.CozyGamesPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents a game session.
 * <p>
 * This class is created within the cozy game plugin
 * and exists only if the plugin is enabled.
 *
 * @param <A> The arena class which is used.
 * @param <M> The map class which is in the arena.
 */
public abstract class Session<A extends Arena<A, M>, M extends Map<M>> {

    private final @NotNull String arenaIdentifier;
    private final @NotNull List<SessionComponent<A, M>> sessionComponentList;

    /**
     * Used to create a new session instance.
     *
     * @param arenaIdentifier The arena identifier.
     */
    public Session(@NotNull String arenaIdentifier) {
        this.arenaIdentifier = arenaIdentifier;
        this.sessionComponentList = new ArrayList<>();
    }

    /**
     * Used to get the instance of the plugin.
     *
     * @return The instance of the plugin.
     */
    public abstract @NotNull CozyGamesPlugin<?, A, M, ?> getPlugin();

    public @NotNull String getArenaIdentifier() {
        return this.arenaIdentifier;
    }

    /**
     * Used to get the instance of the arena via
     * the arena configuration in the related plugin.
     * <p>
     * All arena session should be stopped before the
     * arena instance is deleted.
     *
     * @return The optional arena.
     * @throws NoSuchElementException When the arena no longer exists.
     */
    public @NotNull A getArena() {
        return this.getPlugin().getArenaConfiguration()
                .getType(this.getArenaIdentifier())
                .orElseThrow();
    }

    public @NotNull List<SessionComponent<A, M>> getComponentList() {
        return this.sessionComponentList;
    }

    /**
     * Used to get the instance of a specific session component.
     *
     * @param clazz The session component class.
     * @return The optional registered session component.
     */
    @SuppressWarnings("all")
    public <C extends SessionComponent<A, M>> @NotNull Optional<C> getComponent(@NotNull Class<C> clazz) {
        for (SessionComponent<A, M> sessionComponent : this.sessionComponentList) {

            // Check if the class name are the same.
            if (sessionComponent.getClass().getName().equals(clazz.getName())) {
                return (Optional<C>) Optional.of(sessionComponent);
            }
        }
        return Optional.empty();
    }

    /**
     * Used to register a component in this session instance.
     * <p>
     * You can then get this component back from the
     * {@link Session#getComponent(Class)} method.
     *
     * @param sessionComponent The instance of the session component.
     * @return This instance.
     */
    public @NotNull Session<A, M> registerComponent(@NotNull SessionComponent<A, M> sessionComponent) {
        this.sessionComponentList.add(sessionComponent);
        return this;
    }

    public @NotNull Session<A, M> unregisterComponent(@NotNull SessionComponent<A, M> sessionComponent) {
        this.sessionComponentList.remove(sessionComponent);
        return this;
    }

    public <C extends SessionComponent<A, M>> @NotNull Session<A, M> unregisterComponent(@NotNull Class<C> clazz) {
        this.sessionComponentList.removeIf(sessionComponent -> sessionComponent.getClass().getName().equals(clazz.getName()));
        return this;
    }

    /**
     * Used to call the {@link SessionComponent#start()} method
     * for each registered component.
     *
     * @return This instance.
     */
    public @NotNull Session<A, M> startAllComponents() {
        for (SessionComponent<A, M> sessionComponent : this.sessionComponentList) {
            sessionComponent.start();
        }
        return this;
    }

    /**
     * Used to call the {@link SessionComponent#stop()} method
     * for each registered component.
     *
     * @return This instance.
     */
    public @NotNull Session<A, M> stopAllComponents() {
        for (SessionComponent<A, M> sessionComponent : this.sessionComponentList) {
            sessionComponent.stop();
        }
        return this;
    }
}
