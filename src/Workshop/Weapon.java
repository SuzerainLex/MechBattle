package Workshop;

import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;

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

    public static void getMessageWeapons(Robot bot) {
        System.out.println("Выберите оружие");
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("Установлены : ");
        for (Weapon w: bot.weapons) {
            if (w instanceof Gun) {
                if (((Gun) w).leftHand) {
                    System.out.print(" | " + w.name + " в левой руке | ");
                } else if (((Gun) w).rightHand) {
                    System.out.print(" | " + w.name + " в правой руке | ");
                }

            }else if(w instanceof Laser){
                if (((Laser) w).leftHand) {
                    System.out.print(" | " + w.name + " в левой руке | ");
                } else if (((Laser) w).rightHand) {
                    System.out.print(" | " + w.name + " в правой руке | ");
                }
            } else System.out.print(" | " + w.name + " на корпусе | ");
        }

        System.out.println();
        System.out.println("1. Пушки");
        System.out.println("2. Ракеты");
        System.out.println("3. Лазеры");
        System.out.println("4. Убрать всё");
        System.out.println("5. Закончить установку оружия");
    }

    public static void removeWeapon(Robot bot){
        bot.weapons.clear();
        bot.rightHandWeapon.clear();
        bot.leftHandWeapon.clear();
        bot.guns = 0; bot.rockets = 0; bot.lasers = 0;
        bot.weaponWeight = 0;
        System.out.println("Все оружие деисталлировано");
  }

}


