package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Chatterbox plugin = Chatterbox.get();

        PlayerMessage message = new PlayerMessage(e);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            WebhookClient client = WebhookClient.withUrl(plugin.getConfig().getString("webhook-url"));

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(message.messageSender);
            builder.setAvatarUrl(message.playerAvatarUrl);
            builder.setContent(message.playerMessage);

            client.send(builder.build());
        });
    }
}
