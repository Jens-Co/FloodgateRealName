package com.alysaa.floodname.listener;

import com.alysaa.floodname.FloodNamer;
import com.alysaa.floodname.common.CheckJavaOrFloodPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.FloodgateAPI;
import org.geysermc.floodgate.FloodgatePlayer;

public class NamerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean isBedrockPlayer = CheckJavaOrFloodPlayer.isBedrockPlayer(event.getPlayer().getUniqueId());
        if (isBedrockPlayer) {
            FloodgatePlayer player = FloodgateAPI.getPlayer(event.getPlayer().getUniqueId());
            event.getPlayer().setDisplayName(player.getUsername());
            event.getPlayer().setPlayerListName(player.getUsername());
        }
    }
}
