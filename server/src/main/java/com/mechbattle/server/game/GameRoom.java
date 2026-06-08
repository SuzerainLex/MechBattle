package com.mechbattle.server.game;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameRoom {

    private final String roomId;
    private final String player1Id;
    private final String player1Name;
    private String player2Id;
    private String player2Name;
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private GameSession gameSession;

    public GameRoom(String roomId, String player1Id, String player1Name, WebSocketSession session) {
        this.roomId = roomId;
        this.player1Id = player1Id;
        this.player1Name = player1Name;
        sessions.add(session);
    }

    public void addPlayer2(String player2Id, String player2Name, WebSocketSession session) {
        this.player2Id = player2Id;
        this.player2Name = player2Name;
        sessions.add(session);
        this.gameSession = new GameSession(roomId, player1Id, player1Name, player2Id, player2Name);
        gameSession.setPhase(GamePhase.MECH_SELECT);
    }

    public void clearPlayer2() {
        this.player2Id = null;
        this.player2Name = null;
        this.gameSession = null;
    }

    public void startMechSelect() {
        if (gameSession != null) {
            gameSession.setPhase(GamePhase.MECH_SELECT);
        }
    }

    public boolean isFull() {
        return player2Id != null;
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void addSession(WebSocketSession session) {
        if (!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    public boolean belongsToPlayer(String playerId) {
        return player1Id.equals(playerId) || (player2Id != null && player2Id.equals(playerId));
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public List<WebSocketSession> getSessions() {
        return new ArrayList<>(sessions);
    }

    public String getRoomId() {
        return roomId;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public boolean hasGameSession() {
        return gameSession != null;
    }
}
