package me.relend.survivalgames.commands;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.util.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaCommand implements CommandExecutor {

    private final SurvivalGames plugin;

    public ArenaCommand(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("survivalgames.arena")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("addchest")) {
                        if (player.getTargetBlock(null, 3).getType().equals(Material.CHEST)) {
                            try {
                                String loc = player.getTargetBlock(null, 3).getX() + ";" + player.getTargetBlock(null, 3).getY() + ";" + player.getTargetBlock(null, 3).getZ() + ";" + player.getWorld().getName();
                                List<String> chests = plugin.getConfig().getStringList("arena.chests");
                                chests.add(loc);
                                plugin.getConfig().set("arena.chests", chests);
                                plugin.saveConfig();
                                player.sendMessage(Util.color("&aAdded the chest to the arena at location &2" + loc + "&a!"));
                            } catch (Exception e) {
                                player.sendMessage(Util.color("&cError adding the chest to the arena!"));
                            }
                        } else {
                            player.sendMessage(Util.color("&cYou are not looking at a chest!"));
                        }
                    } else if (args[0].equalsIgnoreCase("addspawn")) {
                        try {
                            String loc = Util.round(player.getLocation().getX(), 1) + ";" + Util.round(player.getLocation().getY(), 1) + ";" + Util.round(player.getLocation().getZ(), 1) + ";" + player.getWorld().getName();
                            List<String> spawnpoints = plugin.getConfig().getStringList("arena.spawnpoints");
                            spawnpoints.add(loc);
                            plugin.getConfig().set("arena.spawnpoints", spawnpoints);
                            plugin.saveConfig();
                            player.sendMessage(Util.color("&aAdded the spawnpoint to the arena at location &2" + loc + "&a!"));
                        } catch (Exception e) {
                            player.sendMessage(Util.color("&cError adding the spawnpoint to the arena!"));
                        }
                    } else {
                        player.sendMessage("&c/arena <addspawn|addchest>");
                    }
                }
            } else {
                player.sendMessage(Util.color("&cNo permission."));
            }
        } else {
            System.out.println("This command is only available to players!");
        }
        return false;
    }
}
