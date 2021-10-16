package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.PlayerDeathMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Chatterbox plugin = Chatterbox.get();

        PlayerDeathMessage playerDeathMessage = new PlayerDeathMessage(e);

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xff0000)
                .setThumbnailUrl(playerDeathMessage.playerAvatarUrl)
                .setDescription(playerDeathMessage.deathMessage)
                .build();

        WebhookClient client = WebhookClient.withUrl(plugin.config.getWebhookUrl());
        client.send(embed);
    }
}
