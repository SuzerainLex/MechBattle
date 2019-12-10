package Interfaces;

import Field.FieldOfBattle;
import Robots.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface iMove {

    static void go1(Robot bot1, Robot bot2) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (bot1.numberOfMoves > 0) {
                goMessage(bot1);
                String input = reader.readLine();
                if (input.equals("w") && bot1.coordinatY < FieldOfBattle.SIDEY) {
                    System.out.println("вперед");
                    bot1.coordinatY++;
                    bot1.numberOfMoves--;
                } else if (input.equals("s") && bot1.coordinatY > 0) {
                    System.out.println("назад");
                    bot1.coordinatY--;
                    bot1.numberOfMoves--;

                } else if (input.equals("a") && bot1.coordinatX > 0) {
                    System.out.println("влево");
                    bot1.coordinatX--;
                    bot1.numberOfMoves--;

                } else if (input.equals("d") && bot1.coordinatX < FieldOfBattle.SIDEX) {
                    System.out.println("враво");
                    bot1.coordinatX++;
                    bot1.numberOfMoves--;

                }
                if (bot1.coordinatX == bot2.coordinatX && bot1.coordinatY == bot2.coordinatY) {
                    System.out.println("ТАРАН");
                }
                if (input.equals("1")) {
                    return;
                }
            }

        goMessage(bot1);
        if (bot1.heatLev > 0) {
            bot1.heatLev -= 10;
        }
        System.out.println("Очки ходов закончились");
        bot2.numberOfMoves = bot2.maxNumberOfMoves;

    }

    static void goMessage(Robot bot1) {
        System.out.println("W ВПЕРЕД   S НАЗАД    A ВЛЕВО     D ВПРАВО");
        System.out.println("Местонахождение " + bot1.coordinatX + ":" + bot1.coordinatY);
    }

}

