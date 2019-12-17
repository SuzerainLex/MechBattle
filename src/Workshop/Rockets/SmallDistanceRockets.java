package Workshop.Rockets;

import Messages.Message;
import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallDistanceRockets extends Rockets {
    public SmallDistanceRockets() {
        super("SmallDistanceRockets", 5, 7, 6, 1, 4, 0, 2);
    }

    public void instanceSmallDistanceRockets(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.rockets < bot.rocketSockets) {
            System.out.println("Установлены ракеты малой дальности");
            bot.rockets++;
            bot.allRockets.add(this);
            bot.weapons.add(this);
            bot.setWeaponWeight(bot.getWeaponWeight() + this.weight);
            //ПОСТ ПРОВЕРКА
            if (bot.getWeaponWeight() > bot.getMaxWeigth())
                Message.getWarning(bot);
        } else Message.getWarning(bot);
    }
}
