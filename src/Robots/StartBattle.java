package Robots;

import Workshop.GameWorkShop;

import java.lang.Thread;
import java.io.*;

/*
Выберите мехов;
Передвигайтесь по полю 12x12;
При прицеливании введите число от 0 до 10;
При попадании в голову - тройной урон;
В корпус - обычный урон;
В ноги - сниженный урон, но понижает очки хода противника;
В руки -  сниженный урон, но понижает урон противника;
При стрельбе вы перегреваетесь, но перегрев снижаеся при движении или пропуске хода;

* */
public class StartBattle {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Robot bot1;
        Robot bot2;

        one:
        for (; ; ) {
            System.out.println("Первый игрок выберите меха");
            System.out.println("1.THOR");
            System.out.println("2.MAD CAT");

            String input1 = reader.readLine();
            switch (input1) {

                case ("1"):
                    bot1 = new Thor(6, 12);
                    System.out.println("Ты выбрал THOR");
                    GameWorkShop.getWeapons(bot1);
                    break one;
                case ("2"):
                    bot1 = new MadCat(6, 0);
                    System.out.println("Ты выбрал MAD CAT");
                    break one;

                default:
                    System.out.println("Выберите меха");
            }
        }
        two:
        while (true) {
            System.out.println("Второй игрок выберите меха");
            System.out.println("1.THOR");
            System.out.println("2.MAD CAT");

            String input2 = reader.readLine();
            if (input2.equals("1")) {
                bot2 = new Thor(6, 12);
                System.out.println("Ты выбрал THOR");
                break two;
            } else if (input2.equals("2")) {
                bot2 = new MadCat(6, 0);
                System.out.println("Ты выбрал MAD CAT");
                break two;
            } else
                System.out.println("Второй игрок выберите меха");

        }



        while (true) {
            if ((bot1.getInitiativa() * (Math.random() * 10)) > (bot2.getInitiativa() * (Math.random() * 10))) {
                bot1.firstTurn = true;
                System.out.println("Первым ходит игрок 1");
                break;
            }

            if ((bot2.getInitiativa() * (Math.random() * 10)) > (bot1.getInitiativa() * (Math.random() * 10))) {
                System.out.println("Первым ходит игрок 2");
                bot2.firstTurn = true;
                break;
            }
        }
        while (!Robot.victory) {

            if (bot1.firstTurn && !bot2.firstTurn) {
                System.out.println();
                System.out.println("ХОД ИГРОКА 1");
                Robot.turn(bot1, bot2);
                Thread.sleep(500);
            }
            else if(bot2.firstTurn && !bot1.firstTurn){
                System.out.println();
                System.out.println("ХОД ИГРОКА 2");
                Robot.turn(bot2, bot1);
                Thread.sleep(500);
            }
        }
    }

    public static void wrongInput() {
        System.out.println("Неверный ввод");
    }


}

