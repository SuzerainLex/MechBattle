package com.mechbattle.server.game;

public class WeaponInstance {

    private final WeaponDef def;
    private int ammunition;

    public WeaponInstance(WeaponDef def) {
        this.def = def;
        this.ammunition = def.ammunition();
    }

    public WeaponDef getDef() {
        return def;
    }

    public String getId() {
        return def.id();
    }

    public String getCatalogKey() {
        return def.catalogKey();
    }

    public String getName() {
        return def.name();
    }

    public String getSlot() {
        return def.slot();
    }

    public WeaponCategory getCategory() {
        return def.category();
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void spendAmmo() {
        ammunition--;
    }

    public int getCost() {
        return def.cost();
    }

    public int getRange() {
        return def.range();
    }

    public int getDamage() {
        return def.damage();
    }

    public int getHeat() {
        return def.heat();
    }

    public int getDamageHeat() {
        return def.damageHeat();
    }

    public int getWeight() {
        return def.weight();
    }

    public String soundKey() {
        return WeaponCatalog.fromKey(def.catalogKey()).map(WeaponCatalog::soundKey).orElse("hit");
    }

    public boolean canFireAt(MechState attacker, MechState target, BattleField field) {
        return inRange(attacker, target)
                && field.hasLineOfSight(attacker.getX(), attacker.getY(), target.getX(), target.getY())
                && getAmmunition() > 0;
    }

    public boolean inRange(MechState attacker, MechState target) {
        int dx = Math.abs(target.getX() - attacker.getX());
        int dy = Math.abs(target.getY() - attacker.getY());
        return dx <= def.range() && dy <= def.range();
    }
}
