package com.hwgilbert16.chatterbox.plugin.messages;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerMessage {
    public String playerMessage;
    public String messageSender;
    public String playerAvatarUrl;
    public String playerUUID;

    Chatterbox plugin = Chatterbox.get();

    public PlayerMessage(AsyncPlayerChatEvent e) {
        this.playerMessage = e.getMessage();
        this.messageSender = e.getPlayer().getDisplayName();
        this.playerUUID = e.getPlayer().getUniqueId().toString();

        // Avatar URL is gathered from cravatar.eu
        this.playerAvatarUrl = "https://cravatar.eu/helmavatar/" + plugin.uuidCache.get(e.getPlayer().getDisplayName()) + "/128.png";
    }
}
