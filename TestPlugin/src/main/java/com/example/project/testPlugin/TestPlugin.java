package com.example.project.testPlugin;

import com.example.project.testPlugin.commands.EventCommand;
import com.example.project.testPlugin.listeners.XPBottleBreakListener;
import com.example.project.testPlugin.tabcompleter.EventTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {
    private static final String Prefix = ChatColor.RED + "Lunaya | " + ChatColor.WHITE;

    private static TestPlugin instance;

    @Override
    public void onEnable() { // There are called when the plugin starts
        // Plugin startup logic


        getLogger().info("\u001B[31mTest Plugin has been enabled!.\u001B[0m"); // console message

        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(), this); // XPBottleBreakListener
        getServer().getPluginManager().registerEvents(this, this); // onPlayerJoinEvent, onPlayerQuitEvent

        getCommand("event").setExecutor(new EventCommand());

        // TabCompleter save
        getCommand("event").setTabCompleter(new EventTabCompleter());

        instance = this; // ★★★
    }

    public static TestPlugin getInstance() {  // ★★★
        return instance;
    }



    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        String playerName = player.getName();
        Bukkit.broadcastMessage(Prefix + ChatColor.GREEN + "[+] " + ChatColor.RESET + playerName ); // in game chat message with player name
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        String playerName = player.getName();
        getServer().broadcastMessage(Prefix + ChatColor.RED + "[-] " + ChatColor.RESET + playerName);// in game chat message with player name
    }



}
