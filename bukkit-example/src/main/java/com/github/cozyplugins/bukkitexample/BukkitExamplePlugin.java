package com.github.cozyplugins.bukkitexample;

import com.github.cozygames.api.CozyGames;
import com.github.cozygames.api.CozyGamesProvider;
import com.github.cozygames.api.location.Position;
import com.github.cozygames.api.location.ServerLocation;
import com.github.cozygames.api.map.GlobalMap;
import com.github.cozygames.api.member.Member;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class BukkitExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // Get the api instance.
        CozyGames api = this.getAPI();

        // Example of getting the list of available maps.
        List<GlobalMap> globalMapList = api.getMapManager().getGlobalMapList();

        // Example of getting a member.
        // This will first try to find them on the server.
        // Otherwise, it will search the database.
        Member member = api.getMember("Smudyy");

        // Teleport a member to a server.
        member.teleport(new ServerLocation("lobby", "world", new Position(0, 100, 0)));

        // Example of converting a member to a player.
        Player player = member.getPlayer(Player.class).orElse(null);
    }

    @Override
    public void onDisable() {

    }

    /**
     * Used to get the instance of the server's
     * cozy games api connection.
     *
     * @return The instance of the cozy games api.
     */
    public @NotNull CozyGames getAPI() {

        // Get the instance of the cozy games service provider.
        RegisteredServiceProvider<CozyGames> provider = getServer().getServicesManager().getRegistration(CozyGames.class);

        // Check if the cozy games api has not been registered yet.
        if (provider == null) throw new CozyGamesProvider.APINotLoadedException();

        // Return the api instance.
        return provider.getProvider();
    }
}
