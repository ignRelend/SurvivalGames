package me.relend.survivalgames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartCompleter implements TabCompleter {

    private static final String[] tabcompletion = {"force"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            //create new array
            List<String> completions = new ArrayList<>();
            //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
            StringUtil.copyPartialMatches(args[0], Arrays.asList(tabcompletion), completions);
            //sort the list
            Collections.sort(completions);
            return completions;
        } else {
            return null;
        }
    }
}
