package me.relend.survivalgames.commands;

import me.relend.survivalgames.SurvivalGames;
import me.relend.survivalgames.manager.GameState;
import me.relend.survivalgames.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    private final SurvivalGames plugin;

    public StartCommand(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("survivalgames.start")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("force")) {
                    if (plugin.getManager().getGameState() == GameState.WAITING || plugin.getManager().getGameState() == GameState.COUNTDOWN) {
                        plugin.getManager().setGameState(GameState.IN_GAME);
                        plugin.getManager().setTimerCancelled(true);
                    } else {
                        sender.sendMessage(Util.color("&cYou cannot do this right now!"));
                    }
                }
            } else {
                if (plugin.getManager().getGameState() == GameState.WAITING) {
                    plugin.getManager().setGameState(GameState.COUNTDOWN);
                } else {
                    sender.sendMessage(Util.color("&cYou cannot do this right now!"));
                }
            }
        } else {
            sender.sendMessage(Util.color("&cNo permission."));
        }
        return false;
    }
}
