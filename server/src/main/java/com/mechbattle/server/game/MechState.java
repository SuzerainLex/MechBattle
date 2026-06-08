package com.mechbattle.server.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MechState {

    public static final int FIELD_SIZE = 12;

    private final String playerId;
    private final String playerName;
    private MechType mechType;
    private int x;
    private int y;
    private int headArmor;
    private int bodyArmor;
    private int legsArmor;
    private int leftHandArmor;
    private int rightHandArmor;
    private int maxHeadArmor;
    private int maxBodyArmor;
    private int maxLegsArmor;
    private int maxLeftHandArmor;
    private int maxRightHandArmor;
    private int heat;
    private int actionPoints;
    private int maxActionPoints;
    private int weaponWeight;
    private int guns;
    private int lasers;
    private int rockets;
    private final List<WeaponInstance> weapons = new ArrayList<>();
    private boolean ready;

    public MechState(String playerId, String playerName, int startX, int startY) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.x = startX;
        this.y = startY;
    }

    public void selectMech(MechType type) {
        this.mechType = type;
        this.headArmor = type.armor / 5;
        this.bodyArmor = type.armor / 2;
        this.legsArmor = type.armor / 3;
        this.leftHandArmor = type.armor / 4;
        this.rightHandArmor = type.armor / 4;
        this.maxHeadArmor = this.headArmor;
        this.maxBodyArmor = this.bodyArmor;
        this.maxLegsArmor = this.legsArmor;
        this.maxLeftHandArmor = this.leftHandArmor;
        this.maxRightHandArmor = this.rightHandArmor;
        this.maxActionPoints = type.maxMoves;
        this.actionPoints = type.maxMoves;
        this.heat = 0;
        clearAllWeapons();
        this.ready = false;
    }

    public Optional<String> equipWeapon(WeaponCatalog catalog, String slot) {
        if (mechType == null) {
            return Optional.of("Сначала выберите меха");
        }
        if (!catalog.handMounted && !"body".equals(slot)) {
            return Optional.of("Ракеты ставятся только на корпус");
        }
        if (catalog.handMounted && "body".equals(slot)) {
            return Optional.of("Это оружие ставится на руку");
        }
        if (catalog.handMounted && ("left".equals(slot) || "right".equals(slot))) {
            if ("left".equals(slot) && countHandWeapons("left") >= mechType.maxLeftHandSlots) {
                return Optional.of("Левая рука занята");
            }
            if ("right".equals(slot) && countHandWeapons("right") >= mechType.maxRightHandSlots) {
                return Optional.of("Правая рука занята");
            }
        }
        if (catalog.category == WeaponCategory.GUN && guns >= mechType.gunSockets) {
            return Optional.of("Превышен лимит пушек");
        }
        if (catalog.category == WeaponCategory.LASER && lasers >= mechType.laserSockets) {
            return Optional.of("Превышен лимит лазеров");
        }
        if (catalog.category == WeaponCategory.ROCKET && rockets >= mechType.rocketSockets) {
            return Optional.of("Превышен лимит ракет");
        }
        if (weaponWeight + catalog.weight > mechType.maxWeight) {
            return Optional.of("Превышен допустимый вес");
        }

        WeaponInstance instance = new WeaponInstance(catalog.toDef(slot));
        weapons.add(instance);
        weaponWeight += catalog.weight;
        switch (catalog.category) {
            case GUN -> guns++;
            case LASER -> lasers++;
            case ROCKET -> rockets++;
        }
        ready = false;
        return Optional.empty();
    }

    public void removeByCategory(WeaponCategory category) {
        weapons.removeIf(w -> {
            if (w.getCategory() != category) {
                return false;
            }
            weaponWeight -= w.getWeight();
            switch (category) {
                case GUN -> guns--;
                case LASER -> lasers--;
                case ROCKET -> rockets--;
            }
            return true;
        });
        ready = false;
    }

    public void clearAllWeapons() {
        weapons.clear();
        weaponWeight = 0;
        guns = 0;
        lasers = 0;
        rockets = 0;
        ready = false;
    }

    private int countHandWeapons(String slot) {
        return (int) weapons.stream().filter(w -> slot.equals(w.getSlot())).count();
    }

    public boolean isOverweight() {
        return mechType != null && weaponWeight > mechType.maxWeight;
    }

    public boolean canEnterBattle() {
        return mechType != null && !weapons.isEmpty() && !isOverweight();
    }

    public void resetTurn() {
        if (mechType != null) {
            actionPoints = maxActionPoints;
            heat = Math.max(0, heat - 5);
        }
    }

    public boolean isDestroyed() {
        return headArmor <= 0 || bodyArmor <= 0;
    }

    public boolean canMove() {
        return legsArmor > 0;
    }

    public int getRadiator() {
        return mechType != null ? mechType.radiator : 0;
    }

    public int getMeleeMight() {
        return mechType != null ? mechType.meleeMight : 0;
    }

    public int getMaxWeight() {
        return mechType != null ? mechType.maxWeight : 0;
    }

    public int getWeaponWeight() {
        return weaponWeight;
    }

    public int getGunSockets() {
        return mechType != null ? mechType.gunSockets : 0;
    }

    public int getLaserSockets() {
        return mechType != null ? mechType.laserSockets : 0;
    }

    public int getRocketSockets() {
        return mechType != null ? mechType.rocketSockets : 0;
    }

    public int getGuns() {
        return guns;
    }

    public int getLasers() {
        return lasers;
    }

    public int getRockets() {
        return rockets;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public MechType getMechType() {
        return mechType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMaxHeadArmor() {
        return maxHeadArmor;
    }

    public int getMaxBodyArmor() {
        return maxBodyArmor;
    }

    public int getMaxLegsArmor() {
        return maxLegsArmor;
    }

    public int getMaxLeftHandArmor() {
        return maxLeftHandArmor;
    }

    public int getMaxRightHandArmor() {
        return maxRightHandArmor;
    }

    public int getHeadArmor() {
        return Math.max(0, headArmor);
    }

    public void damageHead(int damage) {
        headArmor -= damage;
    }

    public int getBodyArmor() {
        return Math.max(0, bodyArmor);
    }

    public void damageBody(int damage) {
        bodyArmor -= damage;
    }

    public int getLegsArmor() {
        return Math.max(0, legsArmor);
    }

    public void damageLegs(int damage) {
        legsArmor -= damage;
    }

    public int getLeftHandArmor() {
        return Math.max(0, leftHandArmor);
    }

    public void damageLeftHand(int damage) {
        leftHandArmor -= damage;
    }

    public int getRightHandArmor() {
        return Math.max(0, rightHandArmor);
    }

    public void damageRightHand(int damage) {
        rightHandArmor -= damage;
    }

    public int getHeat() {
        return Math.max(0, heat);
    }

    public void addHeat(int amount) {
        heat += amount;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void spendActionPoints(int cost) {
        actionPoints -= cost;
    }

    public void clearActionPoints() {
        actionPoints = 0;
    }

    public int getMaxActionPoints() {
        return maxActionPoints;
    }

    public List<WeaponInstance> getWeapons() {
        return weapons;
    }

    public List<WeaponInstance> getLeftHandWeapons() {
        return weapons.stream().filter(w -> "left".equals(w.getSlot())).toList();
    }

    public List<WeaponInstance> getRightHandWeapons() {
        return weapons.stream().filter(w -> "right".equals(w.getSlot())).toList();
    }

    public List<WeaponInstance> getBodyWeapons() {
        return weapons.stream().filter(w -> "body".equals(w.getSlot())).toList();
    }

    public void removeLeftHandWeapons() {
        weapons.removeIf(w -> {
            if (!"left".equals(w.getSlot())) {
                return false;
            }
            weaponWeight -= w.getWeight();
            adjustCategoryCount(w.getCategory(), -1);
            return true;
        });
    }

    public void removeRightHandWeapons() {
        weapons.removeIf(w -> {
            if (!"right".equals(w.getSlot())) {
                return false;
            }
            weaponWeight -= w.getWeight();
            adjustCategoryCount(w.getCategory(), -1);
            return true;
        });
    }

    private void adjustCategoryCount(WeaponCategory category, int delta) {
        switch (category) {
            case GUN -> guns += delta;
            case LASER -> lasers += delta;
            case ROCKET -> rockets += delta;
        }
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean hasMech() {
        return mechType != null;
    }
}
