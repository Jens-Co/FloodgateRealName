package com.alysaa.geysernickname;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.connector.GeyserConnector;
import org.geysermc.floodgate.FloodgateAPI;
import org.geysermc.floodgate.FloodgatePlayer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class NickNamer extends JavaPlugin implements Listener {
    private FileConfiguration config;
    Plugin plugin;

    public void onEnable() {
        boolean hasFloodgate = Bukkit.getPluginManager().getPlugin("floodgate-bukkit") != null;
        boolean hasGeyser = Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null;
        if (!hasFloodgate && !hasGeyser) {
            getLogger().warning("There is no Geyser or Floodgate plugin detected! Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        createFiles();
        getLogger().info("Has been enabled");
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
        }
    public void onDisable() {
        getLogger().info("Has been disabled");
    }
    /**
     * Determines if a player is from Bedrock
     * @param uuid the UUID to determine
     * @return true if the player is from Bedrock
     */
    public boolean isBedrockPlayer(UUID uuid) {
        if (!config.getBoolean("Enable-NickNames")){
            return GeyserConnector.getInstance().getPlayerByUuid(uuid) != null;
        } else {
            return FloodgateAPI.isBedrockPlayer(uuid);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean isBedrockPlayer = this.isBedrockPlayer(event.getPlayer().getUniqueId());
        if (isBedrockPlayer) {
            FloodgatePlayer player = FloodgateAPI.getPlayer(event.getPlayer().getUniqueId());
            event.getPlayer().setDisplayName(player.getUsername());
            event.getPlayer().setPlayerListName(player.getUsername());
            getLogger().info("Geyser/Floodgate Username has been changed into -> "+player.getUsername());
        }
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
