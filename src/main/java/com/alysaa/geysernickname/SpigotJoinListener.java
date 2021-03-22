package com.alysaa.geysernickname;

import com.alysaa.geysernickname.common.JavaPlayerChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.FloodgateAPI;
import org.geysermc.floodgate.FloodgatePlayer;

public class SpigotJoinListener implements Listener {
    public JavaPlayerChecker playerChecker;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean isBedrockPlayer = playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());
        if (isBedrockPlayer) {
            FloodgatePlayer player = FloodgateAPI.getPlayer(event.getPlayer().getUniqueId());
            event.getPlayer().setDisplayName(player.getUsername());
        }
    }
}