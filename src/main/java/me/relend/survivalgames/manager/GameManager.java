package me.relend.survivalgames.manager;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class GameManager {

    private GameState gameState;
    private final BlockManager blockManager;
    private final SurvivalGames plugin;

    private ArrayList<Player> alive = new ArrayList<>();
    private ArrayList<Player> spectators = new ArrayList<>();

    private boolean timerCancelled = false;

    public GameManager(SurvivalGames plugin) {
        this.plugin = plugin;
        gameState = GameState.WAITING;
        blockManager = new BlockManager();
    }

    public void setGameState(GameState newGameState) {
        if (this.gameState == GameState.WAITING && gameState == GameState.COUNTDOWN) return;
        if (this.gameState == GameState.COUNTDOWN && gameState == GameState.IN_GAME) return;
        if (this.gameState == GameState.IN_GAME && gameState == GameState.FINISH) return;
        if (this.gameState == GameState.FINISH && gameState == GameState.RESETTING) return;
        if (this.gameState == GameState.RESETTING && gameState == GameState.WAITING) return;

        this.gameState = newGameState;
        switch (newGameState) {
            case WAITING:
                // dont let players move
                // give new players kit selector
                break;
            case COUNTDOWN:
                BukkitTask countdown = new BukkitRunnable() {
                    int count = 30;
                    final int[] message = {30, 15, 10, 5, 3, 2, 1};

                    @Override
                    public void run() {
                        for (int item : message) {
                            if (item == count) {
                                if (item == 3) {
                                    Util.sendTitleAlive("&a3", "", 3, 14, 3);
                                } else if (item == 2) {
                                    Util.sendTitleAlive("&e2", "", 3, 14, 3);
                                }
                                else if (item == 1) {
                                    Util.sendTitleAlive("&c1", "", 3, 14, 3);
                                }
                                Util.broadcastAlive(Util.color("&aThe game is starting in &e" + item + " &aseconds!"));
                                Util.playSoundAlive(Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                            }
                        }
                        if (count <= 0) {
                            if (alive.size() >= plugin.getConfig().getInt("arena.start-amount")) {
                                setGameState(GameState.IN_GAME);
                            } else {
                                setGameState(GameState.WAITING);
                                Util.broadcastAlive(Util.color("&c&lNot enough players to start!"));
                            }
                            cancel();
                        }
                        if (timerCancelled) {
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
                for (String s : plugin.getConfig().getStringList("arena.chests")) {
                    String[] loc = s.split(";");
                    Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                    Chest chest = (Chest) location.getBlock().getState();
                    Util.fillChest(chest);
                }
                Util.broadcastAlive(Util.color("&a&lThe game has started!"));
                Util.sendTitleAlive("&a&lGO!", "The game has started!", 5, 50, 5);
                Util.playSoundAlive(Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 4f);
                // wait 30 seconds to give kits
                // let players move and fight
                break;
            case FINISH:
                // display winning text
                // send winning chat message
                // dont let players fight
                break;
            case RESETTING:
                // reset worlds
                // send players to hub
                break;
        }
    }

    public GameState getGameState() {
        return gameState;
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
