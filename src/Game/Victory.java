package Game;

import Robots.Robot;
import java.io.IOException;

public class Victory {
    public static boolean victory = false;

    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        while (!Victory.victory) {


            if (bot1.getNumberOfMoves() > 0 && bot1.firstTurn) {
                bot2.firstTurn = true;
                bot1.playerTurn(bot2);

            }

            if (bot2.getNumberOfMoves() > 0 && bot2.firstTurn) {
                bot1.firstTurn = true;
                bot2.playerTurn(bot1);

            }
        }
    }

    }

