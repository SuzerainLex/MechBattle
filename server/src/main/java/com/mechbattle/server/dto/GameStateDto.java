package com.mechbattle.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mechbattle.server.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GameStateDto(
        String roomId,
        String phase,
        String yourPlayerId,
        String activePlayerId,
        String winnerId,
        String mapType,
        List<PlayerDto> players,
        List<WeaponCatalogDto> catalog,
        List<TerrainDto> terrain,
        List<ObstacleDto> obstacles,
        List<String> log
) {
    public static GameStateDto from(GameSession session, String viewerPlayerId) {
        boolean inBattle = session.getPhase() == GamePhase.BATTLE || session.getPhase() == GamePhase.FINISHED;

        List<PlayerDto> players = List.of(
                PlayerDto.from(session.getPlayer1(), inBattle ? session.getEnemy(session.getPlayer1()) : null, session.getBattleField(), inBattle),
                PlayerDto.from(session.getPlayer2(), inBattle ? session.getEnemy(session.getPlayer2()) : null, session.getBattleField(), inBattle)
        );
        List<WeaponCatalogDto> catalog = Arrays.stream(WeaponCatalog.values())
                .map(WeaponCatalogDto::from)
                .toList();
        List<TerrainDto> terrain = session.getBattleField().getTerrain().stream()
                .map(t -> new TerrainDto(t.x(), t.y(), t.type().name()))
                .toList();
        List<ObstacleDto> obstacles = session.getBattleField().getObstacles().stream()
                .map(o -> new ObstacleDto(o.x(), o.y()))
                .toList();
        return new GameStateDto(
                session.getRoomId(),
                session.getPhase().name(),
                viewerPlayerId,
                session.getActivePlayerId(),
                session.getWinnerId(),
                session.getMapTypeName(),
                players,
                catalog,
                terrain,
                obstacles,
                new ArrayList<>(session.getLog())
        );
    }

    public record PlayerDto(
            String id,
            String name,
            String mech,
            int x,
            int y,
            ArmorDto armor,
            ArmorDto maxArmor,
            int heat,
            int radiator,
            int actionPoints,
            int maxActionPoints,
            int weaponWeight,
            int maxWeight,
            int guns,
            int gunSockets,
            int lasers,
            int laserSockets,
            int rockets,
            int rocketSockets,
            boolean ready,
            List<WeaponDto> weapons
    ) {
        public static PlayerDto from(MechState state, MechState opponent, BattleField field, boolean inBattle) {
            String mechName = state.getMechType() != null ? state.getMechType().name() : null;
            List<WeaponDto> weapons = state.getWeapons().stream()
                    .map(w -> WeaponDto.from(w, inBattle ? state : null, opponent, field))
                    .toList();
            return new PlayerDto(
                    state.getPlayerId(),
                    state.getPlayerName(),
                    mechName,
                    state.getX(),
                    state.getY(),
                    new ArmorDto(
                            state.getHeadArmor(),
                            state.getBodyArmor(),
                            state.getLeftHandArmor(),
                            state.getRightHandArmor(),
                            state.getLegsArmor()
                    ),
                    new ArmorDto(
                            state.getMaxHeadArmor(),
                            state.getMaxBodyArmor(),
                            state.getMaxLeftHandArmor(),
                            state.getMaxRightHandArmor(),
                            state.getMaxLegsArmor()
                    ),
                    state.getHeat(),
                    state.getRadiator(),
                    state.getActionPoints(),
                    state.getMaxActionPoints(),
                    state.getWeaponWeight(),
                    state.getMaxWeight(),
                    state.getGuns(),
                    state.getGunSockets(),
                    state.getLasers(),
                    state.getLaserSockets(),
                    state.getRockets(),
                    state.getRocketSockets(),
                    state.isReady(),
                    weapons
            );
        }
    }

    public record ArmorDto(int head, int body, int leftArm, int rightArm, int legs) {
    }

    public record ObstacleDto(int x, int y) {
    }

    public record TerrainDto(int x, int y, String type) {
    }

    public record WeaponDto(
            String id,
            String catalogKey,
            String name,
            String slot,
            String category,
            int ammunition,
            int range,
            int damage,
            int weight,
            int cost,
            int heat,
            boolean inRange,
            boolean hasLineOfSight,
            boolean canFire,
            String disabledReason
    ) {
        public static WeaponDto from(WeaponInstance weapon, MechState attacker, MechState target, BattleField field) {
            boolean inRange = false;
            boolean hasLos = false;
            boolean canFire = false;
            String reason = "";

            if (attacker != null && target != null) {
                inRange = weapon.inRange(attacker, target);
                hasLos = field.hasLineOfSight(attacker.getX(), attacker.getY(), target.getX(), target.getY());
                canFire = weapon.canFireAt(attacker, target, field);
                if (weapon.getAmmunition() <= 0) {
                    reason = "Нет боеприпасов";
                } else if (!inRange) {
                    reason = "Вне дальности";
                } else if (!hasLos) {
                    reason = "Нет обзора";
                }
            }

            return new WeaponDto(
                    weapon.getId(),
                    weapon.getCatalogKey(),
                    weapon.getName(),
                    weapon.getSlot(),
                    weapon.getCategory().name(),
                    weapon.getAmmunition(),
                    weapon.getRange(),
                    weapon.getDamage(),
                    weapon.getWeight(),
                    weapon.getCost(),
                    weapon.getHeat(),
                    inRange,
                    hasLos,
                    canFire,
                    reason
            );
        }
    }

    public record WeaponCatalogDto(
            String key,
            String name,
            String category,
            int weight,
            int damage,
            int range,
            int heat,
            int ammunition,
            int cost,
            boolean handMounted
    ) {
        public static WeaponCatalogDto from(WeaponCatalog catalog) {
            return new WeaponCatalogDto(
                    catalog.name(),
                    catalog.displayName,
                    catalog.category.name(),
                    catalog.weight,
                    catalog.damage,
                    catalog.range,
                    catalog.heat,
                    catalog.ammunition,
                    catalog.cost,
                    catalog.handMounted
            );
        }
    }
}
