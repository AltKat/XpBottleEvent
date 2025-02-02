package com.example.project.testPlugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class EventTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Subcommands of the event command
            completions.add("start");
            completions.add("stop");
            completions.add("info");
            completions.add("information");
            completions.add("location");
            completions.add("join");
        }

        if (args.length == 2) {
            // ADD
        }

        return completions;
    }
}