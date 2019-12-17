package Workshop.Rockets;

import Messages.Message;
import Robots.Robot;
import Workshop.GameWorkShop;

public class LargeDistanceRockets extends Rockets {

    public LargeDistanceRockets() {
        super("LargeDistanceRockets", 5, 5, 8, 1, 4, 0,2 );
    }
    public void instanceLargeDistanceRockets(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены ракеты большой дальности");
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

