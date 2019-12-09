package Workshop;

import Robots.Robot;
import Robots.StartBattle;
import Workshop.Guns.BigGun;
import Workshop.Guns.Gun;
import Workshop.Guns.MediumGun;
import Workshop.Guns.MiniGun;
import Workshop.Lasers.BigLaser;
import Workshop.Lasers.Laser;
import Workshop.Lasers.MediumLaser;
import Workshop.Lasers.SmallLaser;
import Workshop.Rockets.LargeDistanceRockets;
import Workshop.Rockets.Rockets;
import Workshop.Rockets.SmallDistanceRockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameWorkShop {

    public static void getWeapons(Robot bot) {
        boolean i = false , l = false, m = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        while (true) {
            getMessageWeapons(bot);
            try {
                String inputW = reader.readLine();
                switch (inputW) {
                    case ("1"):
                        i = true;
                    while(i) {
                            getMessageGuns(bot);
                            String inputG = reader.readLine();
                            switch (inputG) {

                                case ("1"):
                                    MiniGun miniG = new MiniGun();
                                    miniG.instanceMiniGun(bot);
                                   // break guns;
                                    break ;


                                case ("2"):
                                    MediumGun mediumG = new MediumGun();
                                    mediumG.instanceMediumGun(bot);
                                   // break guns;
                                    break ;


                                case ("3"):
                                    BigGun bigG = new BigGun();
                                    bigG.instanceBigGun(bot);
                                  //  break guns;
                                    break ;
                                case ("4"): i = false;
                                break;

                                default:
                                    StartBattle.wrongInput();
                                    break;
                            }
                    }
                    break;
                    case ("2"): {
                        getMessageRockets(bot);
                        l = true;
                        while (l) {
                            String inputR = reader.readLine();
                            switch (inputR) {
                                case ("1"):
                                    SmallDistanceRockets sDR = new SmallDistanceRockets();
                                    sDR.instanceSmallDistanceRockets(bot);
                                    break;

                                case ("2"):
                                    LargeDistanceRockets lDR = new LargeDistanceRockets();
                                    lDR.instanceLargeDistanceRockets(bot);
                                    break;
                                case ("3"):
                                    l = false;
                                    break;


                                default:
                                    StartBattle.wrongInput();
                                    break;
                            }
                        }
                    }
                    break;

                    case ("3"): {
                        getMessageLasers(bot);
                        m = true;
                        while (m) {
                            String inputL = reader.readLine();
                            switch (inputL) {
                                case ("1"):
                                    SmallLaser sLaser = new SmallLaser();
                                    sLaser.instanceSmallLaser(bot);
                                    break;


                                case ("2"):
                                    MediumLaser mLaser = new MediumLaser();
                                    mLaser.instanceMediumlLaser(bot);

                                case ("3"):
                                    BigLaser bLaser = new BigLaser();
                                    bLaser.instanceBiglLaser(bot);
                                    break;
                                case ("4"): m = false;
                                break;

                                default:
                                    StartBattle.wrongInput();
                            }
                        }
                    }
                    break;
                }


                } catch(IOException e){
                    StartBattle.wrongInput();
                }
        }
    }

    public static void getWarning(Robot bot, Weapon weapon) {
        if (bot.guns > 2 || bot.lasers > 2 || bot.rockets > 2)
            System.out.println("Превышен лимит установки данного типа оружия");
        else if (weapon instanceof Laser && !bot.laserSocket || weapon instanceof Rockets && !bot.rocketSocket || weapon instanceof Gun && !bot.gunSocket) {
            System.out.println("Отсутсвует слот для установки данного вооружения");
        } else
            System.out.println("Превышен допустимый для вооружения вес");
    }
    private static void getMessageGuns(Robot bot){
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Маленькая пушка");
        System.out.println("2. Средняя пушка");
        System.out.println("3. Большая пушка");
        System.out.println("4. Назад");
    }
    private static void getMessageRockets(Robot bot){
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Ракеты малой дальности");
        System.out.println("2. Ракеты большой дальности");
        System.out.println("3. Назад");

    }
    private static void getMessageLasers(Robot bot){
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Малый лазер");
        System.out.println("2. Средний лазер");
        System.out.println("3. Большой лазер");
        System.out.println("4. Назад");
    }
    private static void getMessageWeapons(Robot bot){
        System.out.println("Выберите оружие");
        System.out.println("Вес вооружения " + bot.weaponWeight);
        System.out.println("1. Пушки");
        System.out.println("2. Ракеты");
        System.out.println("3. Лазеры");
    }

}
