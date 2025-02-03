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



public class EventCommand implements CommandExecutor {

    // Create a prefix for message
    public static final String lunaPREFIX = ChatColor.RED + "Lunaya" + ChatColor.GRAY + " >> " + ChatColor.WHITE;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Location eventLocation = new Location(Bukkit.getWorld("hub"), 0, 70, -90); // set the world name and coordinate
        Location infoLocation = new Location(Bukkit.getWorld("hub"), 129.5, 68, -51.5);


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
            player.sendMessage(lunaPREFIX + "/event information - Learn how to play the event.");
            return true;
        }

        // Run only for "/event start"
        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
             TestPlugin plugin = TestPlugin.getInstance();
             onlinePlayer.sendMessage(lunaPREFIX + "Hello! " + ChatColor.AQUA + onlinePlayer.getName() + ChatColor.RESET + ", Xp bottle event will start in 1 minute! ");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        onlinePlayer.sendMessage(lunaPREFIX + "If you do not know the xp bottle event, you can find out by typing " + ChatColor.GOLD + "/event info");
                    }
                }.runTaskLater(plugin, 60L);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        onlinePlayer.sendMessage(lunaPREFIX + "Those who want to join the xp bottle event can type. " + ChatColor.GOLD + "/event join");
                    }
                }.runTaskLater(plugin, 1100L);
            }
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
        if (args.length > 0 && (args[0].equalsIgnoreCase("info") || args.length > 0 && args[0].equalsIgnoreCase("information"))) { // We linked the two commands to the same function.
            Location startLocatin = player.getLocation(); // start location
            player.sendMessage(lunaPREFIX + "Hello " + ChatColor.GREEN + player.getName() + ChatColor.RESET + ", Now I will teach you this event." +
                    ChatColor.GREEN + "\nFirstly, " + ChatColor.RESET + "this event is played with an xp bottle. " +
                    "The main purpose here is, ");
                    player.teleport(infoLocation);

                TestPlugin plugin = TestPlugin.getInstance(); // ★★★ start time
                // add wait 3 seconds
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(lunaPREFIX + "You need to throw the XP bottle at the netherite blocks like a basketball, it gives you a reward if you hit it. ");
                    }
                }.runTaskLater(plugin, 60L); // first task

                // add wait 3 seconds
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 5));
                        player.sendMessage(lunaPREFIX + "Now hold these XP bottles and try!");
                    }
                }.runTaskLater(plugin, 120L); // second task



                // Teleport to starting location 5 seconds after finishing XP bottles
                new BukkitRunnable() { // The first BukkitRunnable checks every second to see if XP bottles have run out.

                    @Override
                    public void run() {

                        int currentXPCount = 0;


                        for (ItemStack item : player.getInventory().getContents()) {
                            if (item != null && item.getType() == Material.EXPERIENCE_BOTTLE) {
                                currentXPCount += item.getAmount();
                            }
                        }
                        // Bukkit.broadcastMessage(String.valueOf(currentXPCount)); // delete

                        if (currentXPCount == 0) {
                            this.cancel(); // Stop the BukkitRunnable
                            player.sendMessage(lunaPREFIX + "Congratulations, you can now continue where you left off. " + ChatColor.GREEN + "After 5 seconds");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.teleport(startLocatin);
                                    player.sendMessage(lunaPREFIX + ChatColor.GREEN + "You are back at your start position!");
                                }
                            }.runTaskLater(plugin, 100L); // 5 seconds

                        }
                    }
                }.runTaskTimer(plugin, 130L, 30L); // Check every second.
            return true;
        }

        player.sendMessage(lunaPREFIX + ChatColor.RED + "Invalid usage! Correct usage: /event <start | info | location>");
        return true;

    }

}