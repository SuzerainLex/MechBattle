package com.mechbattle.server.game;

public record GameActionResult(boolean success, String message, String sound, CombatEvent event) {

    public static GameActionResult ok(String message) {
        return new GameActionResult(true, message, null, null);
    }

    public static GameActionResult ok(String message, String sound) {
        return new GameActionResult(true, message, sound, null);
    }

    public static GameActionResult ok(String message, String sound, CombatEvent event) {
        return new GameActionResult(true, message, sound, event);
    }

    public static GameActionResult error(String message) {
        return new GameActionResult(false, message, null, null);
    }
}
