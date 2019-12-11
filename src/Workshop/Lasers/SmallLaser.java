package Workshop.Lasers;

import Game.StartBattle;
import Robots.Robot;
import Workshop.GameWorkShop;
import Workshop.Guns.MiniGun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SmallLaser extends Laser {
    public SmallLaser(boolean leftHand, boolean rightHand) {
        super("SmallLaser", 2, 3, 4, 3, 99, 5, 1);
        this.rightHand = rightHand; this.leftHand = leftHand;
    }

    public void instanceSmallLaser(Robot bot) {
        if (bot.maxWeigth > bot.weaponWeight && bot.lasers < bot.laserSockets) {
            System.out.println("Малый лазер");

            bot.weaponWeight += this.weight;
            System.out.println("Установлено лазеров " + bot.lasers);
            if (this.leftHand)
                bot.leftHandWeapon.add(this);
            else
                bot.rightHandWeapon.add(this);
            bot.weapons.add(this);
            bot.lasers++;
            if(bot.weaponWeight > bot.maxWeigth)
                GameWorkShop.getWarning(bot);
        }

         else  GameWorkShop.getWarning(bot);
    }

}
