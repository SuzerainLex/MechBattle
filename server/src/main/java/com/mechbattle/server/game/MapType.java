package com.mechbattle.server.game;

public enum MapType {
    STANDARD("Стандарт", 6, 5, 3),
    CITY("Город", 10, 4, 2),
    DESERT("Пустыня", 2, 3, 8),
    FACTORY("Завод", 8, 4, 4);

    public final String displayName;
    public final int buildingCount;
    public final int forestCount;
    public final int waterCount;

    MapType(String displayName, int buildingCount, int forestCount, int waterCount) {
        this.displayName = displayName;
        this.buildingCount = buildingCount;
        this.forestCount = forestCount;
        this.waterCount = waterCount;
    }
}
