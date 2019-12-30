package Game;

import Robots.Robot;
import java.io.IOException;

public class Victory {
    public static boolean victory = false;

    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        while (!Victory.victory) {


            if (bot1.getNumberOfMoves() > 0 && bot1.getFirstTurn()) {
                bot2.setFirstTurn(true);
                bot1.playerTurn(bot2);

            }

            if (bot2.getNumberOfMoves() > 0 && bot2.getFirstTurn()) {
                bot1.setFirstTurn(true);
                bot2.playerTurn(bot1);

            }
        }
    }

    }

