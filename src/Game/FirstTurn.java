package Game;

import Robots.Robot;

public class FirstTurn {
    public static void calcFirstTurn(Robot bot1, Robot bot2){
        while (true) {
            if ((bot1.getInitiative() * (Math.random() * 10)) > (bot2.getInitiative() * (Math.random() * 10))) {
                bot1.setFirstTurn(true);
                System.out.println("Первым ходит игрок 1");
                break;
            }

            if ((bot2.getInitiative() * (Math.random() * 10)) > (bot1.getInitiative() * (Math.random() * 10))) {
                System.out.println("Первым ходит игрок 2");
                bot2.setFirstTurn(true);
                break;
            }
        }
    }
}
