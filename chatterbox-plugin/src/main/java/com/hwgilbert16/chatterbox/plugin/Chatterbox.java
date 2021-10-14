package com.hwgilbert16.chatterbox.plugin;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import com.hwgilbert16.chatterbox.plugin.listeners.*;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

public class Chatterbox extends JavaPlugin {
    public static Chatterbox plugin;
    public Socket socket = null;

    @Override
    public void onEnable() {
        plugin = this;

        // Config file setup
        FileConfiguration config = this.getConfig();

        //TODO Allow people to ping users in the Discord, setting to enable/disable it
        // And probably a configuration class since this is getting messy

        config.addDefault("webhook-url", "EMPTY");
        config.addDefault("discord-bot-hostname", "EMPTY");
        config.addDefault("discord-bot-auth-token", "EMPTY");
        config.options().copyDefaults(true);
        saveConfig();

        // Generate auth token if it is empty
        getLogger().info(config.getString("discord-bot-auth-token"));
        if (config.getString("discord-bot-auth-token").equals("EMPTY")) {
            String authToken = UUID.randomUUID().toString().replace("-", "");
            getLogger().info(authToken);

            getConfig().set("discord-bot-auth-token", authToken);
            saveConfig();
        }

        // Check to make sure user setup the config file
        if (config.getString("webhook-url").equals("EMPTY")) {
            getLogger().severe("You need to configure your webhook URL in config.yml for Chatterbox to work - disabling");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Throw warning if discord bot is enabled and the configuration for it is empty
        if (config.getString("discord-bot-hostname").equals("EMPTY") || config.getString("discord-bot-auth-token").equals("EMPTY")) {
            getLogger().severe("You need to configure your discord bot hostname and auth token in config.yml for Chatterbox two-way communication to work, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Socket.IO initialization
        IO.Options options = IO.Options.builder().setAuth(Collections.singletonMap("auth-token", config.getString("discord-bot-auth-token"))).build();
        try {
            socket = IO.socket(config.getString("discord-bot-hostname"), options);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Event initialization
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), this);

        socket.on("message", args -> {
            String message = Arrays.toString(args).replace("[", "").replace("]", "");
            Bukkit.broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.RESET + message);
        });
    }

    @Override
    public void onDisable() {
        try {
            if (socket != null) {
                if (socket.isActive()) {
                    socket.disconnect();
                }
            }
        } catch (IllegalStateException ignored) {}
        plugin = null;
    }

    public static Chatterbox get() {
        return plugin;
    }
}
