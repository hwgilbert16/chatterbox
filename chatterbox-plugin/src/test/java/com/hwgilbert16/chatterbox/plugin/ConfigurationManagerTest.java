package com.hwgilbert16.chatterbox.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hwgilbert16.chatterbox.plugin.listeners.AsyncPlayerChatEventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigurationManagerTest {
    private ServerMock server;
    private Chatterbox plugin;

    @Before
    public void setUp() {
        // Start the mock server
        server = MockBukkit.mock();
        // Load your plugin
        plugin = MockBukkit.load(Chatterbox.class);
    }

    @After
    public void tearDown() {
        // Stop the mock server
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("webhook-url should default to EMPTY")
    public void webhookUrlDefault() {
        assertEquals("EMPTY", plugin.config.getWebhookUrl());
    }

    @Test
    @DisplayName("discord-bot-hostname should default to EMPTY")
    public void discordBotHostnameDefault() {
        assertEquals("EMPTY", plugin.config.getDiscordBotHostname());
    }

    @Test
    @DisplayName("discord-bot-auth-token should default to EMPTY")
    public void discordBotAuthTokenDefault() {
        assertEquals("EMPTY", plugin.config.getDiscordBotAuthToken());
    }

    @Test
    @DisplayName("send-join-messages should default to true")
    public void sendJoinMessagesDefault() {
        assertTrue(plugin.config.isSendJoinMessages());
    }

    @Test
    @DisplayName("send-quit-messages should default to true")
    public void sendQuitMessagesDefault() {
        assertTrue(plugin.config.isSendQuitMessages());
    }

    @Test
    @DisplayName("send-death-messages should default to true")
    public void sendDeathMessagesDefault() {
        assertTrue(plugin.config.isSendDeathMessages());
    }

    @Test
    @DisplayName("enable-discord-mentioning should default to true")
    public void enableDiscordMentioningDefault() {
        assertTrue(plugin.config.isSendDiscordMentions());
    }

//    @Test
//    @DisplayName("AsyncPlayerChatEventListener should be registered as an event listener")
//    public void asyncPlayerChatEventListenerRegistered() {
//        System.out.println("ArrayList:" + plugin.handlerList.getRegisteredListeners(plugin));
//        assertEquals(true, plugin.handlerList.getRegisteredListeners(plugin).contains("Test"));
//    }
}
