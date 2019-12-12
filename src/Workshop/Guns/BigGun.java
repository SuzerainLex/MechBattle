package Workshop.Guns;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigGun extends Gun{
    public BigGun(boolean leftHand, boolean rightHand) {
        super("BigGun", 6, 12, 3, 8, 4, 0 , 3);
        this.leftHand = leftHand; this.rightHand = rightHand;
    }
    public void instanceBigGun(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.guns < bot.gunSockets) {
            System.out.println("Установлена большая пушка");
            bot.guns++;
            bot.weaponWeight += this.weight;
             System.out.println("Установлено пушек " + bot.guns);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);
          bot.weapons.add(this);

        }
        else  GameWorkShop.getWarning(bot);
    }
}
