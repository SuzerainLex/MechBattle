package Workshop.Rockets;

import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallDistanceRockets extends Rockets {
    public SmallDistanceRockets() {
        super("SmallDistanceRockets", 7, 5, 6, 0, 4,0);
    }
    public void instanceSmallDistanceRockets(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены ракеты малой дальности");
            bot.rockets++;
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot);
    }
}
