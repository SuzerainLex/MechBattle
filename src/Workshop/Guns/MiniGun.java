package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MiniGun extends Gun {
        public MiniGun() {
        super("MiniGun", 1, 3, 7, 10, 3);
    }
    public void instanceMiniGun(Robot bot) {
        if (bot.maxWeigth >= bot.weaponWeight && bot.laserSocket && bot.lasers < 2) {
            System.out.println("Установлена маленькая пушка");
            bot.weapons.add(this);
            bot.weaponWeight += this.weight;
            System.out.println("Вес вооружения " + bot.weaponWeight);
        }
        else   GameWorkShop.getWarning(bot, this);
    }
}
