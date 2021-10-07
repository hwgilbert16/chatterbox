package com.hwgilbert16.chatterbox.plugin.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hwgilbert16.chatterbox.plugin.Chatterbox;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PlayerMessage {
    public String playerMessage;
    public String messageSender;
    public String playerAvatarUrl;
    public String playerUUID;

    Chatterbox plugin = Chatterbox.get();

//    private JsonElement HTTPGetRequest(String url) {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
//
//        HttpResponse<String> response = null;
//
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            plugin.getLogger().severe("Error contacting Mojang API - message not sent!");
//            return null;
//        }
//
//        JsonParser parser = new JsonParser();
//        JsonElement json = parser.parse(response.body());
//
//        return json;
//    }

    public PlayerMessage(AsyncPlayerChatEvent e) {
        this.playerMessage = e.getMessage();
        this.messageSender = e.getPlayer().getDisplayName();
        this.playerUUID = e.getPlayer().getUniqueId().toString();
        this.playerAvatarUrl = "https://cravatar.eu/helmavatar/" + e.getPlayer().getDisplayName() + "/128.png";
    }
}
