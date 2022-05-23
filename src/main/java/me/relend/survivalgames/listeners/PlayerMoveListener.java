package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final SurvivalGames plugin;

    public PlayerMoveListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Util.color("&cThe game has not started yet!"));
        }
    }

}
