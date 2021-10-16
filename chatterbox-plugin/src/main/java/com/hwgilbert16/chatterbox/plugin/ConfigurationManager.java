package com.hwgilbert16.chatterbox.plugin;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationManager {
    private String webhookUrl;
    private String discordBotHostname;
    private String discordBotAuthToken;
    private boolean sendJoinMessages;
    private boolean sendQuitMessages;
    private boolean sendDeathMessages;
    private boolean enableDiscordMentioning;

    public void initializeConfig() {
        Chatterbox plugin = Chatterbox.get();
        FileConfiguration config = plugin.getConfig();

        config.addDefault("webhook-url", "EMPTY");
        config.addDefault("discord-bot-hostname", "EMPTY");
        config.addDefault("discord-bot-auth-token", "EMPTY");
        config.addDefault("send-join-messages", true);
        config.addDefault("send-quit-messages", true);
        config.addDefault("send-death-messages", true);
        config.addDefault("enable-discord-mentioning", true);
        config.options().copyDefaults(true);
        plugin.saveConfig();

        this.webhookUrl = config.getString("webhook-url");
        this.discordBotHostname = config.getString("discord-bot-hostname");
        this.discordBotAuthToken = config.getString("discord-bot-auth-token");
        this.sendJoinMessages = config.getBoolean("send-join-messages");
        this.sendQuitMessages = config.getBoolean("send-quit-messages");
        this.sendDeathMessages = config.getBoolean("send-death-messages");
        this.enableDiscordMentioning = config.getBoolean("enable-discord-mentioning");
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getDiscordBotHostname() {
        return discordBotHostname;
    }

    public String getDiscordBotAuthToken() {
        return discordBotAuthToken;
    }

    public boolean isSendJoinMessages() {
        return sendJoinMessages;
    }

    public boolean isSendQuitMessages() {
        return sendQuitMessages;
    }

    public boolean isSendDeathMessages() {
        return sendDeathMessages;
    }

    public boolean isEnableDiscordMentioning() {
        return enableDiscordMentioning;
    }
}
