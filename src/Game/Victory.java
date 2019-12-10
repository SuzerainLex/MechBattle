package Game;

import Robots.Robot;

import java.io.IOException;

public class Victory {
    public static void gamePlay(Robot bot1, Robot bot2) throws IOException, InterruptedException {
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
}
