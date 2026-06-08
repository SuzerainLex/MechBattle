package com.mechbattle.server.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mechbattle.server.game.CombatEvent;

public class ServerMessageFactory {

    private final ObjectMapper mapper;

    public ServerMessageFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String connected(String playerId) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CONNECTED");
        node.put("playerId", playerId);
        return mapper.writeValueAsString(node);
    }

    public String roomCreated(String roomId, String playerId) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "ROOM_CREATED");
        node.put("roomId", roomId);
        node.put("playerId", playerId);
        return mapper.writeValueAsString(node);
    }

    public String roomJoined(String roomId, String playerId) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "ROOM_JOINED");
        node.put("roomId", roomId);
        node.put("playerId", playerId);
        return mapper.writeValueAsString(node);
    }

    public String state(GameStateDto state) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "STATE");
        node.set("state", mapper.valueToTree(state));
        return mapper.writeValueAsString(node);
    }

    public String actionResult(boolean success, String message, String sound, CombatEvent event) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "ACTION_RESULT");
        node.put("success", success);
        node.put("message", message);
        if (sound != null) {
            node.put("sound", sound);
        }
        if (event != null) {
            node.set("event", mapper.valueToTree(event));
        }
        return mapper.writeValueAsString(node);
    }

    public String error(String message) throws Exception {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "ERROR");
        node.put("message", message);
        return mapper.writeValueAsString(node);
    }
}
