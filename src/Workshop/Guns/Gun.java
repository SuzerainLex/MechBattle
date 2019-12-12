package Workshop.Guns;

import Game.StartBattle;
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

    public static void getMessageGuns(Robot bot) {
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Маленькая пушка | Урон: 4 | Дальность: 7 | Боезапас: 10  | Очки хода: 1  | Перегрев: 5 | Вес: 1 |");
        System.out.println("2. Средняя пушка  | Урон: 7 | Дальность: 5 | Боезапас: 6  | Очки хода: 2  | Перегрев: 6 | Вес: 3 |");
        System.out.println("3. Большая пушка  | Урон: 12 | Дальность: 3 | Боезапас: 4 | Очки хода: 3  | Перегрев: 8 | Вес: 6 |");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все пушки");

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
                        //    bot.leftHandSlots--;
                        leftWeaponIterator.remove();
                    }
                } else if (gun.rightHand) {
                    Iterator<Weapon> rightWeaponIterator = bot.rightHandWeapon.iterator();
                    while (rightWeaponIterator.hasNext()) {
                        //  bot.rightHandSlots--;
                        Weapon nextLeftGun = rightWeaponIterator.next();
                        rightWeaponIterator.remove();
                    }
                }

                if (gun instanceof MiniGun) {
                    bot.weaponWeight -= gun.weight;
                    weaponIterator.remove();
                } else if (gun instanceof MediumGun) {
                    bot.weaponWeight -= gun.weight;
                    weaponIterator.remove();
                } else if (gun instanceof BigGun) {

                    bot.weaponWeight -= gun.weight;
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
                    //  if(bot.maxRightHandSlots != bot.rightHandSlots) {
                    if (bot.maxLeftHandSlots != bot.rightHandWeapon.size()) {
                        // bot.rightHandSlots++;
                        if (weapon.equals("miniGun")) {
                            MiniGun miniGR = new MiniGun(false, true);
                            miniGR.instanceMiniGun(bot);
                        } else if (weapon.equals("mediumGun")) {
                            MediumGun mediumGunR = new MediumGun(false, true);
                            mediumGunR.instanceMediumGun(bot);
                        } else if (weapon.equals("BigGun")) {
                            BigGun BigGunR = new BigGun(false, true);
                            BigGunR.instanceBigGun(bot);
                        }
                        return;
                    } else
                        System.out.println("Правая рука занята");
                    break;

                case ("2"):
                       /* if(bot.maxLeftHandSlots != bot.leftHandSlots) {
                            bot.leftHandSlots++;*/
                    if (bot.maxLeftHandSlots != bot.leftHandWeapon.size()) {
                        if (weapon.equals("miniGun")) {
                            MiniGun miniGL = new MiniGun(true, false);
                            miniGL.instanceMiniGun(bot);
                        } else if (weapon.equals("mediumGun")) {
                            MediumGun mediumGunL = new MediumGun(true, false);
                            mediumGunL.instanceMediumGun(bot);
                        } else if (weapon.equals("BigGun")) {
                            BigGun BigGunL = new BigGun(true, false);
                            BigGunL.instanceBigGun(bot);
                        }
                        return;
                    } else
                        System.out.println("Левая рука занята");
                    break;

                case ("3"):
                    return;

                default:
                    StartBattle.wrongInput();
                    break;
            }

        }
    }
}

