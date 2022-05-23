package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    private final SurvivalGames plugin;

    public PlayerJoinListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            plugin.getManager().getAlive().add(event.getPlayer());
            plugin.getManager().getSpectators().remove(event.getPlayer());
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.setJoinMessage(Util.color("&e" + event.getPlayer().getName() + " &7has joined the game! &2(&f" + plugin.getManager().getAlive().size() + "&a/&f" + plugin.getConfig().getInt("arena.start-amount") + "&2)"));
            if (plugin.getManager().getGameState() == GameState.WAITING) {
                if (plugin.getManager().getAlive().size() >= plugin.getConfig().getInt("arena.start-amount")) {
                    plugin.getManager().setGameState(GameState.COUNTDOWN);
                }
            }
            event.getPlayer().getInventory().addItem(new ItemStack(Material.BOW));
        } else {
            plugin.getManager().getSpectators().add(event.getPlayer());
            plugin.getManager().getAlive().remove(event.getPlayer());
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            event.getPlayer().sendMessage(Util.color("&cThe game has already started, so you are now a spectator!"));
            event.setJoinMessage(null);
        }
    }
}
