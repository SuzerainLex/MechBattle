package Workshop.Rockets;

import Robots.Robot;
import Workshop.Weapon;

import java.io.IOException;
import java.util.Iterator;

public abstract class Rockets extends Weapon {

    public Rockets(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat, int cost) {
        super(name, cost, weight, damage, range, heat, ammunition, damageHeat);
    }

       public static void getMessageRockets(Robot bot) {
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Ракеты малой дальности  | Урон: 7 | Дальность: 6 | Боезапас: 4  | Очки хода: 2  | Перегрев: 1 | Вес: 5 |");
        System.out.println("2. Ракеты большой дальности  | Урон: 5 | Дальность: 8 | Боезапас: 4  | Очки хода: 2  | Перегрев: 1 | Вес: 5 |");
        System.out.println("3. Назад");
        System.out.println("4. Убрать все ракеты");

    }

    public static void removeRockets(Robot bot) {
        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {
            Weapon nextRocket = weaponIterator.next();
            if (nextRocket instanceof SmallDistanceRockets) {

                bot.weaponWeight -= nextRocket.weight;
                weaponIterator.remove();
            }
            if (nextRocket instanceof LargeDistanceRockets) {

                bot.weaponWeight -= nextRocket.weight;
                weaponIterator.remove();
            }

        }
        bot.allRockets.clear();
        bot.rockets = 0;
        System.out.println("Все ракеты деинсталированы");
    }

}
