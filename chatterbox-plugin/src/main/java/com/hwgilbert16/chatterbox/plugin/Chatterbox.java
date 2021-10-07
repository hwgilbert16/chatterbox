package com.hwgilbert16.chatterbox.plugin;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.hwgilbert16.chatterbox.plugin.listeners.*;

import java.net.URISyntaxException;

public class Chatterbox extends JavaPlugin {
    public static Chatterbox plugin;
    public Socket socket;

    @Override
    public void onEnable() {
        plugin = this;

        // Config file setup
        FileConfiguration config = this.getConfig();

        config.addDefault("webhook-url", "EMPTY");
        config.addDefault("discord-bot-enabled", true);
        config.addDefault("discord-bot-hostname", "EMPTY");
        config.addDefault("discord-bot-token", "EMPTY");
        config.options().copyDefaults(true);
        saveConfig();

        // Check to make sure user setup the config file
        if (config.getString("webhook-url").equals("EMPTY")) {
            getLogger().severe("You need to configure your webhook URL in config.yml for Chatterbox to work - disabling");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (config.getBoolean("discord-bot-enabled")) {
            if (config.getString("discord-bot-hostname").equals("EMPTY") || config.getString("discord-bot-token").equals("EMPTY")) {
                getLogger().severe("You need to configure your discord bot hostname and token in config.yml for Chatterbox two-way communication to work - disabling. If you don't want two-way communication, disable discord-bot-enabled in plugin.yml");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        // Event initialization
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), this);

        // Socket.IO initialization
        IO.Options options = IO.Options.builder().build();
        try {
            socket = IO.socket(config.getString("discord-bot-hostname"), options);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
        socket.disconnect();
    }

    public static Chatterbox get() {
        return plugin;
    }
}
