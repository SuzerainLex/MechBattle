package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumGun extends Gun{
    public MediumGun() {
        super("MediumGun", 3, 6, 5, 6, 5);
    }
    public void instanceMediumGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлена средняя пушка");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
