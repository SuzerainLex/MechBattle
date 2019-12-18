package Game;

import Messages.Message;
import Messages.StartMessage;
import Robots.*;
import Workshop.GameWorkShop;
import music.PlaySounds;
import music.Sounds;

import java.io.*;


/*
Это игра для двоих игроков.
Выберите мехов;
Установите оружие соотвествующее весу меха;
Передвигайтесь по полю 12x12;
Ваша цель уничтожить оппонента;
При прицеливании введите число от 0 до 10;
Уничтожение головы или корпуса полностью выводит из строя противника;
При уничтожении ног противник теряет спопсобность передвигаться ;
При уничтожении рук противник теряет оружие;
При стрельбе вы перегреваетесь, но перегрев снижаеся каждый ход;
При столкновении с врагом вы вступаете в ближний бой и наносите урон противнику (meleemight) по (bodyarmor),
 но также и половину этого урона приходится на вашу броню (bodyarmor);
*/
public class StartBattle {
    static {
        try {
            StartMessage.rulesMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pS1 = new PlaySounds(Sounds.TRACK);
    }

    public static PlaySounds pS1;
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Robot bot1;
        Robot bot2;

        one:
        for (; ;) {
            System.out.println("Первый игрок выберите меха");
            System.out.println("1.THOR");
            System.out.println("2.MAD CAT");
            System.out.println("3.LOCUST");
            System.out.println("4.ATLAS");

            String input1 = reader.readLine();
            switch (input1) {

                case ("1"):
                    bot1 = new Thor("PLAYER 1", 6, 0);
                    System.out.println("Ты выбрал " + bot1.name);
                    Message.mechChar(bot1);
                    GameWorkShop.getWeapons(bot1);
                    break one;
                case ("2"):
                    bot1 = new MadCat("PLAYER 1", 6, 0);
                    System.out.println("Ты выбрал " + bot1.name);
                    Message.mechChar(bot1);
                    GameWorkShop.getWeapons(bot1);
                    break one;
                case ("3"):
                    bot1 = new Locust("PLAYER 1", 6, 0);
                    System.out.println("Ты выбрал " + bot1.name);
                    Message.mechChar(bot1);
                    GameWorkShop.getWeapons(bot1);
                    break one;
                case ("4"):
                    bot1 = new Atlas("PLAYER 1", 6, 0);
                    System.out.println("Ты выбрал " + bot1.name);
                    Message.mechChar(bot1);
                    GameWorkShop.getWeapons(bot1);
                    break one;

                default:
                    System.out.println("Выберите меха");
            }
        }
        two:
        for (; ;) {
            System.out.println("Второй игрок выберите меха");
            System.out.println("1.THOR");
            System.out.println("2.MAD CAT");
            System.out.println("3.LOCUST");
            System.out.println("4.ATLAS");

            String input2 = reader.readLine();
            switch (input2) {
                case ("1"):
                    bot2 = new Thor("PLAYER 2", 6, 12);
                    System.out.println("Ты выбрал " + bot2.name);
                    Message.mechChar(bot2);
                    GameWorkShop.getWeapons(bot2);
                    break two;
                case ("2"):
                    bot2 = new MadCat("PLAYER 2", 6, 12);
                    System.out.println("Ты выбрал " + bot2.name);
                    Message.mechChar(bot2);
                    GameWorkShop.getWeapons(bot2);
                    break two;
                case ("3"):
                    bot2 = new Locust("PLAYER 2", 6, 12);
                    System.out.println("Ты выбрал " + bot2.name);
                    Message.mechChar(bot2);
                    GameWorkShop.getWeapons(bot2);
                    break two;
                case ("4"):
                    bot2 = new Atlas("PLAYER 2", 6, 12);
                    System.out.println("Ты выбрал " + bot2.name);
                    Message.mechChar(bot2);
                    GameWorkShop.getWeapons(bot2);
                    break two;

                default:
                    System.out.println("Второй игрок выберите меха");
            }
        }
        FirstTurn.calcFirstTurn(bot1, bot2);
        Victory.gamePlay(bot1, bot2);
        reader.close();
    }
}
