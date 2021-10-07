package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Chatterbox plugin = Chatterbox.get();

        PlayerMessage message = new PlayerMessage(e);

//        // Construct webhook connection
//        WebhookClientBuilder builder = new WebhookClientBuilder(Objects.requireNonNull(plugin.getConfig().getString("webhook-url")));
//        builder.setThreadFactory((job) -> {
//            Thread thread = new Thread(job);
//            thread.setName("webhook");
//            thread.setDaemon(true);
//            return thread;
//        });
//        builder.setWait(true);
//
//        WebhookClient client = builder.build();
    }
}
