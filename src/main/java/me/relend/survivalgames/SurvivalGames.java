package me.relend.survivalgames;

import me.relend.survivalgames.listeners.*;
import me.relend.survivalgames.manager.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalGames extends JavaPlugin {

    private GameManager gameManager;

    @EventHandler
    public void onEnable() {
        getLogger().info("=======================");
        registerCommands();
        getLogger().info("Loaded commands!");
        registerListeners();
        getLogger().info("Loaded listeners!");
        loadManagers();
        getLogger().info("Loaded managers!");
        getLogger().info("=======================");
    }

    private void registerCommands() {

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlocksListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
    }

    private void loadManagers() {
        gameManager = new GameManager(this);
    }

    public GameManager getManager() {
        return gameManager;
    }

}
