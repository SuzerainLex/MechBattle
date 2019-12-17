package Workshop.Lasers;

import Messages.Message;
import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumLaser extends Laser {
    public MediumLaser(boolean leftHand, boolean rightHand) {
        super("MediumLaser", 3, 6, 6, 3, 99, 7, 2);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }

    public void instanceMediumlLaser(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.lasers < bot.laserSockets) {
            System.out.println("Установлен средний лазер");
            bot.setWeaponWeight(bot.getWeaponWeight() + this.weight);
            System.out.println("Вес вооружения " + bot.getWeaponWeight());
            System.out.println("Установлено лазеров " + bot.lasers);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);
            bot.weapons.add(this);
            bot.lasers++;
            //ПОСТ ПРОВЕРКА
            if (bot.getWeaponWeight() > bot.getMaxWeigth())
                Message.getWarning(bot);

        } else Message.getWarning(bot);
    }
}
