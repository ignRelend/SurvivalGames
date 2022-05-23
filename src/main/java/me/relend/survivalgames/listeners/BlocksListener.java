package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlocksListener implements Listener {

    private final SurvivalGames plugin;

    public BlocksListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Util.color("&cThe game has not started yet!"));
        } else {
            if (!plugin.getManager().getBlockManager().canBreak(event.getBlock().getType())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cYou cannot break this block!"));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Util.color("&cThe game has not started yet!"));
        } else {
            if (!plugin.getManager().getBlockManager().canPlace(event.getBlock().getType())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cYou cannot place this block!"));
            }
        }
    }

}
