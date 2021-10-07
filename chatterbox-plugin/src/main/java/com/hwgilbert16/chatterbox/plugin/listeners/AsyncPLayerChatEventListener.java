package com.hwgilbert16.chatterbox.plugin.listeners;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.hwgilbert16.chatterbox.plugin.DiscordWebhook;

import java.io.IOException;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Chatterbox plugin = Chatterbox.get();

        PlayerMessage message = new PlayerMessage(e);
        DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("webhook-url"));

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            webhook.setContent(message.playerMessage);
            webhook.setUsername(message.messageSender);
            webhook.setAvatarUrl(message.playerAvatarUrl);

            try {
                webhook.execute();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
