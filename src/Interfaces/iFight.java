package Interfaces;

import Game.StartBattle;
import Robots.Robot;
import Workshop.Weapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface iFight {

    //void distAttack(Robot robot) throws IOException, InterruptedException;
   /* static void distAttack(Robot bot1, Robot bot2, Weapon weapon) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input4 = -1 , number;
        int rand = (int) (Math.random() * 10);

        if (bot1.heatLev <= bot1.radiator) {
            if ((bot2.coordinatX <= bot1.coordinatX + bot1.rangeOfGunAttack & bot2.coordinatX >= bot1.coordinatX - bot1.rangeOfGunAttack) && (bot2.coordinatY <= bot1.coordinatY + bot1.rangeOfGunAttack & bot2.coordinatY >= bot1.coordinatY - bot1.rangeOfGunAttack)) {
                Thread.sleep(200);
                System.out.println("Введите число от 0 до 10: ");
                while (input4 < 0 || input4 > 10) {
                    try {
                        input4 = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        StartBattle.wrongInput();
                    }

                }
                if (rand == input4) {
                    System.out.println("Попадание в голову!");
                    bot2.armor = bot2.armor - bot1.mightOfGun * 3;
                    bot1.heatLev += 10;

                   getDistAttackMessahe(bot1, bot2);
                } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                    System.out.println("Попадание в корпус!");
                    bot2.armor = bot2.armor - bot1.mightOfGun;
                    bot1.heatLev += 10;

                    getDistAttackMessahe(bot1, bot2);
                } else if ((rand - 2) == input4) {
                    System.out.println("Попадание в ногу!");
                    bot2.armor = bot2.armor - (bot1.mightOfGun - 2);
                     bot1.heatLev += 10;


                    getDistAttackMessahe(bot1, bot2);
                } else if ((rand + 2) == input4) {
                    System.out.println("Попадание в руку!");
                    bot2.armor = bot2.armor - (bot1.mightOfGun - 2);
                    number = bot2.getMightOfGun() - 1;
                    bot1.heatLev += 10;
                    bot2.setMightOfGun(number);


                } else {
                    Thread.sleep(200);
                    System.out.println("Промазал!!!");
                    bot1.heatLev += 10;
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + bot1.heatLev);
                }
                //
                bot1.firstTurn = false;
                bot2.firstTurn = true;

                if (bot2.armor <= 0) {
                    Thread.sleep(500);
                    System.out.println("Враг уничожен!");
                    Thread.sleep(500);
                    System.out.println(bot1.name + " победил!!!");
                    //this.firstTurn = false;
                    bot2.firstTurn = false;
                    Robot.victory = true;
                }
            }
        }
    }*/
    static void getDistAttackMessahe(Robot bot1, Robot bot2){
       try {

           Thread.sleep(200);
           System.out.println(bot1.name + " нанес " + bot1.mightOfGun * 3 + " урона " + bot2.name);
           Thread.sleep(200);
           System.out.println("У противника" + " осталось " + bot2.armor + " брони.");
           Thread.sleep(200);
           System.out.println("Уровень перегрева составляет " + bot1.heatLev);
       }
       catch (InterruptedException ie){
           System.out.println("ERROR");
       }


    }
    static void distAttack1(Robot bot1, Robot bot2, Weapon weapon) throws IOException, InterruptedException {
        System.out.println("АТАКА " + weapon.name);
   /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input4 = -1 , number;
        int rand = (int) (Math.random() * 10);

        if (bot1.heatLev <= bot1.radiator) {
            if ((bot2.coordinatX <= bot1.coordinatX + weapon.range & bot2.coordinatX >= bot1.coordinatX - weapon.range) && (bot2.coordinatY <= bot1.coordinatY + weapon.range & bot2.coordinatY >= bot1.coordinatY - weapon.range)) {
                Thread.sleep(200);
                System.out.println("Введите число от 0 до 10: ");
                while (input4 < 0 || input4 > 10) {
                    try {
                        input4 = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        StartBattle.wrongInput();
                    }

                }
                if (rand == input4) {
                    bot1.numberOfMoves -= weapon.
                    System.out.println("Попадание в голову!");
                    bot2.headArmor -= weapon.damage;
                    bot2.heatLev += weapon.damageHeat;

                    bot2.armor = bot2.armor - bot1.mightOfGun * 3;
                    bot1.heatLev += 10;


                    getDistAttackMessahe(bot1, bot2);
                } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                    System.out.println("Попадание в корпус!");
                    bot2.armor = bot2.armor - bot1.mightOfGun;
                    bot1.heatLev += 10;

                    getDistAttackMessahe(bot1, bot2);
                } else if ((rand - 2) == input4) {
                    System.out.println("Попадание в ногу!");
                    bot2.armor = bot2.armor - (bot1.mightOfGun - 2);
                    bot1.heatLev += 10;


                    getDistAttackMessahe(bot1, bot2);
                } else if ((rand + 2) == input4) {
                    System.out.println("Попадание в руку!");
                    bot2.armor = bot2.armor - (bot1.mightOfGun - 2);
                    number = bot2.getMightOfGun() - 1;
                    bot1.heatLev += 10;
                    bot2.setMightOfGun(number);


                } else {
                    Thread.sleep(200);
                    System.out.println("Промазал!!!");
                    bot1.heatLev += 10;
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + bot1.heatLev);
                }
                // рудимент заменить на очки хода
               bot1.firstTurn = false;
                bot2.firstTurn = true;

                if (bot2.headArmor <= 0 || bot2.bodyArmor <= 0) {
                    Thread.sleep(500);
                    System.out.println("Враг уничожен!");
                    Thread.sleep(500);
                    System.out.println(bot1.name + " победил!!!");
                    //this.firstTurn = false;
                    bot2.firstTurn = false;
                    Robot.victory = true;
                }
            }
        }*/
    }
}
