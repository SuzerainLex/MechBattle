package Workshop.Rockets;

import Robots.Robot;
import Workshop.GameWorkShop;

public class LargeDistanceRockets extends Rockets {

    public LargeDistanceRockets() {
        super("LargeDistanceRockets", 5, 5, 8, 0, 4, 0,2 );
    }
    public void instanceLargeDistanceRockets(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены ракеты большой дальности");
            bot.weapons.add(this);
            bot.allRockets.add(this);
            bot.rockets++;
            bot.weaponWeight += this.weight;
            //System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено ракет " + bot.rockets);
            if(bot.weaponWeight > bot.maxWeigth)
                GameWorkShop.getWarning(bot);
        }
       // else System.out.println("Превышен лимит установки данного типа оружия");
        else  GameWorkShop.getWarning(bot);
    }
}

