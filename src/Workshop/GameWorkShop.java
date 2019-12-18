package Workshop;

import Messages.Message;
import Messages.WeaponMessage;
import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;
import Workshop.Rockets.LargeDistanceRockets;
import Workshop.Rockets.Rockets;
import Workshop.Rockets.SmallDistanceRockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameWorkShop {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void getWeapons(Robot bot) throws IOException {
        boolean g, r, l;
        String weapon;

        while (true) {
            Message.getMessageWeapons(bot);
            try {
                String inputW = reader.readLine();
                switch (inputW) {
                    //ПУШКИ
                    case ("1"):
                        g = true;

                        while (g) {
                            WeaponMessage.getMessageGuns(bot);
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
                                    g = false;
                                    break;

                                case ("5"):
                                    Gun.removeGuns(bot);
                                    break;

                                default:
                                    Message.wrongInput();
                                    break;
                            }
                        }
                        break;
                    //РАКЕТЫ
                    case ("2"):
                        r = true;
                        while (r) {
                            WeaponMessage.getMessageRockets(bot);
                            String inputR = reader.readLine();
                            switch (inputR) {
                                case ("1"):
                                    SmallDistanceRockets sDR = new SmallDistanceRockets();
                                    sDR.instanceRockets(bot);
                                    break;

                                case ("2"):
                                    LargeDistanceRockets lDR = new LargeDistanceRockets();
                                    lDR.instanceRockets(bot);
                                    break;

                                case ("3"):
                                    r = false;
                                    break;
                                case ("4"):
                                    Rockets.removeRockets(bot);
                                    break;

                                default:
                                    Message.wrongInput();
                                    break;
                            }
                        }
                        break;
                    //ЛАЗЕРЫ
                    case ("3"):
                        l = true;
                        while (l) {
                            WeaponMessage.getMessageLasers(bot);
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
                                    l = false;
                                    break;
                                case ("5"):
                                    Laser.removeLasers(bot);
                                    break;

                                default:
                                    Message.wrongInput();
                            }
                        }
                        break;
                    case ("4"):
                        Weapon.removeWeapon(bot);
                        break;

                    case ("5"):
                        if (bot.getWeaponWeight() > bot.getMaxWeigth()) {
                            System.out.println("Вы не можете выйти на бой с перевесом");
                            System.out.println("Установите другой набор вооружения");
                            continue;
                        } else
                            return;
                }
            } catch (IOException e) {
                Message.wrongInput();
            }
        }
    }
}
