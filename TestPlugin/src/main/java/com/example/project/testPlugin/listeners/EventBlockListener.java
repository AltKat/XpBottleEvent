package com.example.project.testPlugin.listeners;

import com.example.project.testPlugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class EventBlockListener implements Listener {
    private final TestPlugin plugin;

    public EventBlockListener(TestPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        System.out.println("Block broken: " + block.getType()); // Debug statement

        // Eğer kırılan blok Netherite ve belirlenen alandaysa
        if (block.getType() == Material.NETHERITE_BLOCK && isInsideEventArea(block.getLocation())) {
            System.out.println("Block is Netherite and inside event area"); // Debug statement
            Location blockLocation = block.getLocation();
            player.sendMessage(ChatColor.YELLOW + "You broke a netherite block! It will regenerate soon...");

            new BukkitRunnable() {
                @Override
                public void run() {
                    Block blockToRegen = blockLocation.getWorld().getBlockAt(blockLocation);
                    if (blockToRegen.getType() == Material.AIR) {
                        blockToRegen.setType(Material.NETHERITE_BLOCK);
                        player.sendMessage(ChatColor.GREEN + "The Netherite block has regenerated!");
                    } else {
                        System.out.println("Block has already been replaced or is not air.");
                    }
                }
            }.runTaskLater(plugin, 100L); // 5 saniye sonra tekrar oluştur
        }
    }

    private boolean isInsideEventArea(Location location) {
        System.out.println("Location World: " + location.getWorld().getName());

        int centerX = 129; // Alanın merkezi X
        int centerZ = -51; // Alanın merkezi Z
        int radius = 7; // 15x15 için yarıçap = 7

        String worldName = "hub";

        if (!location.getWorld().getName().equals(worldName)) {
            return false;
        }

        int x = location.getBlockX();
        int z = location.getBlockZ();

        return (x >= centerX - radius && x <= centerX + radius) &&
                (z >= centerZ - radius && z <= centerZ + radius);
    }
}
