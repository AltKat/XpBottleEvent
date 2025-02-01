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
import org.bukkit.scheduler.BukkitRunnable;

import static com.example.project.testPlugin.TestPlugin.lunaPREFIX;


public class EventCommand implements CommandExecutor {

    // Create a prefix for message
    public static final String lunaPREFIX = ChatColor.RED + "Lunaya" + ChatColor.GRAY + " >> " + ChatColor.WHITE;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Location eventLocation = new Location(Bukkit.getWorld("world"), -35, 63, -100); // set the world name and coordinate

        if (!(sender instanceof Player player)) { // If the sender is not a player -> console cannot start events
            sender.sendMessage(lunaPREFIX + ChatColor.RED + "Only players can use this command!");
            return true;
        }

        // check player permission
        if (!player.hasPermission("event.use")) {
            player.sendMessage(lunaPREFIX + ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        // event command menu
        if (args.length == 0) {
            player.sendMessage(lunaPREFIX + ChatColor.BLUE + "Event Command Usage:");
            player.sendMessage(lunaPREFIX + "/event start - Teleport to the event area.");
            player.sendMessage(lunaPREFIX + "/event location - Get the coordinates of the event.");
            player.sendMessage(lunaPREFIX + "/event info - Learn how to play the event.");
            return true;
        }

        // Run only for "/event start"
        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
            player.teleport(eventLocation);
            player.sendMessage(lunaPREFIX + ChatColor.AQUA + player.getName() + ChatColor.RESET + ", You were teleported to the event area.");
            return true;
        }

        // Run only for "/event location"
        if (args.length > 0 && args[0].equalsIgnoreCase("location")) {
            player.sendMessage(lunaPREFIX + "Coordinates of the event; in the " + ChatColor.GOLD + eventLocation.getWorld().getName() + ChatColor.RESET +
                    "\nX Coordinates = " + ChatColor.GOLD + eventLocation.getX() + ChatColor.RESET +
                    "\nY Coordinates = " + ChatColor.GOLD + eventLocation.getY() + ChatColor.RESET +
                    "\nZ Coordinates = " + ChatColor.GOLD + eventLocation.getZ()
            );
            return true;
        }

        // Run only for "/event info"
        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            player.sendMessage(lunaPREFIX + "Hello " + ChatColor.GREEN + player.getName() + ChatColor.RESET + ", Now I will teach you this event." +
                    ChatColor.GREEN + "\nFirstly, " + ChatColor.RESET + "this event is played with an xp bottle. " +
                    "The main purpose here is, ");
                    player.teleport(eventLocation);

                    // add wait 3 seconds
                    // 3 saniye beklemek için BukkitRunnable kullanabiliriz (ana iş parçacığını engellemeden) -> Sana attığım gibi olmadan yani :)

                TestPlugin plugin = TestPlugin.getInstance(); // ★★★ start time

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(lunaPREFIX + "You need to throw the XP bottle at the netherite blocks like a basketball, it gives you a reward if you hit it. ");
                        player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE));
                    }
                }.runTaskLater(plugin, 60L); // first task

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(lunaPREFIX + "Now hold these XP bottles and try!");
                    }
                }.runTaskLater(plugin, 120L); // second task

            return true;
        }

        player.sendMessage(lunaPREFIX + ChatColor.RED + "Invalid usage! Correct usage: /event <start | info | location>");
        return true;

    }

}