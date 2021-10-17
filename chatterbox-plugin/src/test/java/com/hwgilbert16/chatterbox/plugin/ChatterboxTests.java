package com.hwgilbert16.chatterbox.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hwgilbert16.chatterbox.plugin.listeners.AsyncPlayerChatEventListener;
import org.bukkit.event.HandlerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatterboxTests {
    private ServerMock server;
    private Chatterbox plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Chatterbox.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Four event listeners are registered")
    public void asyncPlayerChatEventListenerRegistered() {
        assertEquals(4, HandlerList.getRegisteredListeners(plugin).size());
    }
}
