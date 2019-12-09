package Workshop.Guns;

import Workshop.Weapon;

public abstract class Gun extends Weapon {
    private int heat;
    public int ammunition;

    public Gun(String name, int weight, int damage, int range, int heat, int ammunition) {
        super(name, weight, damage, range);
        this.heat = heat;
        this.ammunition = ammunition;
    }
}
