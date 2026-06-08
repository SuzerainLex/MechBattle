package com.mechbattle.server.game;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomManager {

    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToRoom = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToPlayer = new ConcurrentHashMap<>();

    public GameRoom createRoom(WebSocketSession session, String playerName) {
        String roomId = generateRoomId();
        String playerId = UUID.randomUUID().toString();
        GameRoom room = new GameRoom(roomId, playerId, playerName, session);
        rooms.put(roomId, room);
        sessionToRoom.put(session.getId(), roomId);
        sessionToPlayer.put(session.getId(), playerId);
        return room;
    }

    public Optional<GameRoom> joinRoom(WebSocketSession session, String roomId, String playerName) {
        String normalizedRoomId = roomId.toUpperCase();
        GameRoom room = rooms.get(normalizedRoomId);
        if (room == null) {
            return Optional.empty();
        }

        String existingPlayerId = sessionToPlayer.get(session.getId());
        if (existingPlayerId != null && existingPlayerId.equals(room.getPlayer1Id())) {
            return Optional.empty();
        }

        if (room.isFull() && !isPlayerOnline(room, room.getPlayer2Id())) {
            room.clearPlayer2();
        }

        if (room.isFull()) {
            return Optional.empty();
        }

        String playerId = UUID.randomUUID().toString();
        room.addPlayer2(playerId, playerName, session);
        sessionToRoom.put(session.getId(), normalizedRoomId);
        sessionToPlayer.put(session.getId(), playerId);
        room.startMechSelect();
        return Optional.of(room);
    }

    public Optional<GameRoom> rejoinRoom(WebSocketSession session, String roomId, String playerId) {
        String normalizedRoomId = roomId.toUpperCase();
        GameRoom room = rooms.get(normalizedRoomId);
        if (room == null || !room.belongsToPlayer(playerId)) {
            return Optional.empty();
        }
        room.addSession(session);
        sessionToRoom.put(session.getId(), normalizedRoomId);
        sessionToPlayer.put(session.getId(), playerId);
        return Optional.of(room);
    }

    public Optional<GameRoom> getRoomBySession(WebSocketSession session) {
        String roomId = sessionToRoom.get(session.getId());
        if (roomId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(rooms.get(roomId));
    }

    public Optional<String> getPlayerId(WebSocketSession session) {
        return Optional.ofNullable(sessionToPlayer.get(session.getId()));
    }

    public void removeSession(WebSocketSession session) {
        sessionToPlayer.remove(session.getId());
        String roomId = sessionToRoom.remove(session.getId());
        if (roomId != null) {
            GameRoom room = rooms.get(roomId);
            if (room != null) {
                room.removeSession(session);
            }
        }
    }

    private boolean isPlayerOnline(GameRoom room, String playerId) {
        if (playerId == null) {
            return false;
        }
        for (WebSocketSession wsSession : room.getSessions()) {
            if (playerId.equals(sessionToPlayer.get(wsSession.getId()))) {
                return true;
            }
        }
        return false;
    }

    private String generateRoomId() {
        String id;
        do {
            id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (rooms.containsKey(id));
        return id;
    }
}
