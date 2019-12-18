package Workshop.Guns;

import Messages.Message;
import Robots.Robot;
import Workshop.Weapon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public abstract class Gun extends Weapon {
    public boolean rightHand;
    public boolean leftHand;

    public Gun(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat, int cost) {
        super(name, cost, weight, damage, range, heat, ammunition, damageHeat);
    }



    public static void removeGuns(Robot bot) {
        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {
            Weapon nextGun = weaponIterator.next();
            if (nextGun instanceof Gun) {
                Gun gun = (Gun) nextGun;

                if (gun.leftHand) {
                    Iterator<Weapon> leftWeaponIterator = bot.leftHandWeapon.iterator();
                    while (leftWeaponIterator.hasNext()) {
                        Weapon nextLeftGun = leftWeaponIterator.next();
                        leftWeaponIterator.remove();
                    }
                } else if (gun.rightHand) {
                    Iterator<Weapon> rightWeaponIterator = bot.rightHandWeapon.iterator();
                    while (rightWeaponIterator.hasNext()) {
                        Weapon nextLeftGun = rightWeaponIterator.next();
                        rightWeaponIterator.remove();
                    }
                }
                if (gun instanceof MiniGun) {
                    bot.setWeaponWeight(bot.getWeaponWeight() - gun.weight);
                    weaponIterator.remove();
                } else if (gun instanceof MediumGun) {
                    bot.setWeaponWeight(bot.getWeaponWeight() - gun.weight);
                    weaponIterator.remove();
                } else if (gun instanceof BigGun) {
                    bot.setWeaponWeight(bot.getWeaponWeight() - gun.weight);
                    weaponIterator.remove();
                }
            }
        }
        bot.guns = 0;
        System.out.println("Все пушки деинсталированы");
    }

    public static void handChoose(Robot bot, String weapon) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1. Правая рука");
        System.out.println("2. Левая рука");
        System.out.println("3. Назад");

        while (true) {
            String inputHand = reader.readLine();
            switch (inputHand) {
                case ("1"):
                    if (bot.maxLeftHandSlots != bot.rightHandWeapon.size()) {
                        if (weapon.equals("miniGun")) {
                            MiniGun miniGR = new MiniGun(false, true);
                            miniGR.instanceGun(bot);
                        } else if (weapon.equals("mediumGun")) {
                            MediumGun mediumGunR = new MediumGun(false, true);
                            mediumGunR.instanceGun(bot);
                        } else if (weapon.equals("BigGun")) {
                            BigGun BigGunR = new BigGun(false, true);
                            BigGunR.instanceGun(bot);
                        }
                        return;
                    } else
                        System.out.println("Правая рука занята");
                    break;

                case ("2"):
                    if (bot.maxLeftHandSlots != bot.leftHandWeapon.size()) {
                        if (weapon.equals("miniGun")) {
                            MiniGun miniGL = new MiniGun(true, false);
                            miniGL.instanceGun(bot);
                        } else if (weapon.equals("mediumGun")) {
                            MediumGun mediumGunL = new MediumGun(true, false);
                            mediumGunL.instanceGun(bot);
                        } else if (weapon.equals("BigGun")) {
                            BigGun BigGunL = new BigGun(true, false);
                            BigGunL.instanceGun(bot);
                        }
                        return;
                    } else
                        System.out.println("Левая рука занята");
                    break;

                case ("3"):
                    return;

                default:
                    Message.wrongInput();
                    break;
            }

        }
    }
    public void instanceGun(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.guns < bot.gunSockets) {
            System.out.println("Установлена " + this.name);
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
        } else  Message.getWarning(bot);
    }
}

