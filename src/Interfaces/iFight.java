package Interfaces;

import Game.StartBattle;
import Game.Victory;
import Robots.Robot;
import Workshop.Lasers.Laser;
import Workshop.Weapon;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface iFight {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static void getDistAttackMessage(Robot bot1, Robot bot2, Weapon weapon) {
        try {
            Thread.sleep(200);
            System.out.println(bot1.name + " нанес " + weapon.name + " очков урона " + weapon.damage + " " + bot2.name);
            Thread.sleep(200);
            System.out.println("У противника" + " осталось брони " + "голова:" + bot2.headArmor + " тело:" + bot2.bodyArmor + " руки:" + bot2.handsArmor + " ноги:" + bot2.legsArmor);
            Thread.sleep(200);
            if (weapon instanceof Laser)
                System.out.println("Противнику нанесен тепловой урон " + weapon.damageHeat);
            System.out.println("Уровень перегрева составляет " + bot1.getHeatLev());
        } catch (InterruptedException ie) {
            System.out.println("ERROR");
        }
    }

    static void distAttack1(Robot bot1, Robot bot2, Weapon weapon) throws IOException, InterruptedException {
        if (weapon.ammunition != 0) {
            if ((bot1.numberOfMoves - weapon.cost) >= 0) {
                if (bot1.getHeatLev() <= bot1.radiator) {
                    if ((bot2.coordinatX <= bot1.coordinatX + weapon.range & bot2.coordinatX >= bot1.coordinatX - weapon.range) && (bot2.coordinatY <= bot1.coordinatY + weapon.range & bot2.coordinatY >= bot1.coordinatY - weapon.range)) {
                        int input4 = -1;
                        int rand = (int) (Math.random() * 10);

                        Thread.sleep(200);
                        System.out.println("Введите число от 0 до 10: ");
                        while (input4 < 0 || input4 > 10) {
                            try {
                                input4 = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                StartBattle.wrongInput();
                            }
                        }
                        if (rand == input4) {
                            System.out.println("Попадание в голову!");
                            weapon.ammunition--;
                            bot1.numberOfMoves -= weapon.cost;
                            bot2.headArmor -= weapon.damage;
                            bot2.setHeatLev(bot2.getHeatLev() + weapon.damageHeat);
                            bot1.setHeatLev(bot1.getHeatLev() + weapon.heat);

                            getDistAttackMessage(bot1, bot2, weapon);
                        } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                            System.out.println("Попадание в корпус!");
                            --weapon.ammunition;
                            bot1.numberOfMoves -= weapon.cost;
                            bot2.bodyArmor -= weapon.damage;
                            bot2.setHeatLev(bot2.getHeatLev() + weapon.damageHeat);
                            bot1.setHeatLev(bot1.getHeatLev() + weapon.heat);

                            getDistAttackMessage(bot1, bot2, weapon);
                        } else if ((rand - 2) == input4) {
                            weapon.ammunition--;
                            System.out.println("Попадание в ногу!");
                            bot1.numberOfMoves -= weapon.cost;
                            bot2.legsArmor -= weapon.damage;
                            bot2.setHeatLev(bot2.getHeatLev() + weapon.damageHeat);
                            bot1.setHeatLev(bot1.getHeatLev() + weapon.heat);
                            if (bot2.legsArmor <= 0) {
                                System.out.println("Ноги противника уничтожены");
                            }

                            getDistAttackMessage(bot1, bot2, weapon);
                        } else if ((rand + 2) == input4) {
                            weapon.ammunition--;
                            System.out.println("Попадание в руку!");
                            bot1.numberOfMoves -= weapon.cost;
                            bot2.handsArmor -= weapon.damage;
                            bot2.setHeatLev(bot2.getHeatLev() + weapon.damageHeat);
                            bot1.setHeatLev(bot1.getHeatLev() + weapon.heat);
                            if (bot2.handsArmor <= 0) {
                                System.out.println("Руки противника уничтожены");
                            }

                            getDistAttackMessage(bot1, bot2, weapon);
                        } else {
                            Thread.sleep(200);
                            System.out.println("Промазал!!!");
                            weapon.ammunition--;
                            bot1.numberOfMoves -= weapon.cost;
                            bot1.setHeatLev(bot1.getHeatLev() + weapon.heat);
                            Thread.sleep(200);

                        }
                        if (bot2.headArmor <= 0 || bot2.bodyArmor <= 0) {
                            Thread.sleep(500);
                            System.out.println("Враг уничожен!");
                            Thread.sleep(500);
                            System.out.println(bot1.name + " победил!!!");
                            //ЭТО НЕ РАБОТАЕТ((
                            bot1.numberOfMoves = 0;
                            bot2.numberOfMoves = 0;

                        }
                    } else System.out.println("Не достаю до цели");
                } else System.out.println("Мех перегрелся, невозможно стрелять");
            } else System.out.println("Не хватает очков действий");
        } else System.out.println("Нет боеприпасов!");
    }
}
