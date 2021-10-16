package com.hwgilbert16.chatterbox.plugin.messages;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitMessage {
    public String quitMessage;
    public String player;
    public String playerAvatarUrl;

    Chatterbox plugin = Chatterbox.get();

    public PlayerQuitMessage(PlayerQuitEvent e) {
        this.quitMessage = e.getQuitMessage().substring(2);
        this.player = e.getPlayer().getDisplayName();
        this.playerAvatarUrl = "https://cravatar.eu/helmavatar/" + plugin.uuidCache.get(player) + "/32.png";
    }
}
