package Workshop.Rockets;

import Robots.Robot;
import Workshop.GameWorkShop;

public class LargeDistanceRockets extends Rockets {

    public LargeDistanceRockets() {
        super("LargeDistanceRockets", 5, 5, 8, 5);
    }
    public void instanceLargeDistanceRockets(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлены ракеты большой дальности");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}

