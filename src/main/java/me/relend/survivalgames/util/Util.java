package me.relend.survivalgames.util;

import me.relend.survivalgames.SurvivalGames;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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
}
