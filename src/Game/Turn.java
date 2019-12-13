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
                    if (bot1.legsArmor > 0)
                        iMove.go1(bot1, bot2);
                    else System.out.println("У тебя нет ног!!!");
                    break;
                //СТРЕЛЬБА
                case ("2"):
                    Thread.sleep(500);
                    System.out.println("Выберите оружие для стрельбы");

                    while (bot1.numberOfMoves > 0) {
                        preFightMessage(bot1, bot2);
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
                                                iFight.distAttack1(bot1, bot2, bot1.leftHandWeapon.get(0));
                                                break;
                                            case ("2"):
                                                if (bot1.leftHandWeapon.size() > 1) {
                                                    System.out.println(bot1.leftHandWeapon.get(1).name);
                                                    iFight.distAttack1(bot1, bot2, bot1.leftHandWeapon.get(1));
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
                                                iFight.distAttack1(bot1, bot2, bot1.rightHandWeapon.get(0));
                                                break;
                                            case ("2"):
                                                if (bot1.rightHandWeapon.size() > 1) {
                                                    System.out.println(bot1.rightHandWeapon.get(1).name);
                                                    iFight.distAttack1(bot1, bot2, bot1.rightHandWeapon.get(1));
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
                    StartBattle.wrongInput();
                    break;
            }
        }
    }

    public static void passTurn(Robot bot1) {
        bot1.setHeatLev(bot1.getHeatLev() - 10);
        bot1.numberOfMoves = 0;

    }

    private static void turnMessage(Robot bot1, Robot bot2) throws InterruptedException {
        System.out.println("Местоположение " + bot1.name + " " + bot1.coordinatX + ":" + bot1.coordinatY);
        Thread.sleep(200);
        System.out.println("Местоположение противника " + bot2.coordinatX + ":" + bot2.coordinatY);
        Thread.sleep(200);
        System.out.println("Состояние брони: " + "голова:" + bot1.headArmor + " корпус:" + bot1.bodyArmor + " левая рука:" + bot1.leftHandArmor + " правая рука:" + bot1.rightHandArmor +  " ноги:" + bot1.legsArmor);
        Thread.sleep(200);
        System.out.println("Очки действий " + bot1.numberOfMoves);
        Thread.sleep(200);
        System.out.println("Уровень перегрева составляет " + bot1.getHeatLev());
        Thread.sleep(200);
        System.out.println("1.Ход");
        Thread.sleep(200);
        System.out.println("2.Атака");
        Thread.sleep(200);
        System.out.println("3.Пропуск хода");
        Thread.sleep(200);
    }

    private static void preFightMessage(Robot bot1, Robot bot2) {
        boolean flag = false;
        for (Weapon w : bot1.weapons) {
            if ((bot2.coordinatX <= bot1.coordinatX + w.range & bot2.coordinatX >= bot1.coordinatX - w.range) && (bot2.coordinatY <= bot1.coordinatY + w.range & bot2.coordinatY >= bot1.coordinatY - w.range) && (bot1.leftHandWeapon.contains(w) || bot1.rightHandWeapon.contains(w) || bot1.allRockets.contains(w))) {
                System.out.print("  " + w.name + " достает до противника  ");
                flag = true;
            }
        }
        if (flag == false)
            System.out.println("Противник слишком далеко");


        System.out.println();
        System.out.println("1. Левая рука");
        System.out.println("2. Правая рука");
        System.out.println("3. Ракеты");
        System.out.println("4. Назад");
    }

}
