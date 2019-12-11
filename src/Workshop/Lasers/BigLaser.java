package Workshop.Lasers;

import Robots.Robot;
import Workshop.GameWorkShop;

public class BigLaser extends Laser {
    public BigLaser(boolean leftHand, boolean rightHand) {
        super("BigLaser", 7, 8, 5, 8, 99, 10, 3);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }

    public void instanceBiglLaser(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.lasers < bot.laserSockets) {
            System.out.println("Установлен большой лазер");
            bot.weapons.add(this);
            bot.lasers++;
            bot.weaponWeight += this.weight;

            System.out.println("Установлено лазеров " + bot.lasers);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);

            if (bot.weaponWeight > bot.maxWeigth)
                GameWorkShop.getWarning(bot);

        } else GameWorkShop.getWarning(bot);
    }
}
