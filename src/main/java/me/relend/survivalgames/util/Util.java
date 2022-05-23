package me.relend.survivalgames.util;

import org.bukkit.ChatColor;

public class Util {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
