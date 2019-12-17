package Game;

import Robots.MadCat;
import Robots.Robot;
import Robots.Thor;
import Workshop.GameWorkShop;
import music.PlaySounds;
import music.Sounds;

import java.io.*;


/*
Выберите мехов;
Передвигайтесь по полю 12x12;
При прицеливании введите число от 0 до 10;
При попадании в голову - тройной урон, уничтожение головы или корпуса поностью выводит из строя противника;
В корпус - обычный урон;
В ноги - обычный урон, но при их уничтожении противник теряет спопсобность передвигаться ;
В руки -  обычный урон, но при их уничтожении противник теряет оружие;
При стрельбе вы перегреваетесь, но перегрев снижаеся каждый ход;
* */
public class StartBattle {
    public static void main(String[] args) throws IOException, InterruptedException {
        PlaySounds pS1 = new PlaySounds(Sounds.TRACK3);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Robot bot1;
        Robot bot2;

            one:
            for (; ;) {
                System.out.println("Первый игрок выберите меха");
                System.out.println("1.THOR");
                System.out.println("2.MAD CAT");

                String input1 = reader.readLine();
                switch (input1) {

                    case ("1"):
                        bot1 = new Thor(6, 0);
                        System.out.println("Ты выбрал THOR");
                        GameWorkShop.getWeapons(bot1);
                        break one;
                    case ("2"):
                        bot1 = new MadCat(6, 0);
                        System.out.println("Ты выбрал MAD CAT");
                        GameWorkShop.getWeapons(bot1);
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
                    GameWorkShop.getWeapons(bot2);
                    break two;
                } else if (input2.equals("2")) {
                    bot2 = new MadCat(6, 12);
                    System.out.println("Ты выбрал MAD CAT");
                    GameWorkShop.getWeapons(bot2);
                    break two;
                } else
                    System.out.println("Второй игрок выберите меха");
            }
            FirstTurn.calcFirstTurn(bot1, bot2);
            Victory.gamePlay(bot1, bot2);
reader.close();
    }
}
