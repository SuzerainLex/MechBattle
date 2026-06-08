package com.mechbattle.server.game;

public record CombatEvent(
        String type,
        int fromX,
        int fromY,
        int toX,
        int toY,
        String weaponCategory,
        boolean hit,
        boolean critical
) {
    public static CombatEvent shot(int fromX, int fromY, int toX, int toY, String category, boolean hit, boolean critical) {
        return new CombatEvent("SHOT", fromX, fromY, toX, toY, category, hit, critical);
    }
}
