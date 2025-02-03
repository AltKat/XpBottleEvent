package com.example.project.testPlugin;

import com.example.project.testPlugin.commands.EventCommand;
import com.example.project.testPlugin.commands.EventTabCompleter;
import com.example.project.testPlugin.listeners.EventBlockListener;
import com.example.project.testPlugin.listeners.XPBottleBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {
    public static final String lunaPREFIX = ChatColor.translateAlternateColorCodes('&', "&cLunaya | &r");
    public static final String prefix = ChatColor.RED + "Lunaya" + ChatColor.GRAY + " >> " + ChatColor.WHITE;

        private static TestPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("\u001B[31mTest Plugin has been enabled!.\u001B[0m");

        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventBlockListener(this), this);

        getCommand("event").setExecutor(new EventCommand());
        getCommand("event").setTabCompleter(new EventTabCompleter());
    }

    public static TestPlugin getInstance() {
        return instance;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        String playerName = player.getName();
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', lunaPREFIX + "&a[+] &r" + playerName));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        String playerName = player.getName();
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', lunaPREFIX + "&a[-] &r" + playerName));
    }
}
