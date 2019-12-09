package Workshop.Rockets;

import Workshop.Weapon;

public class Rockets extends Weapon
{
    public int ammunition;

    public Rockets(String name, int weight, int damage, int range, int ammunition) {
        super(name, weight, damage, range);
        this.ammunition = ammunition;
    }
}
