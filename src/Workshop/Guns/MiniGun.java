package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MiniGun extends Gun {
        public MiniGun() {
        super("MiniGun", 1, 3, 7, 10, 10, 0);
        }
    public void instanceMiniGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.guns < bot.gunSockets) {
            System.out.println("Установлена маленькая пушка");
            ++bot.guns;
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
            System.out.println("Установлено пушек " + bot.guns);
            bot.weapons.add(this);
        }
        else   GameWorkShop.getWarning(bot);
    }
}
