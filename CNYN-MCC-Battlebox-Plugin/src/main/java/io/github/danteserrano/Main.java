package io.github.danteserrano;

import io.github.danteserrano.commands.CommandArena;
import io.github.danteserrano.commands.CommandKit;
import io.github.danteserrano.listeners.DeathListener;
import io.github.danteserrano.listeners.JoinListener;
import io.github.danteserrano.listeners.LeaveListener;
import io.github.danteserrano.listeners.PreventionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Create arenas.
        Manager.setup();

        registerCommands();
        registerListeners();

        // Create configuration file.

        instance.saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("arena").setExecutor(new CommandArena());
        getCommand("kit").setExecutor(new CommandKit());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new PreventionListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new LeaveListener(), this);
    }
}