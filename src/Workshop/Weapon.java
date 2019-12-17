package Workshop;

import Robots.Robot;

public abstract class Weapon
{
    public String name;
    public final int cost;
    public int weight, damage, range, heat, ammunition, damageHeat;

    public Weapon(String name, int cost, int weight, int damage, int range, int heat, int ammunition, int damageHeat) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.damage = damage;
        this.range = range;
        this.heat = heat;
        this.ammunition = ammunition;
        this.damageHeat = damageHeat;
    }

    public static void removeWeapon(Robot bot){
        bot.weapons.clear();
        bot.rightHandWeapon.clear();
        bot.leftHandWeapon.clear();
        bot.guns = 0; bot.rockets = 0; bot.lasers = 0;
        bot.setWeaponWeight(0);
        System.out.println("Все оружие деисталлировано");
  }

}


