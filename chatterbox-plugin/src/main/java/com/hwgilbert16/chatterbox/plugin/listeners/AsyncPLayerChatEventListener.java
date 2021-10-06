package com.hwgilbert16.chatterbox.plugin.listeners;

import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Chatterbox plugin = Chatterbox.get();


    }
}
