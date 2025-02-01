package com.example.project.testPlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;

public class XPBottleBreakListener implements Listener{
    // Create a prefix for message
    public static final String lunaPREFIX = ChatColor.RED + "Lunaya" + ChatColor.GRAY + " >> " + ChatColor.WHITE;
    @EventHandler
    public void onXpBottleBreak(ExpBottleEvent event) {
        Block block = event.getHitBlock();

        // Checking where xp bottle hit
        if (block != null) {
            // If the block hit by xp bottle is a netherite block
            if (block.getType() == Material.NETHERITE_BLOCK) {
                if (event.getEntity().getShooter() instanceof Player player) { // A dispenser that throws does not enter the code block

                    Location location = event.getEntity().getLocation();
                    System.out.println(event.getEntity().getLocation()); // Get location

                    // The message to be sent if the block hit by the xp bottle is a netherite block (for everyone)
                    Bukkit.broadcastMessage(lunaPREFIX + "Congratulations " +
                            ChatColor.AQUA + player.getName() + ChatColor.RESET +
                            ", Netherite block was successfully broken and it became a score!"); // Player's name is aqua other messages are white

                    // Give the player the Netherite ingot
                    player.getInventory().addItem(new ItemStack(Material.NETHERITE_INGOT));
                    // for falling block
                    block.setType(Material.AIR);
                }
            } else {
                // Message for other block types (only for the player who throw)
                if (event.getEntity().getShooter() instanceof Player player) {

                    player.sendMessage(lunaPREFIX + "Nice try " + ChatColor.AQUA + player.getName() + ChatColor.RESET + ", Try again!");
                }
                block.breakNaturally(); // for falling others blocks
            }
        }
    }
}
