package Workshop.Lasers;

import Messages.Message;
import Robots.Robot;
import Workshop.Weapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


public abstract class Laser extends Weapon {
    public boolean rightHand;
    public boolean leftHand;

    public Laser(String name, int weight, int damage, int range, int heat, int ammunition, int damageHeat, int cost) {
        super(name, cost, weight, damage, range, heat, ammunition, damageHeat);
    }

    public static void getMessageLasers(Robot bot) {
        System.out.println("Вес вооружения " + bot.getWeaponWeight());
        System.out.println("1. Малый лазер   | Урон: 3 | Дальность: 5 | Боезапас: 99  | Очки хода: 1  | Перегрев: 3 | Вес: 1 | Тепловой урон: 5 | ");
        System.out.println("2. Средний лазер | Урон: 6 | Дальность: 5 | Боезапас: 99  | Очки хода: 2  | Перегрев: 6 | Вес: 3 | Тепловой урон: 7 | ");
        System.out.println("3. Большой лазер | Урон:10 | Дальность: 4 | Боезапас: 99  | Очки хода: 3  | Перегрев: 8 | Вес: 6 | Тепловой урон:10 | ");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все лазеры");
    }

    public static void removeLasers(Robot bot) {
        Iterator<Weapon> weaponIterator = bot.weapons.iterator();
        while (weaponIterator.hasNext()) {

            Weapon nextLaser = weaponIterator.next();
            if (nextLaser instanceof Laser) {
                Laser laser = (Laser) nextLaser;
                if (laser.leftHand) {
                    Iterator<Weapon> leftWeaponIterator = bot.leftHandWeapon.iterator();
                    while (leftWeaponIterator.hasNext()) {
                        Weapon nextLeftLaser = leftWeaponIterator.next();
                        leftWeaponIterator.remove();
                    }
                } else if (laser.rightHand) {
                    Iterator<Weapon> rightWeaponIterator = bot.rightHandWeapon.iterator();
                    while (rightWeaponIterator.hasNext()) {
                        Weapon nextRightLaser = rightWeaponIterator.next();
                        rightWeaponIterator.remove();
                    }
                }

                if (laser instanceof SmallLaser) {

                    bot.setWeaponWeight(bot.getWeaponWeight() - laser.weight);
                    weaponIterator.remove();
                }
                if (laser instanceof MediumLaser) {

                    bot.setWeaponWeight(bot.getWeaponWeight() - laser.weight);
                    weaponIterator.remove();
                }
                if (laser instanceof BigLaser) {
                    bot.setWeaponWeight(bot.getWeaponWeight() - laser.weight);
                    weaponIterator.remove();
                }
            }
        }
        bot.lasers = 0;
        System.out.println("Все лазеры деинсталированы");
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
                    if (bot.maxRightHandSlots != bot.rightHandWeapon.size()) {
                        if (weapon.equals("smalllaser")) {
                            SmallLaser sLR = new SmallLaser(false, true);

                            sLR.instanceSmallLaser(bot);
                        } else if (weapon.equals("mediumlaser")) {
                            MediumLaser mLR = new MediumLaser(false, true);
                            mLR.instanceMediumlLaser(bot);
                        } else if (weapon.equals("biglaser")) {
                            BigLaser bLR = new BigLaser(false, true);
                            bLR.instanceBiglLaser(bot);
                        }
                        return;
                    } else
                        System.out.println("Правая рука занята");
                    break;

                case ("2"):
                    if (bot.maxLeftHandSlots != bot.leftHandWeapon.size()) {
                        if (weapon.equals("smalllaser")) {
                            SmallLaser sLL = new SmallLaser(true, false);
                            sLL.instanceSmallLaser(bot);
                        } else if (weapon.equals("mediumlaser")) {
                            MediumLaser mLL = new MediumLaser(true, false);
                            mLL.instanceMediumlLaser(bot);
                        } else if (weapon.equals("biglaser")) {
                            BigLaser bLL = new BigLaser(true, false);
                            bLL.instanceBiglLaser(bot);
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
}

