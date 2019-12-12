package Workshop;

import Robots.Robot;
import Game.StartBattle;
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
  static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ;
    public static void getWeapons(Robot bot) throws IOException {
        boolean i , l , m;
        String weapon;


            while (true) {
                Weapon.getMessageWeapons(bot);
                try {
                    String inputW = reader.readLine();
                    switch (inputW) {
                        //ПУШКИ
                        case ("1"):
                            i = true;

                            while (i) {
                                Gun.getMessageGuns(bot);
                                String inputG = reader.readLine();
                                switch (inputG) {

                                    case ("1"):
                                        weapon = "miniGun";
                                        Gun.handChoose(bot, weapon);
                                         break;

                                    case ("2"):
                                        weapon = "mediumGun";
                                        Gun.handChoose(bot, weapon);
                                        break;

                                    case ("3"):
                                        weapon = "BigGun";
                                        Gun.handChoose(bot, weapon);
                                        break;

                                    case ("4"):
                                        i = false;
                                        break;

                                    case ("5"):
                                        Gun.removeGuns(bot);
                                        break;

                                    default:
                                        StartBattle.wrongInput();
                                        break;
                                }
                            }
                            break;
                        //РАКЕТЫ
                        case ("2"):
                            l = true;
                            while (l) {
                                Rockets.getMessageRockets(bot);
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
                                    case ("4"):
                                        Rockets.removeRockets(bot);
                                        break;

                                    default:
                                        StartBattle.wrongInput();
                                        break;
                                }
                            }
                            break;
                        //ЛАЗЕРЫ
                        case ("3"):
                            m = true;
                            while (m) {
                                Laser.getMessageLasers(bot);
                                String inputL = reader.readLine();
                                switch (inputL) {
                                    case ("1"):
                                        weapon = "smalllaser";
                                       Laser.handChoose(bot, weapon);
                                        break;

                                    case ("2"):
                                        weapon = "mediumlaser";
                                        Laser.handChoose(bot, weapon);
                                        break;

                                    case ("3"):
                                        weapon = "biglaser";
                                        Laser.handChoose(bot, weapon);
                                        break;
                                    case ("4"):
                                        m = false;
                                        break;
                                    case ("5"):
                                        Laser.removeLasers(bot);
                                        break;

                                    default:
                                        StartBattle.wrongInput();
                                }
                            }
                            break;
                        case ("4"):
                            Weapon.removeWeapon(bot);
                            break;

                        case ("5"):
                            if(bot.weaponWeight > bot.maxWeigth){
                                System.out.println("Вы не можете выйти на бой с перевесом");
                                System.out.println("Установите другой набор вооружения");
                                continue;
                            }
                            else
                            return;
                    }
                } catch (IOException e) {
                    StartBattle.wrongInput();
                }
            }

    }

    public static void getWarning(Robot bot) {
        if(bot.maxWeigth <= bot.weaponWeight) {
            System.out.println("Превышен допустимый для вооружения вес");
            System.out.println("Вы не можете выйти на бой с перевесом");
            System.out.println("Установите другой набор вооружения");
        } else if (bot.guns >= bot.gunSockets || bot.lasers >= bot.laserSockets || bot.rockets >= bot.rocketSockets) {
            System.out.println("Превышен лимит установки данного типа оружия");}

    }

}
