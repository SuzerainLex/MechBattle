package com.mechbattle.server.game;

public enum MechType {
    THOR("Thor", 6, 4, 35, 25, 20, 2, 2, 2, 4, 2, 2),
    MADCAT("Mad Cat", 4, 5, 30, 20, 20, 2, 2, 2, 5, 2, 2),
    LOCUST("Locust", 2, 7, 30, 18, 15, 2, 1, 2, 7, 2, 2),
    ATLAS("Atlas", 8, 3, 50, 30, 30, 4, 2, 4, 3, 4, 4);

    public final String displayName;
    public final int meleeMight;
    public final int initiative;
    public final int armor;
    public final int radiator;
    public final int maxWeight;
    public final int gunSockets;
    public final int rocketSockets;
    public final int laserSockets;
    public final int maxMoves;
    public final int maxLeftHandSlots;
    public final int maxRightHandSlots;

    MechType(String displayName, int meleeMight, int initiative, int armor, int radiator,
             int maxWeight, int gunSockets, int rocketSockets, int laserSockets,
             int maxMoves, int maxLeftHandSlots, int maxRightHandSlots) {
        this.displayName = displayName;
        this.meleeMight = meleeMight;
        this.initiative = initiative;
        this.armor = armor;
        this.radiator = radiator;
        this.maxWeight = maxWeight;
        this.gunSockets = gunSockets;
        this.rocketSockets = rocketSockets;
        this.laserSockets = laserSockets;
        this.maxMoves = maxMoves;
        this.maxLeftHandSlots = maxLeftHandSlots;
        this.maxRightHandSlots = maxRightHandSlots;
    }
}
