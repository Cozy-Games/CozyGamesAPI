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

package com.github.cozygames.bukkit.session.component;

import com.github.cozygames.api.session.Session;
import com.github.cozygames.api.session.SessionComponent;
import com.github.cozygames.bukkit.arena.ExampleArena;
import com.github.cozygames.bukkit.map.ExampleMap;
import com.github.cozygames.bukkit.session.ExampleSession;
import com.github.cozyplugins.cozylibrary.scoreboard.Scoreboard;
import com.github.cozyplugins.cozylibrary.task.TaskContainer;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExampleScoreboardComponent extends TaskContainer implements SessionComponent<ExampleArena, ExampleMap> {

    private static final @NotNull String SCOREBOARD_TASK_IDENTIFIER = "SCOREBOARD_TASK_IDENTIFIER";

    private final @NotNull ExampleSession session;

    public ExampleScoreboardComponent(@NotNull ExampleSession session) {
        this.session = session;
    }

    @Override
    public @NotNull Session<ExampleArena, ExampleMap> getSession() {
        return this.session;
    }

    @Override
    public void start() {

        // Update scoreboards every second.
        this.runTaskLoop(SCOREBOARD_TASK_IDENTIFIER, () -> {

            // Loop though all the online members.
            for (Player player : this.getSession().getGroup().getMembersOnline(Player.class)) {
                PlayerUser user = new PlayerUser(player);
                user.setScoreboard(this.createCurrentScoreboard());
            }
        }, 20);
    }

    @Override
    public void stop() {

    }

    public @NotNull Scoreboard createCurrentScoreboard() {
        return new Scoreboard()
                .setTitle("&e&lEXAMPLE")
                .addLines("&8" + this.getSession().getArena().getWorldName().substring(0, 20))
                .addLines("&7")
                .addLines("&eplay.example.com");
    }
}
