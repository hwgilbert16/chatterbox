package com.hwgilbert16.chatterbox.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.hwgilbert16.chatterbox.plugin.listeners.*;

public class Chatterbox extends JavaPlugin {
    public static Chatterbox plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Config file setup
        FileConfiguration config = this.getConfig();

        config.addDefault("webhook-url", "blank");
        config.options().copyDefaults(true);
        saveConfig();

        // Check to make sure user setup the config file
        if (config.getString("webhook-url").equals("blank")) {
            getLogger().severe("You need to configure your webhook URL in config.yml for Chatterbox to work - disabling");
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
