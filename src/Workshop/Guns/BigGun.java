package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigGun extends Gun{
    public BigGun() {
        super("BigGun", 5, 10, 3, 3, 8);
    }
    public void instanceBigGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлена большая пушка");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
