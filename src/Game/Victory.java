package Game;

import Robots.Robot;

import java.io.IOException;

public class Victory {
    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        while (!Robot.victory) {

            //if (bot1.firstTurn && !bot2.firstTurn) {
            if(bot1.numberOfMoves > 0) {
                System.out.println();
                System.out.println("ХОД ИГРОКА 1");
                Turn.makeTurn(bot1, bot2);
            }

            //} else if (bot2.firstTurn && !bot1.firstTurn) {
            if(bot2.numberOfMoves > 0){
                System.out.println();
                System.out.println("ХОД ИГРОКА 2");
                Turn.makeTurn(bot2, bot1);
            }
        }
    }
}
