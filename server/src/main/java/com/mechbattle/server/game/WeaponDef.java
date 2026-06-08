package com.mechbattle.server.game;

public record WeaponDef(
        String id,
        String catalogKey,
        String name,
        String slot,
        WeaponCategory category,
        int cost,
        int weight,
        int damage,
        int range,
        int heat,
        int ammunition,
        int damageHeat
) {
}
