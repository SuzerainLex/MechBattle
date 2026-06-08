package com.mechbattle.server.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameSession {

    private static final Random RANDOM = new Random();

    private final String roomId;
    private GamePhase phase = GamePhase.WAITING;
    private final MechState player1;
    private final MechState player2;
    private String activePlayerId;
    private String winnerId;
    private final List<String> log = new ArrayList<>();
    private final BattleField battleField = new BattleField();

    public GameSession(String roomId, String player1Id, String player1Name, String player2Id, String player2Name) {
        this.roomId = roomId;
        this.player1 = new MechState(player1Id, player1Name, 6, 0);
        this.player2 = new MechState(player2Id, player2Name, 6, MechState.FIELD_SIZE);
    }

    public synchronized GameActionResult selectMech(String playerId, MechType mechType) {
        MechState player = getPlayer(playerId);
        if (player == null) {
            return GameActionResult.error("Игрок не найден");
        }
        if (phase != GamePhase.MECH_SELECT && phase != GamePhase.WAITING) {
            return GameActionResult.error("Сейчас нельзя выбрать меха");
        }
        player.selectMech(mechType);
        player.setReady(false);
        log.add(player.getPlayerName() + " выбрал " + mechType.displayName);
        if (phase == GamePhase.WAITING) {
            phase = GamePhase.MECH_SELECT;
        }
        maybeEnterWorkshop();
        return GameActionResult.ok("Мех выбран");
    }

    private void maybeEnterWorkshop() {
        if (player1.hasMech() && player2.hasMech() && phase == GamePhase.MECH_SELECT) {
            phase = GamePhase.WORKSHOP;
            log.add("Мастерская открыта — установите оружие");
        }
    }

    public synchronized GameActionResult equipWeapon(String playerId, String weaponKey, String slot) {
        MechState player = getPlayer(playerId);
        if (player == null) {
            return GameActionResult.error("Игрок не найден");
        }
        if (phase != GamePhase.WORKSHOP) {
            return GameActionResult.error("Сейчас нельзя менять оружие");
        }
        Optional<WeaponCatalog> catalog = WeaponCatalog.fromKey(weaponKey);
        if (catalog.isEmpty()) {
            return GameActionResult.error("Неизвестное оружие");
        }
        Optional<String> error = player.equipWeapon(catalog.get(), slot.toLowerCase());
        if (error.isPresent()) {
            return GameActionResult.error(error.get());
        }
        log.add(player.getPlayerName() + " установил " + catalog.get().displayName + " (" + slot + ")");
        return GameActionResult.ok("Оружие установлено");
    }

    public synchronized GameActionResult removeWeapons(String playerId, String categoryKey) {
        MechState player = getPlayer(playerId);
        if (player == null) {
            return GameActionResult.error("Игрок не найден");
        }
        if (phase != GamePhase.WORKSHOP) {
            return GameActionResult.error("Сейчас нельзя менять оружие");
        }
        if ("ALL".equalsIgnoreCase(categoryKey)) {
            player.clearAllWeapons();
            log.add(player.getPlayerName() + " снял всё оружие");
            return GameActionResult.ok("Всё оружие снято");
        }
        try {
            WeaponCategory category = WeaponCategory.valueOf(categoryKey.toUpperCase());
            player.removeByCategory(category);
            log.add(player.getPlayerName() + " снял " + category.name().toLowerCase());
            return GameActionResult.ok("Оружие снято");
        } catch (IllegalArgumentException e) {
            return GameActionResult.error("Неизвестная категория");
        }
    }

    public synchronized GameActionResult setReady(String playerId) {
        MechState player = getPlayer(playerId);
        if (player == null) {
            return GameActionResult.error("Игрок не найден");
        }
        if (phase != GamePhase.WORKSHOP) {
            return GameActionResult.error("Сейчас нельзя подтвердить готовность");
        }
        if (!player.hasMech()) {
            return GameActionResult.error("Сначала выберите меха");
        }
        if (!player.canEnterBattle()) {
            if (player.getWeapons().isEmpty()) {
                return GameActionResult.error("Установите хотя бы одно оружие");
            }
            return GameActionResult.error("Превышен допустимый вес — измените вооружение");
        }
        player.setReady(true);
        log.add(player.getPlayerName() + " готов к бою");

        if (player1.isReady() && player2.isReady()) {
            startBattle();
        }
        return GameActionResult.ok("Готов");
    }

    private void startBattle() {
        phase = GamePhase.BATTLE;
        battleField.generate(player1, player2, RANDOM);
        log.add("Карта: " + battleField.getMapType().displayName);
        log.add("Объектов на поле: " + battleField.getTerrain().size());

        int roll1 = player1.getMechType().initiative * (1 + RANDOM.nextInt(10));
        int roll2 = player2.getMechType().initiative * (1 + RANDOM.nextInt(10));
        if (roll1 >= roll2) {
            activePlayerId = player1.getPlayerId();
            log.add("Первым ходит " + player1.getPlayerName());
        } else {
            activePlayerId = player2.getPlayerId();
            log.add("Первым ходит " + player2.getPlayerName());
        }

        player1.resetTurn();
        player2.resetTurn();
        player1.setReady(false);
        player2.setReady(false);
    }

    public synchronized GameActionResult move(String playerId, Direction direction) {
        if (!isActivePlayer(playerId)) {
            return GameActionResult.error("Сейчас не ваш ход");
        }
        if (phase != GamePhase.BATTLE) {
            return GameActionResult.error("Бой ещё не начался");
        }

        MechState active = getActivePlayer();
        MechState enemy = getEnemy(active);

        if (!active.canMove()) {
            return GameActionResult.error("Ноги уничтожены — ход невозможен");
        }
        if (active.getActionPoints() <= 0) {
            return GameActionResult.error("Очки действий закончились");
        }

        int newX = active.getX();
        int newY = active.getY();

        switch (direction) {
            case NORTH -> {
                if (active.getY() >= MechState.FIELD_SIZE) {
                    return GameActionResult.error("Нельзя идти дальше");
                }
                newY++;
            }
            case SOUTH -> {
                if (active.getY() <= 0) {
                    return GameActionResult.error("Нельзя идти дальше");
                }
                newY--;
            }
            case WEST -> {
                if (active.getX() <= 0) {
                    return GameActionResult.error("Нельзя идти дальше");
                }
                newX--;
            }
            case EAST -> {
                if (active.getX() >= MechState.FIELD_SIZE) {
                    return GameActionResult.error("Нельзя идти дальше");
                }
                newX++;
            }
        }

        if (battleField.isBlocked(newX, newY)) {
            return GameActionResult.error("Препятствие на пути");
        }

        int moveCost = battleField.getMoveCost(newX, newY);
        if (active.getActionPoints() < moveCost) {
            return GameActionResult.error("Недостаточно ОД (вода = 2 ОД)");
        }

        active.setX(newX);
        active.setY(newY);
        active.spendActionPoints(moveCost);
        if (moveCost > 1) {
            log.add(active.getPlayerName() + " пересекает воду");
        }
        log.add(active.getPlayerName() + " переместился на " + newX + ":" + newY);

        boolean melee = active.getX() == enemy.getX() && active.getY() == enemy.getY();
        if (melee) {
            resolveMelee(active, enemy, direction);
        }

        checkVictory(active);
        if (phase == GamePhase.FINISHED) {
            return GameActionResult.ok("Таран! Бой окончен", "warning");
        }

        if (active.getActionPoints() <= 0) {
            endTurn(active);
        }
        return GameActionResult.ok("Ход выполнен", melee ? "melee" : "step");
    }

    private void resolveMelee(MechState active, MechState enemy, Direction direction) {
        int damage = active.getMeleeMight();
        enemy.damageBody(damage);
        active.damageBody(damage / 2);
        log.add("ТАРАН! " + damage + " урона врагу, " + (damage / 2) + " себе");

        switch (direction) {
            case NORTH -> active.setY(enemy.getY() - 1);
            case SOUTH -> active.setY(enemy.getY() + 1);
            case WEST -> active.setX(enemy.getX() + 1);
            case EAST -> active.setX(enemy.getX() - 1);
        }
    }

    public synchronized GameActionResult attack(String playerId, String weaponId, int aim) {
        if (!isActivePlayer(playerId)) {
            return GameActionResult.error("Сейчас не ваш ход");
        }
        if (phase != GamePhase.BATTLE) {
            return GameActionResult.error("Бой ещё не начался");
        }
        if (aim < 0 || aim > 10) {
            return GameActionResult.error("Прицел должен быть от 0 до 10");
        }

        MechState active = getActivePlayer();
        MechState enemy = getEnemy(active);

        WeaponInstance weapon = active.getWeapons().stream()
                .filter(w -> w.getId().equals(weaponId))
                .findFirst()
                .orElse(null);

        if (weapon == null) {
            return GameActionResult.error("Оружие не найдено");
        }
        if (weapon.getAmmunition() <= 0) {
            return GameActionResult.error("Нет боеприпасов");
        }
        if (active.getActionPoints() < weapon.getCost()) {
            return GameActionResult.error("Не хватает очков действий");
        }
        if (active.getHeat() > active.getRadiator()) {
            return GameActionResult.error("Мех перегрелся");
        }
        if (!weapon.inRange(active, enemy)) {
            return GameActionResult.error("Цель вне дальности");
        }
        if (!battleField.hasLineOfSight(active.getX(), active.getY(), enemy.getX(), enemy.getY())) {
            return GameActionResult.error("Нет прямой видимости — препятствие закрывает цель");
        }

        int roll = RANDOM.nextInt(11);
        int baseDamage = weapon.getDamage();
        String hitMessage;
        String hitLocation = null;

        if (roll == aim) {
            hitLocation = "head";
            hitMessage = "Попадание в голову!";
        } else if (roll - 1 == aim || roll + 1 == aim) {
            hitLocation = "body";
            hitMessage = "Попадание в корпус!";
        } else if (roll + 1 == aim && enemy.getLegsArmor() > 0) {
            hitLocation = "legs";
            hitMessage = "Попадание в ноги!";
        } else if (roll + 2 == aim && enemy.getLeftHandArmor() > 0) {
            hitLocation = "leftArm";
            hitMessage = "Попадание в левую руку!";
        } else if (roll - 2 == aim && enemy.getRightHandArmor() > 0) {
            hitLocation = "rightArm";
            hitMessage = "Попадание в правую руку!";
        } else {
            hitMessage = "Промах!";
        }

        boolean hit = hitLocation != null;
        boolean critical = false;
        int damage = baseDamage;

        if (hit) {
            if (RANDOM.nextDouble() < 0.15) {
                critical = true;
                damage = (int) Math.ceil(baseDamage * 1.5);
                hitMessage += " КРИТИЧЕСКОЕ!";
            }
            TerrainType cover = battleField.getTerrainAt(enemy.getX(), enemy.getY());
            if (cover == TerrainType.FOREST) {
                damage = Math.max(1, damage - 2);
                hitMessage += " (укрытие леса)";
            }
            applyDamage(enemy, hitLocation, damage);
            if ("legs".equals(hitLocation) && enemy.getLegsArmor() <= 0) {
                log.add("Ноги " + enemy.getPlayerName() + " уничтожены");
            }
            if ("leftArm".equals(hitLocation) && enemy.getLeftHandArmor() <= 0) {
                enemy.removeLeftHandWeapons();
                log.add("Левая рука " + enemy.getPlayerName() + " уничтожена");
            }
            if ("rightArm".equals(hitLocation) && enemy.getRightHandArmor() <= 0) {
                enemy.removeRightHandWeapons();
                log.add("Правая рука " + enemy.getPlayerName() + " уничтожена");
            }
            if (critical && "body".equals(hitLocation) && enemy.getBodyArmor() <= 0) {
                log.add("Корпус пробит — возможна детонация!");
            }
        }

        weapon.spendAmmo();
        active.spendActionPoints(weapon.getCost());
        active.addHeat(weapon.getHeat());
        enemy.addHeat(weapon.getDamageHeat());

        log.add(active.getPlayerName() + " стреляет из " + weapon.getName() + " (прицел " + aim + ", бросок " + roll + "): " + hitMessage);

        CombatEvent event = CombatEvent.shot(
                active.getX(), active.getY(),
                enemy.getX(), enemy.getY(),
                weapon.getCategory().name(), hit, critical
        );

        checkVictory(active);
        if (phase == GamePhase.FINISHED) {
            return GameActionResult.ok(hitMessage, "warning", event);
        }
        if (active.getActionPoints() <= 0) {
            endTurn(active);
        }
        return GameActionResult.ok(hitMessage, weapon.soundKey(), event);
    }

    private void applyDamage(MechState target, String location, int damage) {
        switch (location) {
            case "head" -> target.damageHead(damage);
            case "body" -> target.damageBody(damage);
            case "legs" -> target.damageLegs(damage);
            case "leftArm" -> target.damageLeftHand(damage);
            case "rightArm" -> target.damageRightHand(damage);
        }
    }

    public synchronized GameActionResult skipTurn(String playerId) {
        if (!isActivePlayer(playerId)) {
            return GameActionResult.error("Сейчас не ваш ход");
        }
        if (phase != GamePhase.BATTLE) {
            return GameActionResult.error("Бой ещё не начался");
        }
        MechState active = getActivePlayer();
        active.clearActionPoints();
        log.add(active.getPlayerName() + " пропустил ход");
        endTurn(active);
        return GameActionResult.ok("Ход пропущен");
    }

    private void endTurn(MechState active) {
        active.resetTurn();
        MechState next = getEnemy(active);
        next.resetTurn();
        activePlayerId = next.getPlayerId();
        log.add("Ход " + next.getPlayerName());
    }

    private void checkVictory(MechState attacker) {
        MechState enemy = getEnemy(attacker);
        if (enemy.isDestroyed()) {
            phase = GamePhase.FINISHED;
            winnerId = attacker.getPlayerId();
            log.add(enemy.getPlayerName() + " уничтожен!");
            log.add(attacker.getPlayerName() + " победил!");
        }
    }

    private boolean isActivePlayer(String playerId) {
        return playerId != null && playerId.equals(activePlayerId);
    }

    public MechState getPlayer(String playerId) {
        if (player1.getPlayerId().equals(playerId)) {
            return player1;
        }
        if (player2.getPlayerId().equals(playerId)) {
            return player2;
        }
        return null;
    }

    public MechState getActivePlayer() {
        return getPlayer(activePlayerId);
    }

    public MechState getEnemy(MechState player) {
        return player == player1 ? player2 : player1;
    }

    public String getRoomId() {
        return roomId;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public MechState getPlayer1() {
        return player1;
    }

    public MechState getPlayer2() {
        return player2;
    }

    public String getActivePlayerId() {
        return activePlayerId;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public List<String> getLog() {
        return log;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public String getMapTypeName() {
        return battleField.getMapType().name();
    }
}
