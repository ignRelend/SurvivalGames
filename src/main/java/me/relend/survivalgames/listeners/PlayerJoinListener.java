package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SurvivalGames plugin;

    public PlayerJoinListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Util.addPlayer(event.getPlayer())) {
            event.setJoinMessage(Util.color("&e" + event.getPlayer().getName() + " &7has joined the game! &2(&f" + plugin.getManager().getAlive().size() + "&a/&f" + plugin.getConfig().getStringList("arena.spawnpoints").size() + "&2)"));
        } else {
            event.setJoinMessage(null);
        }
    }
}
