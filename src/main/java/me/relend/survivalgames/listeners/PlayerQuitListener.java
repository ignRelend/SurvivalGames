package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerQuitListener implements Listener {

    private final SurvivalGames plugin;

    public PlayerQuitListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getManager().getAlive().contains(event.getPlayer())) {
            for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
            }
            plugin.getManager().getAlive().remove(event.getPlayer());
            event.setQuitMessage(Util.color("&e" + event.getPlayer().getName() + " &7has quit!"));
            if (plugin.getManager().getAlive().size() <= 1) {
                plugin.getManager().setGameState(GameState.FINISH);
                Util.sendTitleAll("&c&lGAME OVER", "Better luck next time!", 5, 70, 5);
                if (plugin.getManager().getAlive().get(0) != null) {
                    Player winner = plugin.getManager().getAlive().get(0);
                    winner.sendTitle(Util.color("&6&lVICTORY"), "You were the last one standing!", 5, 70, 5);
                }
            }
        } else {
            plugin.getManager().getSpectators().remove(event.getPlayer());
            event.setQuitMessage(null);
        }
    }

}
