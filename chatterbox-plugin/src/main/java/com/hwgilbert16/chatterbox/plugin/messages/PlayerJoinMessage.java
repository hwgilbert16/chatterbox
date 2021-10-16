package com.hwgilbert16.chatterbox.plugin.messages;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinMessage {
    public String joinMessage;
    public String player;
    public String playerAvatarUrl;

    Chatterbox plugin = Chatterbox.get();

    public PlayerJoinMessage(PlayerJoinEvent e) {
        this.joinMessage = e.getJoinMessage().substring(2);
        this.player = e.getPlayer().getDisplayName();
        this.playerAvatarUrl = "https://cravatar.eu/helmavatar/" + plugin.uuidCache.get(player) + "/32.png";
    }
}
