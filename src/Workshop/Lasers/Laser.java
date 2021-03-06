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
        bot.setLasers(0);
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
                    if (bot.getMaxRightHandSlots() != bot.rightHandWeapon.size()) {
                        if (weapon.equals("smalllaser")) {
                            SmallLaser sLR = new SmallLaser(false, true);

                            sLR.laserInstance(bot);
                        } else if (weapon.equals("mediumlaser")) {
                            MediumLaser mLR = new MediumLaser(false, true);
                            mLR.laserInstance(bot);
                        } else if (weapon.equals("biglaser")) {
                            BigLaser bLR = new BigLaser(false, true);
                            bLR.laserInstance(bot);
                        }
                        return;
                    } else
                        System.out.println("Правая рука занята");
                    break;

                case ("2"):
                    if (bot.getMaxLeftHandSlots() != bot.leftHandWeapon.size()) {
                        if (weapon.equals("smalllaser")) {
                            SmallLaser sLL = new SmallLaser(true, false);
                            sLL.laserInstance(bot);
                        } else if (weapon.equals("mediumlaser")) {
                            MediumLaser mLL = new MediumLaser(true, false);
                            mLL.laserInstance(bot);
                        } else if (weapon.equals("biglaser")) {
                            BigLaser bLL = new BigLaser(true, false);
                            bLL.laserInstance(bot);
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

    public void laserInstance(Robot bot) {
        if (bot.getMaxWeigth() > bot.getWeaponWeight() && bot.getLasers() < bot.getLaserSockets()) {
            System.out.println("Установлен " + this.name);
            bot.setLasers(bot.getLasers()+1);
            bot.setWeaponWeight(bot.getWeaponWeight() + this.weight);
            System.out.println("Вес вооружения " + bot.getWeaponWeight());
            System.out.println("Установлено лазеров " + bot.getLasers());
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

