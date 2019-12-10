package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallLaser extends Laser {
    public SmallLaser() {
        super("SmallLaser", 2, 3, 4, 3, 99, 5);
    }

    public void instanceSmallLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.lasers < bot.laserSockets) {
            System.out.println("Малый лазер");
            ++bot.lasers;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено лазеров " + bot.lasers);
            bot.weapons.add(this);

        }
        else   GameWorkShop.getWarning(bot);
    }
}
