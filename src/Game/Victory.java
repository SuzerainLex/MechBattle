package Game;

import Robots.Robot;
import java.io.IOException;

public class Victory {
    public static boolean victory = false;

    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        while (!Victory.victory) {


            if (bot1.getNumberOfMoves() > 0) {
                System.out.println();
                System.out.println("ХОД ИГРОКА 1");
                Turn.makeTurn(bot1, bot2);
                if (bot2.getNumberOfMoves() == 0)
                    System.out.println("Очки ходов закончились");
                bot1.setHeatLev(bot1.getHeatLev() - 5);
                bot2.setNumberOfMoves(bot2.getMaxNumberOfMoves());
            }

            if (bot2.getNumberOfMoves() > 0) {
                System.out.println();
                System.out.println("ХОД ИГРОКА 2");
                Turn.makeTurn(bot2, bot1);
                if (bot2.getNumberOfMoves() == 0)
                    System.out.println("Очки ходов закончились");
                bot2.setHeatLev(bot2.getHeatLev() - 5);
                bot1.setNumberOfMoves(bot1.getMaxNumberOfMoves());
            }
        }
    }
 /*   private void playerTurn(Robot bot1, Robot bot2) throws IOException, InterruptedException {
            System.out.println();
            System.out.println("ХОД ИГРОКА 1");
            Turn.makeTurn(bot1, bot2);
            if (bot2.getNumberOfMoves() == 0)
                System.out.println("Очки ходов закончились");
            bot1.setHeatLev(bot1.getHeatLev() - 10);
            bot2.setNumberOfMoves(bot2.getMaxNumberOfMoves());
        }*/

    }

