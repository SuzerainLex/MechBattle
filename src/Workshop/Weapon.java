package Workshop;

public abstract class Weapon
{
    private String name;
    public int weight;
    private int damage;
    private int range;

    public Weapon(String name, int weight, int damage, int range) {
        this.name = name;
        this.weight = weight;
        this.damage = damage;
        this.range = range;

    }
}
