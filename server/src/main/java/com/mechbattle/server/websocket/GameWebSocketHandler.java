package com.mechbattle.server.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mechbattle.server.dto.GameStateDto;
import com.mechbattle.server.dto.ServerMessageFactory;
import com.mechbattle.server.game.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final RoomManager roomManager;
    private final ServerMessageFactory messages;

    public GameWebSocketHandler(ObjectMapper mapper, RoomManager roomManager) {
        this.mapper = mapper;
        this.roomManager = roomManager;
        this.messages = new ServerMessageFactory(mapper);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // CONNECTED отправляется после первой команды клиента — избегаем ошибок при быстром disconnect
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode root = mapper.readTree(message.getPayload());
        String type = root.path("type").asText();
        JsonNode payload = root.path("payload");

        switch (type) {
            case "CREATE_ROOM" -> handleCreateRoom(session, payload);
            case "JOIN_ROOM" -> handleJoinRoom(session, payload);
            case "REJOIN_ROOM" -> handleRejoinRoom(session, payload);
            case "SELECT_MECH" -> handleSelectMech(session, payload);
            case "EQUIP_WEAPON" -> handleEquipWeapon(session, payload);
            case "REMOVE_WEAPONS" -> handleRemoveWeapons(session, payload);
            case "READY" -> handleReady(session);
            case "MOVE" -> handleMove(session, payload);
            case "ATTACK" -> handleAttack(session, payload);
            case "SKIP_TURN" -> handleSkipTurn(session);
            default -> session.sendMessage(new TextMessage(messages.error("Неизвестная команда: " + type)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        roomManager.removeSession(session);
    }

    private void handleCreateRoom(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> existing = roomManager.getRoomBySession(session);
        if (existing.isPresent()) {
            GameRoom room = existing.get();
            String playerId = roomManager.getPlayerId(session).orElse("");
            session.sendMessage(new TextMessage(messages.roomCreated(room.getRoomId(), playerId)));
            return;
        }
        String playerName = payload.path("playerName").asText("Игрок 1");
        GameRoom room = roomManager.createRoom(session, playerName);
        String playerId = roomManager.getPlayerId(session).orElse("");
        session.sendMessage(new TextMessage(messages.roomCreated(room.getRoomId(), playerId)));
    }

    private void handleJoinRoom(WebSocketSession session, JsonNode payload) throws Exception {
        String roomId = payload.path("roomId").asText();
        String playerName = payload.path("playerName").asText("Игрок 2");
        Optional<GameRoom> roomOpt = roomManager.joinRoom(session, roomId, playerName);
        if (roomOpt.isEmpty()) {
            session.sendMessage(new TextMessage(messages.error(
                    "Комната не найдена или уже заполнена. Откройте ссылку с ?guest=1 во второй вкладке.")));
            return;
        }
        GameRoom room = roomOpt.get();
        session.sendMessage(new TextMessage(messages.roomJoined(room.getRoomId(),
                roomManager.getPlayerId(session).orElse(""))));
        broadcastState(room);
    }

    private void handleRejoinRoom(WebSocketSession session, JsonNode payload) throws Exception {
        String roomId = payload.path("roomId").asText();
        String playerId = payload.path("playerId").asText();
        Optional<GameRoom> roomOpt = roomManager.rejoinRoom(session, roomId, playerId);
        if (roomOpt.isEmpty()) {
            session.sendMessage(new TextMessage(messages.error("Не удалось переподключиться к комнате")));
            return;
        }
        GameRoom room = roomOpt.get();
        session.sendMessage(new TextMessage(messages.roomJoined(room.getRoomId(), playerId)));
        broadcastState(room);
    }

    private void handleSelectMech(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        MechType mechType = MechType.valueOf(payload.path("mech").asText().toUpperCase());
        GameActionResult result = room.getGameSession().selectMech(playerIdOpt.get(), mechType);
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleEquipWeapon(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        GameActionResult result = room.getGameSession().equipWeapon(
                playerIdOpt.get(),
                payload.path("weapon").asText(),
                payload.path("slot").asText("right")
        );
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleRemoveWeapons(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        GameActionResult result = room.getGameSession().removeWeapons(
                playerIdOpt.get(),
                payload.path("category").asText("ALL")
        );
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleReady(WebSocketSession session) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        GameActionResult result = room.getGameSession().setReady(playerIdOpt.get());
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleMove(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        Direction direction = Direction.valueOf(payload.path("direction").asText().toUpperCase());
        GameActionResult result = room.getGameSession().move(playerIdOpt.get(), direction);
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleAttack(WebSocketSession session, JsonNode payload) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        String weaponId = payload.path("weaponId").asText();
        int aim = payload.path("aim").asInt(-1);
        GameActionResult result = room.getGameSession().attack(playerIdOpt.get(), weaponId, aim);
        broadcastAction(room, result);
        broadcastState(room);
    }

    private void handleSkipTurn(WebSocketSession session) throws Exception {
        Optional<GameRoom> roomOpt = requireRoom(session);
        if (roomOpt.isEmpty()) {
            return;
        }
        GameRoom room = roomOpt.get();
        Optional<String> playerIdOpt = roomManager.getPlayerId(session);
        GameActionResult result = room.getGameSession().skipTurn(playerIdOpt.get());
        broadcastAction(room, result);
        broadcastState(room);
    }

    private Optional<GameRoom> requireRoom(WebSocketSession session) throws Exception {
        Optional<GameRoom> roomOpt = roomManager.getRoomBySession(session);
        if (roomOpt.isEmpty() || !roomOpt.get().hasGameSession()) {
            session.sendMessage(new TextMessage(messages.error("Вы не в комнате")));
            return Optional.empty();
        }
        return roomOpt;
    }

    private void broadcastAction(GameRoom room, GameActionResult result) throws Exception {
        String json = messages.actionResult(result.success(), result.message(), result.sound(), result.event());
        TextMessage message = new TextMessage(json);
        for (WebSocketSession wsSession : room.getSessions()) {
            if (wsSession.isOpen()) {
                wsSession.sendMessage(message);
            }
        }
    }

    private void broadcastState(GameRoom room) {
        if (!room.hasGameSession()) {
            return;
        }
        for (WebSocketSession wsSession : room.getSessions()) {
            if (!wsSession.isOpen()) {
                continue;
            }
            Optional<String> playerId = roomManager.getPlayerId(wsSession);
            if (playerId.isEmpty()) {
                continue;
            }
            try {
                GameStateDto state = GameStateDto.from(room.getGameSession(), playerId.get());
                wsSession.sendMessage(new TextMessage(messages.state(state)));
            } catch (Exception e) {
                try {
                    wsSession.sendMessage(new TextMessage(messages.error("Ошибка состояния игры: " + e.getMessage())));
                } catch (Exception ignored) {
                    // сессия уже закрыта
                }
            }
        }
    }
}
