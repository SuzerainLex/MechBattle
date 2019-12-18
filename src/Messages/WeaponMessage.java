package Messages;

import Robots.Robot;

public class WeaponMessage {
    public static void getMessageGuns(Robot bot) {
        System.out.println("Вес вооружения " + bot.getWeaponWeight() + " из " + bot.getMaxWeigth());
        System.out.println("1. Маленькая пушка | Урон: 4  | Дальность: 7 | Боезапас: 10 | Очки хода: 1  | Перегрев: 5 | Вес: 1 |");
        System.out.println("2. Средняя пушка   | Урон: 7  | Дальность: 5 | Боезапас: 6  | Очки хода: 2  | Перегрев: 6 | Вес: 3 |");
        System.out.println("3. Большая пушка   | Урон: 12 | Дальность: 3 | Боезапас: 4  | Очки хода: 3  | Перегрев: 8 | Вес: 6 |");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все пушки");
    }
    public static void getMessageLasers(Robot bot) {
        System.out.println("Вес вооружения " + bot.getWeaponWeight() + " из " + bot.getMaxWeigth());
        System.out.println("1. Малый лазер   | Урон: 3 | Дальность: 6 | Боезапас: 10 | Очки хода: 1  | Перегрев: 3 | Вес: 1 | Тепловой урон: 5 | ");
        System.out.println("2. Средний лазер | Урон: 6 | Дальность: 5 | Боезапас: 8  | Очки хода: 2  | Перегрев: 6 | Вес: 3 | Тепловой урон: 7 | ");
        System.out.println("3. Большой лазер | Урон:10 | Дальность: 4 | Боезапас: 6  | Очки хода: 3  | Перегрев: 8 | Вес: 6 | Тепловой урон:10 | ");
        System.out.println("4. Назад");
        System.out.println("5. Убрать все лазеры");
    }
    public static void getMessageRockets(Robot bot) {
        System.out.println("Вес вооружения " + bot.getWeaponWeight() + " из " + bot.getMaxWeigth());
        System.out.println("1. Ракеты малой дальности  | Урон: 7 | Дальность: 6 | Боезапас: 4  | Очки хода: 2  | Перегрев: 1 | Вес: 5 |");
        System.out.println("2. Ракеты большой дальности  | Урон: 5 | Дальность: 8 | Боезапас: 4  | Очки хода: 2  | Перегрев: 1 | Вес: 5 |");
        System.out.println("3. Назад");
        System.out.println("4. Убрать все ракеты");
    }
}
