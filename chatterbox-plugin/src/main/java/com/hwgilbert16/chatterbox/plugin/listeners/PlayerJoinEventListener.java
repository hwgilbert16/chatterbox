package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.PlayerJoinMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Chatterbox plugin = Chatterbox.get();

        // Cache UUID for use in events that do not have a getPlayer method
        plugin.uuidCache.put(e.getPlayer().getDisplayName(), e.getPlayer().getUniqueId().toString());

        PlayerJoinMessage playerJoinMessage = new PlayerJoinMessage(e);

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x00BF00)
                .setThumbnailUrl(playerJoinMessage.playerAvatarUrl)
                .setDescription(playerJoinMessage.joinMessage)
                .build();

        plugin.getLogger().info(playerJoinMessage.joinMessage);

        WebhookClient client = WebhookClient.withUrl(plugin.getConfig().getString("webhook-url"));
        client.send(embed);

    }
}
