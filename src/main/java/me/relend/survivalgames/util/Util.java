package me.relend.survivalgames.util;

import me.relend.survivalgames.SurvivalGames;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
}
