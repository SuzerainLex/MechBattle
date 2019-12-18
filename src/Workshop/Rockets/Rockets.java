package Workshop.Rockets;

import Messages.Message;
import Robots.Robot;
import Workshop.Weapon;
import java.util.Iterator;

public abstract class Rockets extends Weapon {

    public Rockets(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat, int cost) {
        super(name, cost, weight, damage, range, heat, ammunition, damageHeat);
    }



    public static void removeRockets(Robot bot) {
        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {
            Weapon nextRocket = weaponIterator.next();
            if (nextRocket instanceof SmallDistanceRockets) {
                bot.setWeaponWeight(bot.getWeaponWeight() - nextRocket.weight);
                weaponIterator.remove();
            }
            if (nextRocket instanceof LargeDistanceRockets) {
                bot.setWeaponWeight(bot.getWeaponWeight() - nextRocket.weight);
                weaponIterator.remove();
            }
        }
        bot.allRockets.clear();
        bot.rockets = 0;
        System.out.println("Все ракеты деинсталированы");
    }
    public void instanceRockets(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены  " + this.name);
            bot.weapons.add(this);
            bot.allRockets.add(this);
            bot.rockets++;
            bot.setWeaponWeight(bot.getWeaponWeight() + this.weight);
            System.out.println("Установлено ракет " + bot.rockets);
            //ПОСТ ПРОВЕРКА
            if (bot.getWeaponWeight() > bot.getMaxWeigth())
                Message.getWarning(bot);
        }
        else  Message.getWarning(bot);
    }

}

