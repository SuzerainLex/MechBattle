package Robots;

import Field.FieldOfBattle;
import Game.Turn;
import Interfaces.iFight;
import Interfaces.iMove;
import Messages.Message;
import Workshop.Weapon;
import music.PlaySounds;
import music.Sounds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Robot implements iFight, iMove {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final int maxWeigth, radiator, initiative;
    private int heatLev = 0, headArmor, bodyArmor, legsArmor, leftHandArmor, rightHandArmor, weaponWeight, numberOfMoves, maxNumberOfMoves;
    private int coordinatX, coordinatY, guns = 0, rockets = 0, lasers = 0, rocketSockets, laserSockets, gunSockets, meleemight, maxLeftHandSlots, maxRightHandSlots;
    public final String name;
    String playerName;
    private boolean firstTurn = false;
    public List<Weapon> weapons = new ArrayList<>();
    public List<Weapon> leftHandWeapon = new ArrayList<>();
    public List<Weapon> rightHandWeapon = new ArrayList<>();
    public List<Weapon> allRockets = new ArrayList<>();


    public Robot(String name, int meleemight, int initiativa, int armor, int radiator, int maxWeght, int gunSockets, int rocketSockets, int laserSockets, int maxNumberOfMoves, int maxLeftHandSlots, int maxRightHandSlots) {
        this.name = name;
        this.meleemight = meleemight;
        this.maxNumberOfMoves = maxNumberOfMoves;
        this.numberOfMoves = maxNumberOfMoves;
        this.initiative = initiativa;
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

    public int getCoordinatX() {
        return coordinatX;
    }

    public void setCoordinatX(int coordinatX) {
        this.coordinatX = coordinatX;
    }

    public int getCoordinatY() {
        return coordinatY;
    }

    public void setCoordinatY(int coordinatY) {
        this.coordinatY = coordinatY;
    }

    public boolean getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    private void setHeatLev(int heatLev) {
        this.heatLev = heatLev;
    }

    public int getInitiative() {
        return initiative;
    }

    private int getMaxNumberOfMoves() {
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

    private String getPlayerName() {
        return playerName;
    }

    public int getMeleemight() {
        return meleemight;
    }

    public int getGuns() {
        return guns;
    }

    public void setGuns(int guns) {
        this.guns = guns;
    }

    public int getRockets() {
        return rockets;
    }

    public void setRockets(int rockets) {
        this.rockets = rockets;
    }

    public int getLasers() {
        return lasers;
    }

    public void setLasers(int lasers) {
        this.lasers = lasers;
    }

    public int getRocketSockets() {
        return rocketSockets;
    }

    public int getLaserSockets() {
        return laserSockets;
    }


    public int getGunSockets() {
        return gunSockets;
    }

    public int getMaxLeftHandSlots() {
        return maxLeftHandSlots;
    }

    public int getMaxRightHandSlots() {
        return maxRightHandSlots;
    }

    //ГОЛОВА
    public int getHeadArmor() {
        if (headArmor <= 0)
            return 0;
        else return headArmor;
    }

    //ТЕЛО
    public int getBodyArmor() {
        if (bodyArmor <= 0)
            return 0;
        else return bodyArmor;
    }

    //ЛЕВАЯ РУКА
    public int getLeftHandArmor() {
        if (leftHandArmor <= 0)
            return 0;
        else return leftHandArmor;
    }

    //ПРАВАЯ РУКА
    public int getRightHandArmor() {
        if (rightHandArmor <= 0)
            return 0;
        else return rightHandArmor;
    }

    //НОГИ
    public int getLegsArmor() {
        if (legsArmor <= 0)
            return 0;
        else return legsArmor;
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
                            Sounds.chooseSound(weapon);
                            System.out.println("Попадание в голову!");
                            weapon.ammunition--;
                            this.numberOfMoves -= weapon.cost;
                            bot.headArmor -= weapon.damage;
                            bot.heatLev += weapon.damageHeat;
                            this.heatLev += weapon.heat;
                            Message.getDistAttackMessage(this, bot, weapon);

                        } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                            Sounds.chooseSound(weapon);
                            System.out.println("Попадание в корпус!");
                            --weapon.ammunition;
                            this.numberOfMoves -= weapon.cost;
                            bot.bodyArmor -= weapon.damage;
                            bot.heatLev += weapon.damageHeat;
                            this.heatLev += weapon.heat;
                            Message.getDistAttackMessage(this, bot, weapon);

                        } else if ((rand + 1) == input4 && bot.legsArmor > 0) {
                            Sounds.chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в ноги!");
                            this.numberOfMoves -= weapon.cost;
                            bot.legsArmor -= weapon.damage;
                            bot.heatLev += weapon.damageHeat;
                            this.heatLev += weapon.heat;
                            if (bot.legsArmor <= 0) {
                                PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                                System.out.println("Ноги противника уничтожены");
                            }

                            Message.getDistAttackMessage(this, bot, weapon);

                        } else if ((rand + 2) == input4 && bot.leftHandArmor > 0) {
                            Sounds.chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в левую руку!");
                            this.numberOfMoves -= weapon.cost;
                            bot.leftHandArmor -= weapon.damage;
                            bot.heatLev += weapon.damageHeat;
                            this.heatLev += weapon.heat;
                            if (bot.leftHandArmor <= 0) {
                                PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
                                System.out.println("Левая рука противника уничтожена");

                                Iterator<Weapon> leftWeaponIterator = bot.leftHandWeapon.iterator();
                                while (leftWeaponIterator.hasNext()) {
                                    Weapon nextLeftGun = leftWeaponIterator.next();
                                    leftWeaponIterator.remove();
                                }
                            }
                            Message.getDistAttackMessage(this, bot, weapon);

                        } else if ((rand - 2) == input4 && bot.rightHandArmor > 0) {
                            Sounds.chooseSound(weapon);
                            weapon.ammunition--;
                            System.out.println("Попадание в правую руку!");
                            this.numberOfMoves -= weapon.cost;
                            bot.rightHandArmor -= weapon.damage;
                            bot.heatLev += weapon.damageHeat;
                            this.heatLev += weapon.heat;
                            if (bot.rightHandArmor <= 0) {
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
                            Sounds.chooseSound(weapon);
                            Thread.sleep(200);
                            System.out.println("Промазал!!!");
                            weapon.ammunition--;
                            this.numberOfMoves -= weapon.cost;
                            this.heatLev += weapon.heat;
                            Thread.sleep(200);
                        }
                        if (bot.headArmor <= 0 || bot.bodyArmor <= 0) {
                            Message.victoryMessage(this);
                        }

                    } else System.out.println("Не достаю до цели");
                } else System.out.println("Мех перегрелся, невозможно стрелять");
            } else System.out.println("Не хватает очков действий");
        } else System.out.println("Нет боеприпасов!");
    }

    public void go1(Robot bot) throws IOException, InterruptedException {
        while (this.numberOfMoves > 0) {
            Message.goMessage(this, bot);
            Message.preMoveMessage(this, bot);
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
                PlaySounds pS = new PlaySounds(Sounds.MELLEESOUND);
                System.out.println("ТАРАН");
                this.bodyArmor -= meleemight / 2;
                bot.bodyArmor -= meleemight;
                System.out.println("Нанесено " + meleemight + " урона врагу и " + meleemight / 2 + " себе");
                if (input.equals("w"))
                    this.coordinatY = bot.coordinatY-1;
                if (input.equals("s"))
                this.coordinatY = bot.coordinatY+1;
                if (input.equals("a"))
                    this.coordinatX = this.coordinatX++;
                if (input.equals("d"))
                    this.coordinatX = this.coordinatX--;
            }
            if (bot.bodyArmor <= 0) {
                Message.victoryMessage(this);
                return;
            }
            if (input.equals("1")) {
                return;
            }
           Message.goMessage(this, bot);
        }
    }

    public void playerTurn(Robot bot) throws IOException, InterruptedException {
        System.out.println();
        System.out.println(this.getPlayerName());
        Turn.makeTurn(this, bot);
        if (bot.getNumberOfMoves() == 0)
            System.out.println("Очки ходов закончились");
        this.setHeatLev(this.getHeatLev() - 5);
        bot.setNumberOfMoves(bot.getMaxNumberOfMoves());
    }
}
