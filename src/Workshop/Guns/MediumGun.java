package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumGun extends Gun{
    public MediumGun() {
        super("MediumGun", 3, 6, 5, 6, 6, 0);
    }
    public void instanceMediumGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.guns < bot.gunSockets) {
            System.out.println("Установлена средняя пушка");
            ++bot.guns;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено пушек " + bot.guns);
            bot.weapons.add(this);
        }
        else   GameWorkShop.getWarning(bot);
    }
}
