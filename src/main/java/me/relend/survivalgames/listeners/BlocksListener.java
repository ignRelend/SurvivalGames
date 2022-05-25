package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.GameMode;
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
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cThe game has not started yet!"));
            } else if (!plugin.getManager().getBlockManager().canBreak(event.getBlock().getType())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cYou cannot break this block!"));
            } else {
                plugin.getManager().getBlockManager().getBrockenBlocks().put(event.getBlock().getLocation(), event.getBlock().getType());
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cThe game has not started yet!"));
            } else if (!plugin.getManager().getBlockManager().canPlace(event.getBlock().getType())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.color("&cYou cannot place this block!"));
            } else {
                plugin.getManager().getBlockManager().getPlacedBlocks().put(event.getBlock().getLocation(), event.getBlock().getType());
            }
        }
    }
}
