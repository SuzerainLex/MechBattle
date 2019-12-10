package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumLaser extends Laser {
    public MediumLaser() {
        super("MediumLaser", 4, 5, 6, 3, 99, 7 );
    }
    public void instanceMediumlLaser(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.lasers < bot.laserSockets) {
            System.out.println("Установлен средний лазер");
            ++bot.lasers;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено лазеров " + bot.lasers);
            bot.weapons.add(this);

        }
        else   GameWorkShop.getWarning(bot);
    }
}
