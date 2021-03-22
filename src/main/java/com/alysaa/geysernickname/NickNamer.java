package com.alysaa.geysernickname;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.connector.GeyserConnector;
import org.geysermc.floodgate.FloodgateAPI;
import org.geysermc.floodgate.FloodgatePlayer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class NickNamer extends JavaPlugin implements Listener {
    private FileConfiguration config;

    public void onEnable() {
        createFiles();
        getLogger().info("Has been enabled");

    }
    public void onDisable() {

    }
    /**
     * Determines if a player is from Bedrock
     * @param uuid the UUID to determine
     * @return true if the player is from Bedrock
     */
    public boolean isBedrockPlayer(UUID uuid) {
        if (getConfig().getBoolean("Enable-Nicknames")) {
            return GeyserConnector.getInstance().getPlayerByUuid(uuid) != null;
        } else {
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
            return FloodgateAPI.isBedrockPlayer(uuid);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FloodgatePlayer player = FloodgateAPI.getPlayer(event.getPlayer().getUniqueId());
        event.getPlayer().setDisplayName(player.getUsername());
        event.getPlayer().setPlayerListName(player.getUsername());
    }
    private void createFiles() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
