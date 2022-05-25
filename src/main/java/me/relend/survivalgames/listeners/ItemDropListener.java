package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    private final SurvivalGames plugin;

    public ItemDropListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            event.setCancelled(true);
        }
    }

}
