package me.relend.survivalgames.listeners;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerDeathListener implements Listener {

    private final SurvivalGames plugin;

    public PlayerDeathListener(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (plugin.getManager().getAlive().contains(event.getEntity())) {
            event.setDeathMessage(null);
            plugin.getManager().getSpectators().add(event.getEntity());
            plugin.getManager().getAlive().remove(event.getEntity());
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            if (event.getEntity().getKiller() != null) {
                plugin.getManager().getKillManager().addKill(event.getEntity().getKiller(), event.getEntity());
                Util.broadcastAll(Util.color("&a" + event.getEntity().getKiller().getName() + " &7has killed &c" + event.getEntity().getName() + "&7!" + " (&f" + plugin.getManager().getKillManager().getKillsFor(event.getEntity().getKiller()).size() + "&7)"));
            } else {
                Util.broadcastAll(Util.color("&c" + event.getEntity().getName() + " &7has died!"));
            }
        }
        if (plugin.getManager().getAlive().size() <= 1) {
            plugin.getManager().setGameState(GameState.FINISH);
            Util.sendTitleAll("&c&lGAME OVER", "Better luck next time!", 5, 70, 5);
            try {
                Player winner = plugin.getManager().getAlive().get(0);
                winner.sendTitle(Util.color("&6&lVICTORY"), "You were the last one standing!", 5, 70, 5);
            } catch (Exception ignored) {}
        }
    }

}
