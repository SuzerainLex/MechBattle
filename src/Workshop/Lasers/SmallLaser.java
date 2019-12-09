package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class SmallLaser extends Laser {
    public SmallLaser() {
        super("SmallLaser", 2, 3, 4, 3, 3);
    }

    public void instanceSmallLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Малый лазер");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
