package com.mechbattle.server.game;

import java.util.Arrays;
import java.util.Optional;

public enum WeaponCatalog {
    MINIGUN(WeaponCategory.GUN, "MiniGun", 1, 4, 7, 5, 10, 0, 1, true),
    MEDIUMGUN(WeaponCategory.GUN, "MediumGun", 3, 7, 5, 6, 6, 0, 2, true),
    BIGGUN(WeaponCategory.GUN, "BigGun", 6, 12, 3, 8, 4, 0, 3, true),
    SMALLLASER(WeaponCategory.LASER, "SmallLaser", 1, 3, 6, 5, 10, 5, 1, true),
    MEDIUMLASER(WeaponCategory.LASER, "MediumLaser", 3, 6, 6, 6, 8, 7, 2, true),
    BIGLASER(WeaponCategory.LASER, "BigLaser", 6, 10, 4, 8, 6, 10, 3, true),
    SMALLROCKETS(WeaponCategory.ROCKET, "SmallDistanceRockets", 5, 7, 6, 1, 4, 0, 2, false),
    LARGEROCKETS(WeaponCategory.ROCKET, "LargeDistanceRockets", 5, 5, 8, 1, 4, 0, 2, false);

    public final WeaponCategory category;
    public final String displayName;
    public final int weight;
    public final int damage;
    public final int range;
    public final int heat;
    public final int ammunition;
    public final int damageHeat;
    public final int cost;
    public final boolean handMounted;

    WeaponCatalog(WeaponCategory category, String displayName, int weight, int damage, int range,
                  int heat, int ammunition, int damageHeat, int cost, boolean handMounted) {
        this.category = category;
        this.displayName = displayName;
        this.weight = weight;
        this.damage = damage;
        this.range = range;
        this.heat = heat;
        this.ammunition = ammunition;
        this.damageHeat = damageHeat;
        this.cost = cost;
        this.handMounted = handMounted;
    }

    public static Optional<WeaponCatalog> fromKey(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(w -> w.name().equalsIgnoreCase(key.trim()))
                .findFirst();
    }

    public WeaponDef toDef(String slot) {
        return new WeaponDef(
                name().toLowerCase() + "-" + slot + "-" + System.nanoTime(),
                name(),
                displayName,
                slot,
                category,
                cost,
                weight,
                damage,
                range,
                heat,
                ammunition,
                damageHeat
        );
    }

    public String soundKey() {
        return switch (this) {
            case MINIGUN -> "minigun";
            case MEDIUMGUN -> "mediumgun";
            case BIGGUN -> "biggun";
            case SMALLLASER -> "smalllaser";
            case MEDIUMLASER -> "mediumlaser";
            case BIGLASER -> "biglaser";
            case SMALLROCKETS, LARGEROCKETS -> "rocket";
        };
    }
}
