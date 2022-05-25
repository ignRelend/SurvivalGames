package me.relend.survivalgames.manager;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.util.Util;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {

    private GameState gameState;
    private final BlockManager blockManager;
    private final KillManager killManager;
    private final SurvivalGames plugin;

    private ArrayList<Player> alive = new ArrayList<>();
    private ArrayList<Player> spectators = new ArrayList<>();

    private boolean timerCancelled = false;

    public GameManager(SurvivalGames plugin) {
        this.plugin = plugin;
        gameState = GameState.WAITING;
        blockManager = new BlockManager();
        killManager = new KillManager();
    }

    public void setGameState(GameState newGameState) {

        this.gameState = newGameState;
        switch (newGameState) {
            case WAITING:
                // dont let players move
                // give new players kit selector
                break;
            case COUNTDOWN:
                BossBar bossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
                for (Player p : getAlive()) {
                    bossBar.addPlayer(p);
                }
                BukkitTask countdown = new BukkitRunnable() {
                    int count = 30;
                    final int[] message = {30, 15, 10, 5, 3, 2, 1};

                    @Override
                    public void run() {
                        for (int item : message) {
                            bossBar.setTitle(Util.color("&aStarting in &2" + count));
                            bossBar.setProgress(count / (message[0] * 1.0));
                            if (item == count) {

                                if (item == 3) {
                                    Util.sendTitleAll("&a3", "", 3, 14, 3);
                                } else if (item == 2) {
                                    Util.sendTitleAll("&e2", "", 3, 14, 3);
                                } else if (item == 1) {
                                    Util.sendTitleAll("&c1", "", 3, 14, 3);
                                }
                                Util.broadcastAll(Util.color("&aThe game is starting in &e" + item + " &aseconds!"));
                                Util.playSoundAlive(Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                            }
                        }
                        if (count <= 0) {
                            if (alive.size() >= plugin.getConfig().getInt("arena.players-required-start")) {
                                setGameState(GameState.IN_GAME);
                            } else {
                                setGameState(GameState.WAITING);
                                Util.broadcastAll(Util.color("&c&lNot enough players to start!"));
                            }
                            for (Player p : bossBar.getPlayers()) {
                                bossBar.removePlayer(p);
                            }
                            cancel();
                        }
                        if (timerCancelled) {
                            for (Player p : bossBar.getPlayers()) {
                                bossBar.removePlayer(p);
                            }
                            cancel();
                        }
                        count--;
                    }
                }.runTaskTimer(plugin, 0L, 20L);
                timerCancelled = false;
                // dont let players move
                // give new players kit selector
                break;
            case IN_GAME:
                for (Player p : getAlive()) {
                    Util.resetPlayerStats(p);
                }
                for (String s : plugin.getConfig().getStringList("arena.chests.tier-1")) {
                    String[] loc = s.split(";");
                    Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                    Chest chest = (Chest) location.getBlock().getState();
                    Util.fillChest(chest, 1);
                }
                for (String s : plugin.getConfig().getStringList("arena.chests.tier-2")) {
                    String[] loc = s.split(";");
                    Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                    Chest chest = (Chest) location.getBlock().getState();
                    Util.fillChest(chest, 2);
                }
                for (String s : plugin.getConfig().getStringList("arena.chests.tier-3")) {
                    String[] loc = s.split(";");
                    Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                    Chest chest = (Chest) location.getBlock().getState();
                    Util.fillChest(chest, 3);
                }
                Util.broadcastAlive(Util.color("&a&lThe game has started!"));
                Util.sendTitleAlive("&a&lGO!", "The game has started!", 5, 50, 5);
                Util.playSoundAlive(Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 4f);
                // wait 30 seconds to give kits
                // let players move and fight
                break;
            case FINISH:
                BukkitTask reset = new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.getManager().setGameState(GameState.RESETTING);
                    }
                }.runTaskLater(plugin, 100);
                break;
            case RESETTING:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.kickPlayer("The game has ended!");
                }
                for (String s : plugin.getConfig().getStringList("arena.chests")) {
                    String[] loc = s.split(";");
                    Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                    Chest chest = (Chest) location.getBlock().getState();
                    chest.getInventory().clear();
                }
                for (Location loc : getBlockManager().getPlacedBlocks().keySet()) {
                    loc.getBlock().setType(Material.AIR);
                }
                for (Location loc : getBlockManager().getBrockenBlocks().keySet()) {
                    loc.getBlock().setType(getBlockManager().getBrockenBlocks().get(loc));
                }
                for (Entity entity : Bukkit.getWorld(plugin.getConfig().getString("arena.world")).getEntities()) {
                    if (entity.getType().equals(EntityType.DROPPED_ITEM) || entity.getType().equals(EntityType.ARROW)) {
                        entity.remove();
                    }
                }

                plugin.getServer().shutdown();
                break;
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public KillManager getKillManager() {
        return killManager;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public ArrayList<Player> getAlive() {
        return alive;
    }

    public ArrayList<Player> getSpectators() {
        return spectators;
    }

    public void setTimerCancelled(boolean timerCancelled) {
        this.timerCancelled = timerCancelled;
    }
}
