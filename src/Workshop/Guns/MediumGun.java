package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class MediumGun extends Gun {
    public MediumGun(boolean leftHand, boolean rightHand) {
        super("MediumGun", 3, 6, 5, 6, 6, 0, 2);
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public void instanceMediumGun(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.guns < bot.gunSockets) {
            System.out.println("Установлена средняя пушка");
            bot.guns++;
            bot.weaponWeight += this.weight;
            System.out.println("Установлено пушек " + bot.guns);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);
            bot.weapons.add(this);

        } else GameWorkShop.getWarning(bot);
    }
}
