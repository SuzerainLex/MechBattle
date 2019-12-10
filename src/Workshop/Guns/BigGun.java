package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigGun extends Gun{
    public BigGun() {
        super("BigGun", 5, 10, 3, 3, 4, 0);
    }
    public void instanceBigGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.guns < bot.gunSockets) {
            System.out.println("Установлена большая пушка");
            ++bot.guns;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено пушек " + bot.guns);
            bot.weapons.add(this);
        }
        else   GameWorkShop.getWarning(bot);
    }
}
