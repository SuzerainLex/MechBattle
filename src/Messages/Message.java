package Messages;

import Game.StartBattle;
import Game.Victory;
import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;
import Workshop.Weapon;
import music.PlaySounds;
import music.Sounds;

import java.util.List;

public class Message {
    public static void turnMessage(Robot bot1, Robot bot2) throws InterruptedException {
        System.out.println("Местоположение " + bot1.name + " " + bot1.coordinatX + ":" + bot1.coordinatY);
        Thread.sleep(200);
        System.out.println("Местоположение противника " + bot2.coordinatX + ":" + bot2.coordinatY);
        Thread.sleep(200);
        System.out.println("Состояние брони: " + "голова:" + bot1.getHeadArmor() + " корпус:" + bot1.getBodyArmor() + " левая рука:" + bot1.getLeftHandArmor() + " правая рука:" + bot1.getRightHandArmor() + " ноги:" + bot1.getLegsArmor());
        Thread.sleep(200);
        System.out.println("Очки действий " + bot1.getNumberOfMoves());
        Thread.sleep(200);
        System.out.println("Уровень перегрева составляет " + bot1.getHeatLev());
        Thread.sleep(200);
        System.out.println("1.Ход");
        Thread.sleep(200);
        System.out.println("2.Атака");
        Thread.sleep(200);
        System.out.println("3.Пропуск хода");
        Thread.sleep(200);
    }

    public static void preFightMessage(Robot bot1, Robot bot2) {
        boolean flag = false;
       /* for (Weapon w : bot1.weapons) {
            if ((bot2.coordinatX <= bot1.coordinatX + w.range & bot2.coordinatX >= bot1.coordinatX - w.range) && (bot2.coordinatY <= bot1.coordinatY + w.range & bot2.coordinatY >= bot1.coordinatY - w.range) && (bot1.leftHandWeapon.contains(w) || bot1.rightHandWeapon.contains(w) || bot1.allRockets.contains(w))&& w.ammunition > 0) {
                System.out.print("  " + w.name + " достает до противника  ");
                flag = true;
            }*/
        preMoveMessage(bot1, bot2);
        System.out.println();
        System.out.println("1. Левая рука" + fromList(bot1.leftHandWeapon));
        System.out.println("2. Правая рука" + fromList(bot1.rightHandWeapon));
        System.out.println("3. Ракеты" + fromList(bot1.allRockets));
        System.out.println("4. Назад");
    }

    public static void preMoveMessage(Robot bot1, Robot bot2) {
        boolean flag = false;
        for (Weapon w : bot1.weapons) {
            if ((bot2.coordinatX <= bot1.coordinatX + w.range & bot2.coordinatX >= bot1.coordinatX - w.range) && (bot2.coordinatY <= bot1.coordinatY + w.range & bot2.coordinatY >= bot1.coordinatY - w.range) && (bot1.leftHandWeapon.contains(w) || bot1.rightHandWeapon.contains(w) || bot1.allRockets.contains(w)) && w.ammunition > 0) {
                System.out.print("  " + w.name + " достает до противника  ");
                flag = true;
            }
        }
        if (flag == false)
            System.out.println("Противник слишком далеко");
    }


    private static String fromList(List<Weapon> list) {
        String result = "";
        for (Weapon weapon : list) {
            result = result + " " + weapon.name;
        }

        return result;
    }


    public static void getMessageWeapons(Robot bot) {
        System.out.println("Выберите оружие");
        System.out.println("Вес вооружения " + bot.getWeaponWeight() + " из " + bot.getMaxWeigth());
        System.out.println("Установлены : ");
        for (Weapon w : bot.weapons) {
            if (w instanceof Gun) {
                if (((Gun) w).leftHand) {
                    System.out.print(" | " + w.name + " в левой руке | ");
                } else if (((Gun) w).rightHand) {
                    System.out.print(" | " + w.name + " в правой руке | ");
                }

            } else if (w instanceof Laser) {
                if (((Laser) w).leftHand) {
                    System.out.print(" | " + w.name + " в левой руке | ");
                } else if (((Laser) w).rightHand) {
                    System.out.print(" | " + w.name + " в правой руке | ");
                }
            } else System.out.print(" | " + w.name + " на корпусе | ");
        }
        System.out.println();
        System.out.println("1. Пушки");
        System.out.println("2. Ракеты");
        System.out.println("3. Лазеры");
        System.out.println("4. Убрать всё");
        System.out.println("5. Закончить установку оружия");
    }

    public static void getWarning(Robot bot) {
        if (bot.getMaxWeigth() <= bot.getWeaponWeight()) {
            PlaySounds pS = new PlaySounds(Sounds.WARNINGSOUND);
            System.out.println("Превышен допустимый для вооружения вес");
            System.out.println("Вы не можете выйти на бой с перевесом");
            System.out.println("Установите другой набор вооружения");
        } else if (bot.guns >= bot.gunSockets || bot.lasers >= bot.laserSockets || bot.rockets >= bot.rocketSockets) {
            System.out.println("Превышен лимит установки данного типа оружия");
        }
    }

    public static void goMessage(Robot bot1, Robot bot2) {
        System.out.println("W ВПЕРЕД   S НАЗАД    A ВЛЕВО     D ВПРАВО    1. НАЗАД");
        System.out.println("Местонахождение " + bot1.coordinatX + ":" + bot1.coordinatY);
        System.out.println("Местоположение противника " + bot2.coordinatX + ":" + bot2.coordinatY);
        System.out.println("Очки хода " + bot1.getNumberOfMoves());
    }

    public static void getDistAttackMessage(Robot bot1, Robot bot2, Weapon weapon) {
        try {
            Thread.sleep(200);
            System.out.println(bot1.name + " нанес " + weapon.name + " очков урона " + weapon.damage + " " + bot2.name);
            Thread.sleep(200);
            System.out.println("У противника осталось брони " + "голова:" + bot2.getHeadArmor() + " тело:" + bot2.getBodyArmor() + " левая рука:" + bot2.getLeftHandArmor() + " правая рука:" + bot2.getRightHandArmor() + " ноги:" + bot2.getLegsArmor());
            Thread.sleep(200);
            if (weapon instanceof Laser)
                System.out.println("Противнику нанесен тепловой урон " + weapon.damageHeat);
            System.out.println("Уровень перегрева составляет " + bot1.getHeatLev() + " из " + bot1.getRadiator());
        } catch (InterruptedException ie) {
            System.out.println("ERROR");
        }
    }

    public static void wrongInput() {
        System.out.println("Неверный ввод");
    }

    public static void victoryMessage(Robot bot) throws InterruptedException {
        Thread.sleep(500);
        PlaySounds warning = new PlaySounds(Sounds.WARNINGSOUND);
        System.out.println("Враг уничожен!");
        Thread.sleep(500);
        System.out.println(bot.name + " победил!!!");
        Victory.victory = true;
        StartBattle.pS1.interrupt();
    }

    public static void mechChar(Robot bot) {
        System.out.format("Очки хода: %d  Радиатор: %d  Инициатива: %d  Урон в ближнем бою: %d  Максимальный вес %d%n Броня головы: %d  Броня тела: %d  Броня левой руки: %d  Броня правой руки: %d  Броня ног: %d  Сокеты лазеров: %d  Сокеты пушек: %d  Сокеты ракет: %d  Максимум оружия на левой руке: %d  Максимум оружия на правой руке %d%n ",
                bot.getNumberOfMoves(), bot.getRadiator(), bot.getInitiative(), bot.meleemight, bot.getMaxWeigth(), bot.getHeadArmor(), bot.getBodyArmor(), bot.getLeftHandArmor(), bot.getRightHandArmor(), bot.getLegsArmor(), bot.laserSockets, bot.gunSockets, bot.rocketSockets, bot.maxLeftHandSlots, bot.maxLeftHandSlots);
    }
}
