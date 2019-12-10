package Workshop.Lasers;

import Robots.Robot;
import Workshop.Weapon;

import java.util.Iterator;


public class Laser extends Weapon {

    public Laser(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat) {
        super(name, weight, damage, range, heat, ammunition, damageHeat);
    }

    public static void getMessageLasers(Robot bot) {
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Малый лазер");
        System.out.println("2. Средний лазер");
        System.out.println("3. Большой лазер");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все лазеры");
    }

    public static void removeLasers(Robot bot) {

        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {
            Weapon nextLaser = weaponIterator.next();
            if (nextLaser instanceof SmallLaser) {

                bot.weaponWeight -= nextLaser.weight;
                weaponIterator.remove();
            }
            if (nextLaser instanceof MediumLaser) {

                bot.weaponWeight -= nextLaser.weight;
                weaponIterator.remove();
            }
            if (nextLaser instanceof BigLaser) {
                bot.weaponWeight -= nextLaser.weight;
                weaponIterator.remove();
            }
        }
        bot.lasers = 0;
        System.out.println("Все лазеры деинсталированы");
    }

}

