package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final SurvivalGames plugin;

    public InventoryClickListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            event.setCancelled(true);
        }
    }
}
