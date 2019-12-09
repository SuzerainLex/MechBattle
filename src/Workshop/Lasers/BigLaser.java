package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigLaser extends Laser {
    public BigLaser() {
        super("BigLaser", 7, 8, 5, 8, 8);
    }
    public void instanceBiglLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлен большой лазер");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
