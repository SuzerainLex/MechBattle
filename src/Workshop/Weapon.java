package Workshop;

import Robots.Robot;

public abstract class Weapon
{
    public String name;
    public int weight, damage, range, heat, ammunition, damageHeat;



    public Weapon(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat) {
        this.name = name;
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
            System.out.print(" | " + w.name+ " | ");
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
        bot.weaponWeight = 0;
        System.out.println("Все оружие деисталлировано");
  }
}


