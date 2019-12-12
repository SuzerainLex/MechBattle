package Game;

import Robots.Robot;

import java.io.IOException;

public class Victory {
    public static boolean victory = false;
    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
                while (!Victory.victory) {


            if(bot1.numberOfMoves > 0) {
                System.out.println();
                System.out.println("ХОД ИГРОКА 1");
                Turn.makeTurn(bot1, bot2);
                bot1.setHeatLev(bot1.getHeatLev()-10);
                bot2.numberOfMoves = bot2.maxNumberOfMoves;
                System.out.println("Очки ходов закончились");
            }


            if(bot2.numberOfMoves > 0){
                System.out.println();
                System.out.println("ХОД ИГРОКА 2");
                Turn.makeTurn(bot2, bot1);
                bot2.setHeatLev(bot2.getHeatLev()-10);
                bot1.numberOfMoves = bot1.maxNumberOfMoves;
                System.out.println("Очки ходов закончились");
            }
        }
    }
}
