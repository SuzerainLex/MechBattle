package Workshop.Guns;

import Messages.Message;
import Robots.Robot;
import Workshop.GameWorkShop;

public class MiniGun extends Gun {
    public MiniGun(boolean leftHand, boolean rightHand) {
        super("MiniGun", 1, 4, 7, 5, 10, 0, 1);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }

    public void instanceMiniGun(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.guns < bot.gunSockets) {
            System.out.println("Установлена маленькая пушка");
            bot.guns++;
            bot.setWeaponWeight(bot.getWeaponWeight() + this.weight);
            System.out.println("Установлено пушек " + bot.guns);
            if (this.leftHand) {
                bot.leftHandWeapon.add(this);
            } else {
                bot.rightHandWeapon.add(this);
            }
            bot.weapons.add(this);
            //ПОСТ ПРОВЕРКА
            if (bot.getWeaponWeight() > bot.getMaxWeigth())
                Message.getWarning(bot);
        } else Message.getWarning(bot);
    }
}
