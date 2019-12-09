package Workshop.Lasers;

import Workshop.Weapon;

public class Laser extends Weapon {
    private int heat, damageHeat;

    public Laser(String name, int weight, int damage, int range, int heat, int damageHeat) {
        super(name, weight, damage, range);
        this.heat = heat;
        this.damageHeat = damageHeat;
    }
}
