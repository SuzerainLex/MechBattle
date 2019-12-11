package Game;

import Interfaces.iFight;
import Interfaces.iMove;
import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;
import Workshop.Rockets.Rockets;
import Workshop.Weapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Turn {
    public static void makeTurn(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (bot1.numberOfMoves > 0) {
            turnMessage(bot1, bot2);
            String input3 = reader.readLine();
            switch (input3) {
                //ХОДЬБА
                case ("1"):
                    Thread.sleep(500);
                    iMove.go1(bot1, bot2);
                    break;
                //СТРЕЛЬБА
                case ("2"):
                    Thread.sleep(500);
                    System.out.println("Выберите оружие для стрельбы");


                   /* for (Weapon w: bot1.weapons) {
                        if (w instanceof Gun) {
                            if (((Gun) w).leftHand) {
                                System.out.print(" | " + w.name + " в левой руке | ");
                            } else if (((Gun) w).rightHand) {
                                System.out.print(" | " + w.name + " в правой руке | ");
                            }

                        }else if(w instanceof Laser){
                            if (((Laser) w).leftHand) {
                                System.out.print(" | " + w.name + " в левой руке  | ");
                            } else if (((Laser) w).rightHand) {
                                System.out.print(" | " + w.name + " в правой руке | ");
                            }
                        } else System.out.print(" | " + w.name + " на корпусe | ");
                    }*/
                    while (bot1.numberOfMoves > 0) {
                        System.out.println("1. Левая рука");
                        System.out.println("2. Правая рука");
                        System.out.println("3. Ракеты");
                        System.out.println("4. Назад");
                        String inputWeapon = reader.readLine();

                        int i = 1;
                        switch (inputWeapon) {
                            //ЛЕВАЯ РУКА
                            case ("1"):
                                for (Weapon lW : bot1.leftHandWeapon) {
                                    System.out.println("Выберите оружее");
                                    System.out.println(i++ + " " + lW.name);
                                }
                                i = 1;
                                while (true) {
                                    String input6 = reader.readLine();

                                    switch (input6) {
                                        case ("1"):
                                            System.out.println(bot1.leftHandWeapon.get(0).name);
                                            iFight.distAttack1(bot1, bot2, bot1.leftHandWeapon.get(0));
                                            break;
                                        case ("2"):
                                            if (bot1.leftHandWeapon.size() > 1) {
                                                System.out.println(bot2.leftHandWeapon.get(1).name);
                                                iFight.distAttack1(bot1, bot2, bot1.leftHandWeapon.get(1));
                                                break;
                                            } else break;

                                    }
                                    break;
                                }
                                break;
                            //ПРАВАЯ РУКА
                            case ("2"):
                                for (Weapon rW : bot1.rightHandWeapon) {
                                    System.out.println("Выберите оружее");
                                    System.out.println(i++ + " " + rW.name);
                                }

                                while (true) {
                                    String input7 = reader.readLine();

                                    switch (input7) {
                                        case ("1"):
                                            System.out.println(bot1.rightHandWeapon.get(0).name);
                                            iFight.distAttack1(bot1, bot2, bot1.rightHandWeapon.get(0));
                                            break;
                                        case ("2"):
                                            if (bot1.rightHandWeapon.size() > 1) {
                                                System.out.println(bot2.rightHandWeapon.get(1).name);
                                                iFight.distAttack1(bot1, bot2, bot1.rightHandWeapon.get(1));
                                                break;
                                            } else break;

                                    }
                                    break;
                                }
                                break;


                            //КОРПУС
                            case ("3"):
                                for (Weapon rocket : bot1.allRockets) {
                                    System.out.println("Выберите оружее");
                                    System.out.println(i++ + " " + rocket.name);
                                }
                                i = 1;
                                while (true) {
                                    String input8 = reader.readLine();

                                    switch (input8) {
                                        case ("1"):
                                            System.out.println(bot1.allRockets.get(0).name);
                                            iFight.distAttack1(bot1, bot2, bot1.allRockets.get(0));
                                            break;
                                        case ("2"):
                                            if (bot1.allRockets.size() > 1) {

                                                System.out.println(bot1.allRockets.get(0).name);
                                                iFight.distAttack1(bot1, bot2, bot1.allRockets.get(1));
                                                break;
                                            } else break;
                                    }
                                    break;
                                }
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
                    passTurn(bot1, bot2);
                    break;

                default:
                    StartBattle.wrongInput();
                    break;
            }
        }
    }

    public static void passTurn(Robot bot1, Robot bot2) {
        if (bot1.heatLev > 0 && bot1.heatLev != 10) {
            bot1.heatLev -= 20;
        } else if (bot1.heatLev == 10) {
            bot1.heatLev -= 10;
        }
        bot1.numberOfMoves = 0;
        bot2.numberOfMoves += bot2.maxNumberOfMoves;

    }

    private static void turnMessage(Robot bot1, Robot bot2) throws InterruptedException {
        System.out.println("Местоположение " + bot1.name + " " + bot1.coordinatX + ":" + bot1.coordinatY);
        Thread.sleep(200);
        System.out.println("Местоположение противника " + bot2.coordinatX + ":" + bot2.coordinatY);
        Thread.sleep(200);
        System.out.println("Уровень брони " + bot1.armor);
        Thread.sleep(200);
        System.out.println("Перегрев " + bot1.heatLev);
        Thread.sleep(200);
        System.out.println("1.Ход");
        Thread.sleep(200);
        System.out.println("2.Атака");
        Thread.sleep(200);
        System.out.println("3.Пропуск хода");
        Thread.sleep(200);

    }
}
