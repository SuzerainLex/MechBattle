package Workshop.Guns;

import Messages.Message;
import Robots.Robot;

public class MediumGun extends Gun {
    public MediumGun(boolean leftHand, boolean rightHand) {
        super("MediumGun", 3, 7, 5, 6, 6, 0, 2);
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public void instanceMediumGun(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.guns < bot.gunSockets) {
            System.out.println("Установлена средняя пушка");
            bot.guns++;
            bot.setWeaponWeight(bot.getWeaponWeight()+ this.weight);
            System.out.println("Установлено пушек " + bot.guns);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);
            bot.weapons.add(this);
            //ПОСТ ПРОВЕРКА
            if (bot.getWeaponWeight() > bot.getMaxWeigth())
                Message.getWarning(bot);
        } else Message.getWarning(bot);
    }
}
