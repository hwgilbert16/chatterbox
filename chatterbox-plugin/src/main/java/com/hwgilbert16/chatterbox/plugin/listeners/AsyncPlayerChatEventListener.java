package com.hwgilbert16.chatterbox.plugin.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import com.hwgilbert16.chatterbox.plugin.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Chatterbox plugin = Chatterbox.get();

        PlayerMessage message = new PlayerMessage(e);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            WebhookClient client = WebhookClient.withUrl(plugin.getConfig().getString("webhook-url"));

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(message.messageSender);
            builder.setAvatarUrl(message.playerAvatarUrl);

            String messagePattern = "@[a-zA-Z0-9\\d!@#$%&*]+";
            Pattern p = Pattern.compile(messagePattern, Pattern.UNICODE_CHARACTER_CLASS);

            Matcher m = p.matcher(message.playerMessage);
            Matcher secondM = p.matcher(message.playerMessage);
            long mentions = secondM.results().count();
            plugin.getLogger().info(String.valueOf(mentions));

            // TODO: also run this if the user has disabled mentioning in config file
            if (mentions < 1) {
                builder.setContent(message.playerMessage);
                client.send(builder.build());

                return;
            }

            ArrayList<JSONObject> users = new ArrayList<JSONObject>();

            plugin.socket.on("returnId", user -> {
                try {
                    plugin.getLogger().info(user[0].toString());
                    JSONObject userObject = new JSONObject(user[0].toString());
                    users.add(userObject);

                    if (users.size() == mentions) {
                        for (JSONObject mentionedUser : users) {
                            if (mentionedUser.isNull("id")) {
                                plugin.getLogger().info("Skipped");
                                continue;
                            }

                            message.playerMessage = message.playerMessage.replaceAll("@" + mentionedUser.getString("username"), String.format("<@%s>", mentionedUser.getString("id")));
                        }
                        builder.setContent(message.playerMessage);
                        client.send(builder.build());
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            });

            while (m.find()) {
                plugin.socket.emit("getId", message.playerMessage.substring(m.start() + 1, m.end()));
                plugin.getLogger().info(m.group());
            }

//            String messagePattern = "(<@.[0-9]+>)";
//            Pattern p = Pattern.compile(messagePattern);
//            //Matcher m = p.matcher(message.playerMessage);
//            Matcher m = null;
//
//            while ((m = p.matcher(message.playerMessage)) != null) {
//                String userID = message.playerMessage.substring(m.start(), m.end());
//
//                plugin.getLogger().info(userID);
//
//                //plugin.socket.emit("getUsername")
//            }

//            WebhookMessageBuilder builder = new WebhookMessageBuilder();
//            builder.setUsername(message.messageSender);
//            builder.setAvatarUrl(message.playerAvatarUrl);
//            builder.setContent(message.playerMessage);
//
//            client.send(builder.build());
        });
    }
}
