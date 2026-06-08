package com.mechbattle.server.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BattleField {

    private MapType mapType = MapType.STANDARD;
    private final List<TerrainTile> terrain = new ArrayList<>();

    public void generate(MechState player1, MechState player2, Random random) {
        mapType = MapType.values()[random.nextInt(MapType.values().length)];
        terrain.clear();
        Set<String> used = reservedCells(player1, player2);

        placeTerrain(used, random, TerrainType.BUILDING, mapType.buildingCount);
        placeTerrain(used, random, TerrainType.FOREST, mapType.forestCount);
        placeTerrain(used, random, TerrainType.WATER, mapType.waterCount);

        if (mapType == MapType.FACTORY) {
            placeTerrain(used, random, TerrainType.RUBBLE, 4);
        }
    }

    private void placeTerrain(Set<String> used, Random random, TerrainType type, int count) {
        int attempts = 0;
        int placed = 0;
        while (placed < count && attempts < 300) {
            attempts++;
            int x = random.nextInt(MechState.FIELD_SIZE + 1);
            int y = random.nextInt(MechState.FIELD_SIZE + 1);
            String key = x + "," + y;
            if (used.contains(key)) {
                continue;
            }
            used.add(key);
            terrain.add(new TerrainTile(x, y, type));
            placed++;
        }
    }

    private Set<String> reservedCells(MechState player1, MechState player2) {
        Set<String> blocked = new HashSet<>();
        reserveAround(blocked, player1.getX(), player1.getY());
        reserveAround(blocked, player2.getX(), player2.getY());
        return blocked;
    }

    private void reserveAround(Set<String> blocked, int cx, int cy) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = cx + dx;
                int y = cy + dy;
                if (x >= 0 && x <= MechState.FIELD_SIZE && y >= 0 && y <= MechState.FIELD_SIZE) {
                    blocked.add(x + "," + y);
                }
            }
        }
    }

    public TerrainType getTerrainAt(int x, int y) {
        return terrain.stream()
                .filter(t -> t.x() == x && t.y() == y)
                .map(TerrainTile::type)
                .findFirst()
                .orElse(TerrainType.OPEN);
    }

    public boolean isBlocked(int x, int y) {
        TerrainType type = getTerrainAt(x, y);
        return type.impassable;
    }

    public int getMoveCost(int x, int y) {
        return getTerrainAt(x, y).moveCost;
    }

    public boolean hasLineOfSight(int x1, int y1, int x2, int y2) {
        for (int[] cell : bresenhamLine(x1, y1, x2, y2)) {
            int x = cell[0];
            int y = cell[1];
            if (x == x1 && y == y1) continue;
            if (x == x2 && y == y2) continue;
            if (getTerrainAt(x, y).blocksLineOfSight) {
                return false;
            }
        }
        return true;
    }

    public List<int[]> getLineCells(int x1, int y1, int x2, int y2) {
        return bresenhamLine(x1, y1, x2, y2);
    }

    private List<int[]> bresenhamLine(int x0, int y0, int x1, int y1) {
        List<int[]> points = new ArrayList<>();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int x = x0;
        int y = y0;

        while (true) {
            points.add(new int[]{x, y});
            if (x == x1 && y == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
        return points;
    }

    public MapType getMapType() {
        return mapType;
    }

    public List<TerrainTile> getTerrain() {
        return terrain;
    }

    /** @deprecated use getTerrain */
    public List<Obstacle> getObstacles() {
        return terrain.stream()
                .filter(t -> t.type().impassable)
                .map(t -> new Obstacle(t.x(), t.y()))
                .toList();
    }
}
