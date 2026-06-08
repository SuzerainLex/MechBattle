package com.mechbattle.server.game;

public enum TerrainType {
    OPEN(false, false, 1),
    BUILDING(true, true, 1),
    FOREST(false, true, 1),
    WATER(false, false, 2),
    RUBBLE(true, true, 1);

    public final boolean impassable;
    public final boolean blocksLineOfSight;
    public final int moveCost;

    TerrainType(boolean impassable, boolean blocksLineOfSight, int moveCost) {
        this.impassable = impassable;
        this.blocksLineOfSight = blocksLineOfSight;
        this.moveCost = moveCost;
    }
}
