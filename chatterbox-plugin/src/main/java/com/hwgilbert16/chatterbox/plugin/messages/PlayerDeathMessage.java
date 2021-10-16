package com.hwgilbert16.chatterbox.plugin.messages;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathMessage {
    public String deathMessage;
    public String player;
    public String playerAvatarUrl;

    Chatterbox plugin = Chatterbox.get();

    public PlayerDeathMessage(PlayerDeathEvent e) {
        this.deathMessage = e.getDeathMessage();
        this.player = e.getDeathMessage().substring(0, e.getDeathMessage().indexOf(" "));
        this.playerAvatarUrl = "https://cravatar.eu/helmavatar/" + plugin.uuidCache.get(e.getDeathMessage().substring(0, e.getDeathMessage().indexOf(" "))) + "/32.png";
    }
}
