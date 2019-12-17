package Game;

import Interfaces.iFight;
import Interfaces.iMove;
import Messages.Message;
import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;
import Workshop.Rockets.Rockets;
import Workshop.Weapon;
import music.PlaySounds;
import music.Sounds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Turn {
    public static void makeTurn(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (bot1.numberOfMoves > 0 && Victory.victory != true) {
            Message.turnMessage(bot1, bot2);
            String input3 = reader.readLine();
            switch (input3) {
                //ХОДЬБА
                case ("1"):
                    Thread.sleep(500);
                    if (bot1.getLegsArmor() > 0)
                        bot1.go1(bot2);
                    else System.out.println("У тебя нет ног!!!");
                    break;
                //СТРЕЛЬБА
                case ("2"):
                    Thread.sleep(500);
                    System.out.println("Выберите оружие для стрельбы");

                    while (bot1.numberOfMoves > 0) {
                        Message.preFightMessage(bot1, bot2);
                        String inputWeapon = reader.readLine();
                        int i = 1;
                        switch (inputWeapon) {
                            //ЛЕВАЯ РУКА
                            case ("1"):
                                if (bot1.leftHandWeapon.size() != 0) {
                                    System.out.println("Выберите оружее");
                                    for (Weapon lW : bot1.leftHandWeapon) {
                                        System.out.println(i++ + " " + lW.name + "Осталось боеприпасов " + lW.ammunition);
                                    }
                                    i = 1;
                                    while (true) {
                                        String input6 = reader.readLine();

                                        switch (input6) {
                                            case ("1"):
                                                System.out.println(bot1.leftHandWeapon.get(0).name);
                                                bot1.distAttack(bot2, bot1.leftHandWeapon.get(0));
                                                break;
                                            case ("2"):
                                                if (bot1.leftHandWeapon.size() > 1) {
                                                    System.out.println(bot1.leftHandWeapon.get(1).name);
                                                    bot1.distAttack(bot2, bot1.leftHandWeapon.get(1));
                                                    break;
                                                } else break;
                                        }
                                        break;
                                    }
                                } else System.out.println("Нет оружия");
                                break;
                            //ПРАВАЯ РУКА
                            case ("2"):
                                if (bot1.rightHandWeapon.size() != 0) {
                                    System.out.println("Выберите оружее");
                                    for (Weapon rW : bot1.rightHandWeapon) {
                                        System.out.println(i++ + " " + rW.name + "Осталось боеприпасов " + rW.ammunition);
                                    }

                                    while (true) {
                                        String input7 = reader.readLine();

                                        switch (input7) {
                                            case ("1"):
                                                System.out.println(bot1.rightHandWeapon.get(0).name);
                                                bot1.distAttack(bot2, bot1.rightHandWeapon.get(0));
                                                break;
                                            case ("2"):
                                                if (bot1.rightHandWeapon.size() > 1) {
                                                    System.out.println(bot1.rightHandWeapon.get(1).name);
                                                    bot1.distAttack(bot2, bot1.rightHandWeapon.get(1));
                                                    break;
                                                } else break;
                                        }
                                        break;
                                    }
                                } else System.out.println("Нет оружия");
                                break;

                            //КОРПУС
                            case ("3"):
                                if (bot1.allRockets.size() != 0) {
                                    for (Weapon rocket : bot1.allRockets) {
                                        System.out.println("Выберите оружее");
                                        System.out.println(i++ + " " + rocket.name + "Осталось боеприпасов " + rocket.ammunition);
                                    }
                                    i = 1;
                                    while (true) {
                                        String input8 = reader.readLine();

                                        switch (input8) {
                                            case ("1"):

                                                System.out.println(bot1.allRockets.get(0).name);
                                                bot1.distAttack(bot2, bot1.allRockets.get(0));
                                                 break;
                                            case ("2"):
                                                if (bot1.allRockets.size() > 1) {
                                                    System.out.println(bot1.allRockets.get(0).name);
                                                    bot1.distAttack(bot2, bot1.allRockets.get(1));
                                                    break;
                                                } else break;
                                        }
                                        break;
                                    }
                                } else System.out.println("Нет оружия");
                                break;
                            //НАЗАД
                            case ("4"):
                                break;
                        }
                        break;
                    }
                    break;
                //ПРОПУСК ХОДА
                case ("3"):
                    Thread.sleep(500);
                    System.out.println("Пропуск хода");
                    passTurn(bot1);
                    break;

                default:
                    Message.wrongInput();
                    break;

            }
        }
    }

    public static void passTurn(Robot bot1) {
        bot1.setHeatLev(bot1.getHeatLev() - 10);
        bot1.numberOfMoves = 0;

    }


}
