package com.hwgilbert16.chatterbox.plugin;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import com.hwgilbert16.chatterbox.plugin.listeners.*;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class Chatterbox extends JavaPlugin {
    // Reference to main class
    public static Chatterbox plugin;

    // Socket for Socket.IO
    public Socket socket = null;

    // UUID cache for events without a getPlayer method
    public Map<String, String> uuidCache = new HashMap<>();

    // Initialize configuration object
    public ConfigurationManager config = new ConfigurationManager();

    // For testing
    public HandlerList handlerList = new HandlerList();

    public Chatterbox() {
        super();
    }

    protected Chatterbox(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
        super(loader, descriptionFile, dataFolder, file);
    }

    @Override
    public void onEnable() {
        plugin = this;
        config.initializeConfig();

        // Event initialization
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), this);
        if (config.isSendDeathMessages()) {
            getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        }
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(), this);

        // Generate auth token if it is empty
        if (this.config.getDiscordBotAuthToken().equals("EMPTY")) {
            String authToken = UUID.randomUUID().toString().replace("-", "");

            getConfig().set("discord-bot-auth-token", authToken);
            saveConfig();
        }

        // Check to make sure user setup the config file
        if (this.config.getWebhookUrl().equals("EMPTY")) {
            getLogger().severe("You need to configure your webhook URL in config.yml for Chatterbox to work - disabling");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Throw warning if discord bot is enabled and the configuration for it is empty
        if (this.config.getDiscordBotHostname().equals("EMPTY") || this.config.getDiscordBotAuthToken().equals("EMPTY")) {
            getLogger().severe("You need to configure your discord bot hostname and auth token in config.yml for Chatterbox two-way communication to work, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Socket.IO initialization
        IO.Options options = IO.Options.builder().setAuth(Collections.singletonMap("auth-token", this.config.getDiscordBotAuthToken())).build();
        try {
            socket = IO.socket(this.config.getDiscordBotHostname(), options);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on("message", args -> {
            String message = Arrays.toString(args).replace("[", "").replace("]", "");
            Bukkit.broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.RESET + message);
        });
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Chatterbox get() {
        return plugin;
    }
}
