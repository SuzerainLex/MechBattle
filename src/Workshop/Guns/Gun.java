package Workshop.Guns;

import Robots.Robot;
import Workshop.Lasers.BigLaser;
import Workshop.Weapon;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Gun extends Weapon {

    public Gun(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat) {
        super(name, weight, damage, range, heat, ammunition, damageHeat);
    }

    public static void getMessageGuns(Robot bot) {
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Маленькая пушка");
        System.out.println("2. Средняя пушка");
        System.out.println("3. Большая пушка");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все пушки");

    }

    public static void removeGuns(Robot bot) {

        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {
            Weapon nextGun = weaponIterator.next();
            if (nextGun instanceof MiniGun) {

                bot.weaponWeight -= nextGun.weight;
                weaponIterator.remove();
            } else if (nextGun instanceof MediumGun) {

                bot.weaponWeight -= nextGun.weight;
                weaponIterator.remove();
            } else if (nextGun instanceof BigGun) {

                bot.weaponWeight -= nextGun.weight;
                weaponIterator.remove();
            }
        }
        bot.guns = 0;
        System.out.println("Все пушки деинсталированы");
   }
}

