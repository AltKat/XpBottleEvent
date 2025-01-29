package com.example.project.testPlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand implements CommandExecutor {

    // Create a prefix for message
    public static final String Prefix = ChatColor.RED + "Lunaya | " + ChatColor.WHITE;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) { // If the sender is not a player
            sender.sendMessage(Prefix + ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        // check player permission
        if (!player.hasPermission("event.use")) {
            player.sendMessage(Prefix + ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        // Run only for "/event start"
        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
            Location eventLocation = new Location(Bukkit.getWorld("world"), -35, 63, -100); // set the world name and coordinate
            player.teleport(eventLocation);
            player.sendMessage(Prefix + ChatColor.AQUA + player.getName() + ChatColor.RESET + ", You were teleported to the event area.");
            return true;
        }

        player.sendMessage(Prefix + ChatColor.RED + "Invalid usage! Correct usage: /event start");
        return true;
    }

}