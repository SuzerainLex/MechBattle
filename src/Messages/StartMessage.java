package Messages;

import Robots.Robot;

public class StartMessage {
    public StartMessage() {
        try {
            System.out.println("Это игра для двоих игроков;");
            Thread.sleep(700);
            System.out.println("Выберите мехов;");
            Thread.sleep(700);
            System.out.println("Установите оружие соотвествующее весу меха");
            Thread.sleep(700);
            System.out.println("Передвигайтесь по полю 12x12;");
            Thread.sleep(700);
            System.out.println("Ваша цель уничтожить оппонента;");
            Thread.sleep(700);
            System.out.println("При прицеливании введите число от 0 до 10; ");
            Thread.sleep(700);
            System.out.println("Уничтожение головы или корпуса полностью выводит из строя противника;");
            Thread.sleep(700);
            System.out.println("При уничтожении ног противник теряет спопсобность передвигаться;");
            Thread.sleep(700);
            System.out.println("При уничтожении рук противник теряет оружие;");
            Thread.sleep(700);
            System.out.println("При стрельбе вы перегреваетесь, но перегрев снижаеся каждый ход;");
            Thread.sleep(700);
            System.out.println("При столкновении с врагом вы вступаете в ближний бой и наносите урон противнику (meleemight) по (bodyarmor), но также и половину этого урона приходится на вашу броню (bodyarmor)");
            System.out.println();
            Thread.sleep(4000);
            printMes();
            Thread.sleep(400);
            System.out.println("                       Автор: SuzerainLex");
            System.out.println();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }


    private static void printMes() throws InterruptedException {
        char[] logo = {'M', 'E', 'C', 'H', 'B', 'A', 'T', 'T', 'L', 'E'};
        for (int i = 0; i < logo.length; i++) {
            Thread.sleep(300);
            System.out.print(logo[i]);
        }
        System.out.println();
    }

    public void choiceMessage(String s) {
        System.out.println(s + " игрок выберите меха");
        System.out.println("1.THOR");
        System.out.println("2.MAD CAT");
        System.out.println("3.LOCUST");
        System.out.println("4.ATLAS");
    }


}
