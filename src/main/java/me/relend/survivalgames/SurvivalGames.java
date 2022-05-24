package me.relend.survivalgames;

import me.relend.survivalgames.commands.ArenaCommand;
import me.relend.survivalgames.commands.StartCommand;
import me.relend.survivalgames.commands.StartCompleter;
import me.relend.survivalgames.listeners.*;
import me.relend.survivalgames.manager.GameManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SurvivalGames extends JavaPlugin {

    private GameManager gameManager;
    private FileConfiguration messagesConfig;

    @EventHandler
    public void onEnable() {
        getLogger().info("=======================");
        registerCommands();
        getLogger().info("Loaded commands!");
        registerListeners();
        getLogger().info("Loaded listeners!");
        loadManagers();
        getLogger().info("Loaded managers!");
        loadConfigs();
        getLogger().info("Loaded configs!");
        getLogger().info("=======================");
    }

    private void registerCommands() {
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("start").setTabCompleter(new StartCompleter());
        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlocksListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    private void loadManagers() {
        gameManager = new GameManager(this);
    }

    private void loadConfigs() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        createMessagesConfig();
    }

    private void createMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public GameManager getManager() {
        return gameManager;
    }

    public FileConfiguration getMessagesConfig() {
        return this.messagesConfig;
    }

}
