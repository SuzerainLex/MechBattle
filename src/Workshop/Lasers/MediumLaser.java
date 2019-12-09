package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumLaser extends Laser {
    public MediumLaser() {
        super("MediumLaser", 4, 5, 6, 3, 5);
    }
    public void instanceMediumlLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлен средний лазер");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
