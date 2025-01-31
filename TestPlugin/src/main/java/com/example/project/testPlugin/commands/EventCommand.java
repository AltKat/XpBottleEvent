package com.example.project.testPlugin.commands;

import com.example.project.testPlugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventCommand implements CommandExecutor {

    // Create a prefix for message
    public static final String Prefix = ChatColor.RED + "Lunaya | " + ChatColor.WHITE;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Location eventLocation = new Location(Bukkit.getWorld("world"), -35, 63, -100); // set the world name and coordinate

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
            player.teleport(eventLocation);
            player.sendMessage(Prefix + ChatColor.AQUA + player.getName() + ChatColor.RESET + ", You were teleported to the event area.");
            return true;
        }

        // Run only for "/event location"
        if (args.length > 0 && args[0].equalsIgnoreCase("location")) {
            player.sendMessage(Prefix + "Coordinates of the event; in the " + ChatColor.GOLD + eventLocation.getWorld().getName() + ChatColor.RESET +
                    "\nX Coordinates = " + ChatColor.GOLD + eventLocation.getX() + ChatColor.RESET +
                    "\nY Coordinates = " + ChatColor.GOLD + eventLocation.getY() + ChatColor.RESET +
                    "\nZ Coordinates = " + ChatColor.GOLD + eventLocation.getZ()
            );
            return true;
        }

        // Run only for "/event info"
        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            player.sendMessage(Prefix + "Hello " + ChatColor.GREEN + player.getName() + ChatColor.RESET + ", Now I will teach you this event." +
                    ChatColor.GREEN + "\nFirstly, " + ChatColor.RESET + "this event is played with an xp bottle. " +
                    "The main purpose here is, " + player.teleport(eventLocation));

                    // add wait 3 seconds
                    // 3 saniye beklemek için BukkitRunnable kullanabiliriz (ana iş parçacığını engellemeden) -> Sana attığım gibi olmadan yani :)

            TestPlugin plugin = TestPlugin.getInstance(); // ★★★

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(Prefix + "Throwing the XP bottle at the netherite blocks like a basketball, " +
                            "come on try!" + player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE)));
                }
            }.runTaskLater(plugin, 60L);
            return true;
        }

        player.sendMessage(Prefix + ChatColor.RED + "Invalid usage! Correct usage: /event <start|join|info|location>");
        return true;
    }

}