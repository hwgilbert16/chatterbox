package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.PlayerQuitMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Chatterbox plugin = Chatterbox.get();

        if (plugin.config.isSendQuitMessages()) {
            PlayerQuitMessage playerQuitMessage = new PlayerQuitMessage(e);

            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xff0000)
                    .setThumbnailUrl(playerQuitMessage.playerAvatarUrl)
                    .setDescription(playerQuitMessage.quitMessage)
                    .build();

            WebhookClient client = WebhookClient.withUrl(plugin.config.getWebhookUrl());
            client.send(embed);
        }

        // Remove player from cache upon leaving
        // Needed for if user changes username between server restarts
        plugin.uuidCache.remove(e.getPlayer().getDisplayName());
    }
}
