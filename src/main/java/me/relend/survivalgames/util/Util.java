package me.relend.survivalgames.util;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Random;

public class Util {

    private static final SurvivalGames plugin = JavaPlugin.getPlugin(SurvivalGames.class);

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void broadcastAlive(String message) {
        for (Player p : plugin.getManager().getAlive()) {
            p.sendMessage(message);
        }
    }

    public static void broadcastAll(String message) {
        for (Player p : plugin.getManager().getAlive()) {
            p.sendMessage(message);
        }
        for (Player p : plugin.getManager().getSpectators()) {
            p.sendMessage(message);
        }
    }

    public static void playSoundAlive(Sound sound, float volume, float pitch) {
        for (Player p : plugin.getManager().getAlive()) {
            p.playSound(p.getLocation(), sound, volume, pitch);
        }
    }

    public static void sendTitleAlive(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player p : plugin.getManager().getAlive()) {
            p.sendTitle(color(title), color(subtitle), fadeIn, stay, fadeOut);
        }
    }

    public static void sendTitleAll(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player p : plugin.getManager().getAlive()) {
            p.sendTitle(color(title), color(subtitle), fadeIn, stay, fadeOut);
        }
        for (Player p : plugin.getManager().getSpectators()) {
            p.sendTitle(color(title), color(subtitle), fadeIn, stay, fadeOut);
        }
    }

    public static void fillChest(Chest chest) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for (String s : plugin.getConfig().getStringList("chest-items")) {
            String[] item = s.split(":");
            items.add(new ItemStack(Material.valueOf(item[0].toUpperCase()), Integer.parseInt(item[1])));
        }
        Random random = new Random();
        chest.getInventory().clear();
        int amount = random.nextInt(12 - 5 + 1) + 5;
        for (int i = 0; i < amount; i++) {
            int item = random.nextInt(items.size());
            int slot = random.nextInt(26 + 1);
            chest.getInventory().setItem(slot, items.get(item));
        }
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static boolean addPlayer(Player player) {
        if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
            // game is not running
            if (plugin.getManager().getAlive().size() < plugin.getConfig().getStringList("arena.spawnpoints").size()) {
                // enough space for new player
                String[] loc = plugin.getConfig().getStringList("arena.spawnpoints").get(plugin.getManager().getAlive().size()).split(";");
                Location location = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                player.teleport(location);
                player.setGameMode(GameMode.SURVIVAL);
                resetPlayerStats(player);
                plugin.getManager().getAlive().add(player);
                return true;
            } else {
                // not enough space / add to spectator
                plugin.getManager().getSpectators().add(player);
                player.sendMessage(Util.color("&cThere is not enough room for you! You are now a spectator."));
                player.setGameMode(GameMode.SPECTATOR);
                resetPlayerStats(player);
                return false;
            }
        } else {
            // game is already running / add to spectator
            plugin.getManager().getSpectators().add(player);
            player.sendMessage(Util.color("&cThe game has already started! You are now a spectator."));
            player.setGameMode(GameMode.SPECTATOR);
            resetPlayerStats(player);
            return false;
        }
    }

    public static void resetPlayerStats(Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0);
        player.getInventory().clear();
        player.setAbsorptionAmount(0);
        player.getActivePotionEffects().clear();
    }
}
