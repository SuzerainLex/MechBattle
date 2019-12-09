package Workshop.Rockets;

import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallDistanceRockets extends Rockets {
    public SmallDistanceRockets() {
        super("SmallDistanceRockets", 7, 7, 6, 5);
    }
    public void instanceSmallDistanceRockets(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлены ракеты малой дальности");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
