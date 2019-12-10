package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigLaser extends Laser {
    public BigLaser() {
        super("BigLaser", 7, 8, 5, 8, 99, 10);
    }
    public void instanceBiglLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.lasers < bot.laserSockets) {
            System.out.println("Установлен большой лазер");
            bot.weapons.add(this);
            bot.lasers++;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено лазеров " + bot.lasers);
        }
        else   GameWorkShop.getWarning(bot);
    }
}
