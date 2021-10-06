package com.hwgilbert16.chatterbox.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.hwgilbert16.chatterbox.plugin.listeners.*;

public class Chatterbox extends JavaPlugin {
    public static Chatterbox plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Configuration setup
        FileConfiguration config = this.getConfig();

        config.addDefault("discord-bot-IP", "blank");
        config.addDefault("discord-bot-token", "blank");
        config.options().copyDefaults(true);
        saveConfig();

        if (config.getString("discord-bot-IP").equals("blank") || config.getString("discord-bot-token").equals("blank")) {
            getLogger().severe("You need to configure your IP and token in config.yml for Chatterbox to work - disabling");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Event initialization
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), this);
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Chatterbox get() {
        return plugin;
    }
}
