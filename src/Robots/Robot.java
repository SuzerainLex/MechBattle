package Robots;


import Field.FieldOfBattle;
import Game.Victory;
import Interfaces.iFight;
import Interfaces.iMove;
import Messages.Message;
import Workshop.Guns.BigGun;
import Workshop.Guns.MediumGun;
import Workshop.Guns.MiniGun;
import Workshop.Lasers.BigLaser;
import Workshop.Lasers.MediumLaser;
import Workshop.Lasers.SmallLaser;
import Workshop.Rockets.LargeDistanceRockets;
import Workshop.Rockets.SmallDistanceRockets;
import Workshop.Weapon;
import music.PlaySounds;
import music.Sounds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Robot implements iFight, iMove {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final int maxWeigth, radiator, initiativa;
    private int heatLev = 0, headArmor, bodyArmor, legsArmor, leftHandArmor, rightHandArmor, weaponWeight;
    public int coordinatX, coordinatY, guns = 0, rockets = 0, lasers = 0, rocketSockets, laserSockets, gunSockets, maxNumberOfMoves, numberOfMoves, meleemight, maxLeftHandSlots, maxRightHandSlots;
    public final String name;
    public boolean firstTurn = false;
    public List<Weapon> weapons = new ArrayList<>();
    public List<Weapon> leftHandWeapon = new ArrayList<>();
    public List<Weapon> rightHandWeapon = new ArrayList<>();
    public List<Weapon> allRockets = new ArrayList<>();


    public Robot(String name, int meleemight, int initiativa, int armor, int radiator, int maxWeght, int gunSockets, int rocketSockets, int laserSockets, int maxNumberOfMoves, int maxLeftHandSlots, int maxRightHandSlots) {
        this.name = name;
        this.meleemight = meleemight; //ЕЩЕ НЕ СДЕЛАЛ!
        this.maxNumberOfMoves = maxNumberOfMoves;
        this.numberOfMoves = maxNumberOfMoves;
        this.initiativa = initiativa;
        this.radiator = radiator;
        this.maxWeigth = maxWeght;
        this.laserSockets = laserSockets;
        this.gunSockets = gunSockets;
        this.rocketSockets = rocketSockets;
        this.maxLeftHandSlots = maxLeftHandSlots;
        this.maxRightHandSlots = maxRightHandSlots;
        this.headArmor = armor / 5;
        this.bodyArmor = armor / 2;
        this.legsArmor = armor / 3;
        this.leftHandArmor = armor / 4;
        this.rightHandArmor = armor / 4;
    }

    public int getHeatLev() {
        if (heatLev <= 0)
            return 0;
        else return heatLev;
    }

    public void setHeatLev(int heatLev) {
        this.heatLev = heatLev;
    }

    public int getInitiativa() {
        return initiativa;
    }

    public int getMaxNumberOfMoves() {
        return maxNumberOfMoves;
    }

    public int getRadiator() {
        return radiator;
    }

    public int getMaxWeigth() {
        return maxWeigth;
    }

    public int getWeaponWeight() {
        return weaponWeight;
    }

    public void setWeaponWeight(int weaponWeight) {
        this.weaponWeight = weaponWeight;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    //ГОЛОВА
    public int getHeadArmor() {
        if (headArmor <= 0)
            return 0;
        else return headArmor;
    }

    public void setHeadArmor(int headArmor) {
        this.headArmor = headArmor;
    }

    //ТЕЛО
    public int getBodyArmor() {
        if (bodyArmor <= 0)
            return 0;
        else return bodyArmor;
    }

    public void setBodyArmor(int bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    //ЛЕВАЯ РУКА
    public int getLeftHandArmor() {
        if (leftHandArmor <= 0)
            return 0;
        else return leftHandArmor;
    }

    public void setLeftHandArmor(int leftHandArmor) {
        this.leftHandArmor = leftHandArmor;
    }

    //ПРАВАЯ РУКА
    public int getRightHandArmor() {
        if (rightHandArmor <= 0)
            return 0;
        else return rightHandArmor;
    }

    public void setRightHandArmor(int rightHandArmor) {
        this.rightHandArmor = rightHandArmor;
    }

    //НОГИ
    public int getLegsArmor() {
        if (legsArmor <= 0)
            return 0;
        else return legsArmor;
    }

    public void setLegsArmor(int legsArmor) {
        this.legsArmor = legsArmor;
    }


    public void distAttack(Robot bot, Weapon weapon) throws IOException, InterruptedException {
        if (weapon.ammunition != 0) {
            if ((this.numberOfMoves - weapon.cost) >= 0) {
                if (this.getHeatLev() <= this.getRadiator()) {
                    if ((bot.coordinatX <= this.coordinatX + weapon.range & bot.coordinatX >= this.coordinatX - weapon.range) && (bot.coordinatY <= this.coordinatY + weapon.range & bot.coordinatY >= this.coordinatY - weapon.range)) {
                        int input4 = -1;
                        int rand = (int) (Math.random() * 10);

                        Thread.sleep(200);
                        System.out.println("Введите число от 0 до 10: ");
                        while (input4 < 0 || input4 > 10) {
                            try {
                                input4 = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                Message.wrongInput();
                            }
                        }
                        if (rand == input4) {
                            chooseSound(weapon);
                            System.out.println("Попадание в голову!");
                            weapon.ammunition--;
                            this.setNumberOfMoves(this.getNumberOfMoves() - weapon.cost);
                            bot.setHeadArmor(bot.getHeadArmor() - weapon.damage);
                            bot.setHeatLev(bot.getHeatLev() + weapon.damageHeat);
                            this.setHeatLev(this.getHeatLev() + weapon.heat);

                            Message.getDistAttackMessage(this, bot, weapon);
                        } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                            chooseSound(weapon);
                            System.out.println("Попадание в корпус!");
                            --weapon.ammunition;
                            this.numberOfMoves -= weapon.cost;
                            bot.setBodyArmor(bot.getBodyArmor() - weapon.damage);
                            bot.setHeatLev(bot.getHeatLev() + weapon.damageHeat);
                            this.setHeatLev(this.getHeatLev() + weapon.heat);

                            Message.getDistAttackMessage(this, bot, weapon);
                        } else if ((rand + 1) == input4 && bot.getLegsArmor() > 0) {
                            chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в ноги!");
                            this.numberOfMoves -= weapon.cost;
                            bot.setLegsArmor(bot.getLegsArmor() - weapon.damage);
                            bot.setHeatLev(bot.getHeatLev() + weapon.damageHeat);
                            this.setHeatLev(this.getHeatLev() + weapon.heat);
                            if (bot.getLegsArmor() <= 0) {
                                PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                                System.out.println("Ноги противника уничтожены");

                            }

                            Message.getDistAttackMessage(this, bot, weapon);

                        } else if ((rand + 2) == input4 && bot.getLeftHandArmor() > 0) {
                            chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в левую руку!");
                            this.numberOfMoves -= weapon.cost;
                            bot.setLeftHandArmor(bot.getLeftHandArmor() - weapon.damage);
                            bot.setHeatLev(bot.getHeatLev() + weapon.damageHeat);
                            this.setHeatLev(this.getHeatLev() + weapon.heat);
                            if (bot.getLeftHandArmor() <= 0) {
                                PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                                System.out.println("Левая рука противника уничтожена");

                                Iterator<Weapon> leftWeaponIterator = bot.leftHandWeapon.iterator();
                                while (leftWeaponIterator.hasNext()) {
                                    Weapon nextLeftGun = leftWeaponIterator.next();
                                    leftWeaponIterator.remove();
                                }
                            }
                            Message.getDistAttackMessage(this, bot, weapon);
                        } else if ((rand - 2) == input4 && bot.getRightHandArmor() > 0) {
                            chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в правую руку!");
                            this.numberOfMoves -= weapon.cost;
                            this.setRightHandArmor(bot.getRightHandArmor() - weapon.damage);

                            bot.setHeatLev(bot.getHeatLev() + weapon.damageHeat);
                            this.setHeatLev(this.getHeatLev() + weapon.heat);
                            if (bot.getRightHandArmor() <= 0) {
                                PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                                System.out.println("Правая рука противника уничтожена");
                                Iterator<Weapon> rightWeaponIterator = bot.rightHandWeapon.iterator();
                                while (rightWeaponIterator.hasNext()) {
                                    Weapon nextLeftGun = rightWeaponIterator.next();
                                    rightWeaponIterator.remove();
                                }
                            }
                            Message.getDistAttackMessage(this, bot, weapon);

                        } else {
                            chooseSound(weapon);
                            Thread.sleep(200);
                            System.out.println("Промазал!!!");
                            weapon.ammunition--;
                            this.numberOfMoves -= weapon.cost;
                            this.setHeatLev(this.getHeatLev() + weapon.heat);
                            Thread.sleep(200);
                        }
                        if (bot.getHeadArmor() <= 0 || bot.getBodyArmor() <= 0) {
                            Thread.sleep(500);
                            PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                            System.out.println("Враг уничожен!");
                            Thread.sleep(500);
                            System.out.println(this.name + " победил!!!");
                            Victory.victory = true;
                        }

                    } else System.out.println("Не достаю до цели");
                } else System.out.println("Мех перегрелся, невозможно стрелять");
            } else System.out.println("Не хватает очков действий");
        } else System.out.println("Нет боеприпасов!");
    }

    public void go1(Robot bot) throws IOException {
        while (this.numberOfMoves > 0) {
            Message.goMessage(this, bot);
            String input = reader.readLine();
            if (input.equals("w") && this.coordinatY < FieldOfBattle.SIDEY) {
                System.out.println("вперед");
                this.coordinatY++;
                this.numberOfMoves--;
                PlaySounds pS = new PlaySounds(Sounds.STEPSOUND);
            } else if (input.equals("s") && this.coordinatY > 0) {
                System.out.println("назад");
                this.coordinatY--;
                this.numberOfMoves--;
                PlaySounds pS = new PlaySounds(Sounds.STEPSOUND);

            } else if (input.equals("a") && this.coordinatX > 0) {
                System.out.println("влево");
                this.coordinatX--;
                this.numberOfMoves--;
                PlaySounds pS = new PlaySounds(Sounds.STEPSOUND);
            } else if (input.equals("d") && this.coordinatX < FieldOfBattle.SIDEX) {
                System.out.println("враво");
                this.coordinatX++;
                this.numberOfMoves--;
                PlaySounds pS = new PlaySounds(Sounds.STEPSOUND);
            }

            if (this.coordinatX == bot.coordinatX && this.coordinatY == bot.coordinatY) {
                System.out.println("ТАРАН");
            }
            if (input.equals("1")) {
                return;
            }
        }
        Message.goMessage(this, bot);
    }

    private void chooseSound(Weapon weapon) throws InterruptedException {
        if (weapon instanceof MiniGun) {
            PlaySounds pM = new PlaySounds(Sounds.MINIGUNSOUND);
            explosiveSound(400);
        } else if (weapon instanceof MediumGun) {
            PlaySounds pM = new PlaySounds(Sounds.MEDIUMGUNSOUND);
            explosiveSound(1000);
        } else if (weapon instanceof BigGun) {
            PlaySounds pM = new PlaySounds(Sounds.BIGGUNSOUND);
            explosiveSound(2200);
        } else if (weapon instanceof SmallDistanceRockets || weapon instanceof LargeDistanceRockets) {
            PlaySounds pM = new PlaySounds(Sounds.ROCKETSOUND);
            explosiveSound(3000);
        } else if (weapon instanceof SmallLaser) {
            PlaySounds pM = new PlaySounds(Sounds.SMALLLASERSOUND);
            explosiveSound(500);
        } else if (weapon instanceof MediumLaser) {
            PlaySounds pM = new PlaySounds(Sounds.MEDIUMLASERSOUND);
            explosiveSound(1000);
        } else if (weapon instanceof BigLaser) {
            PlaySounds pM = new PlaySounds(Sounds.BIGLASERSOUND);
            explosiveSound(5200);
        }

    }
    private void explosiveSound(int number) throws InterruptedException {
        Thread.sleep(number);
        int rand = (int)(Math.random() * 10);
        if(rand > 5) {
            PlaySounds pS = new PlaySounds(Sounds.HITSOUND1);
        }
        else {
            PlaySounds pS = new PlaySounds(Sounds.HITSOUND2);
        }
    }
}
