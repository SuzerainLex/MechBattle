package Workshop.Rockets;

import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallDistanceRockets extends Rockets {
    public SmallDistanceRockets() {
        super("SmallDistanceRockets", 7, 5, 6, 0, 4,0, 2);
    }
    public void instanceSmallDistanceRockets(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены ракеты малой дальности");
            bot.rockets++;
            bot.allRockets.add(this);
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
        //    System.out.println("Вес вооружения " + bot.weaponWeight);
            if(bot.weaponWeight > bot.maxWeigth)
                GameWorkShop.getWarning(bot);
        }
     //   else   GameWorkShop.getWarning(bot);
        else  GameWorkShop.getWarning(bot);
}
}
