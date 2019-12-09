package Robots;

import Field.FieldOfBattle;
import Interfaces.iFight;
import Interfaces.iMove;
import Workshop.Weapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public abstract class Robot implements iFight, iMove {

    public static boolean victory = false;
    public int coordinatX, coordinatY, maxWeigth, weaponWeight, guns, rockets, lasers;
    private String name;
    private int mightOfGun, rangeOfGunAttack, meleemight, numberOfMoves, armor, initiativa, radiator, heatLev = 0;
    public boolean firstTurn = false, rocketSocket, laserSocket, gunSocket;
    public List<Weapon> weapons = new ArrayList<>();


    public Robot(String name, int mightOfGun, int rangeOfGunAttack, int meleemight, int numberOfMoves, int initiativa, int armor, int radiator) {
        this.name = name;
        this.mightOfGun = mightOfGun;
        this.rangeOfGunAttack = rangeOfGunAttack;
        this.meleemight = meleemight;
        this.numberOfMoves = numberOfMoves;
        this.initiativa = initiativa;
        this.armor = armor;
        this.radiator = radiator;
    }


    private void setMightOfGun(int mightOfGun) {
        this.mightOfGun = mightOfGun;
    }

    private void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    private int getMightOfGun() {
        if (mightOfGun <= 0)
            return 0;
        else return mightOfGun;

    }

    public int getInitiativa() {
        return initiativa;
    }

    private int getNumberOfMoves() {
        if (numberOfMoves <= 0)
            return 0;
        else return numberOfMoves;

    }

    public void distAttack(Robot robot) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input4 = -1;
        int rand = (int) (Math.random() * 10);
        int number;
        if (this.heatLev <= this.radiator) {
            if ((robot.coordinatX <= coordinatX + rangeOfGunAttack & robot.coordinatX >= coordinatX - rangeOfGunAttack) && (robot.coordinatY <= coordinatY + rangeOfGunAttack & robot.coordinatY >= coordinatY - rangeOfGunAttack)) {
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
                    robot.armor = robot.armor - mightOfGun * 3;
                    this.heatLev += 10;
                    Thread.sleep(200);
                    System.out.println(this.name + " нанес " + mightOfGun * 3 + " урона " + robot.name);
                    Thread.sleep(200);
                    System.out.println("У противника" + " осталось " + robot.armor + " брони.");
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + this.heatLev);
                } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                    System.out.println("Попадание в корпус!");
                    robot.armor = robot.armor - mightOfGun;
                    this.heatLev += 10;
                    System.out.println(this.name + " нанес " + mightOfGun + " урона " + robot.name);
                    System.out.println("У противника" + " осталось " + robot.armor + " брони.");
                    System.out.println("Уровень перегрева составляет " + this.heatLev);
                } else if ((rand - 2) == input4) {
                    System.out.println("Попадание в ногу!");
                    robot.armor = robot.armor - (mightOfGun - 2);
                    number = robot.getNumberOfMoves() - 1;
                    this.heatLev += 10;
                    robot.setNumberOfMoves(number);
                    Thread.sleep(200);
                    System.out.println(this.name + " нанес " + (mightOfGun - 2) + " урона " + robot.name);
                    Thread.sleep(200);
                    System.out.println("У противника" + " осталось " + robot.armor + " брони.");
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + this.heatLev);
                } else if ((rand + 2) == input4) {
                    System.out.println("Попадание в руку!");
                    robot.armor = robot.armor - (mightOfGun - 2);
                    number = robot.getMightOfGun() - 1;
                    this.heatLev += 10;
                    robot.setMightOfGun(number);
                    Thread.sleep(200);
                    System.out.println(this.name + " нанес " + (mightOfGun - 2) + " урона " + robot.name);
                    Thread.sleep(200);
                    System.out.println("У противника" + " осталось " + robot.armor + " брони.");
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + this.heatLev);

                } else {
                    Thread.sleep(200);
                    System.out.println("Промазал!!!");
                    this.heatLev += 10;
                    Thread.sleep(200);
                    System.out.println("Уровень перегрева составляет " + this.heatLev);
                }
                //
                this.firstTurn = false;
                robot.firstTurn = true;

                if (robot.armor <= 0) {
                    Thread.sleep(500);
                    System.out.println("Враг уничожен!");
                    Thread.sleep(500);
                    System.out.println(this.name + " победил!!!");
                    //this.firstTurn = false;
                    robot.firstTurn = false;
                    Robot.victory = true;
                }

            } else {
                System.out.println("Не достаю до противника");
                this.firstTurn = true;
                robot.firstTurn = false;
            }
        } else {
            System.out.println("Мех перегрелся");
            System.out.println("Пропуск хода или движение снизят перегрев");
            this.firstTurn = true;
            robot.firstTurn = false;
        }

    }


    public void go(int x, int y, Robot bot) {
        if (x < 0 || y < 0 || x > FieldOfBattle.SIDEX || y > FieldOfBattle.SIDEY) {
            System.out.println("Неверный ход");
            this.firstTurn = true;
            bot.firstTurn = false;
            return;
        }
        if (x == bot.coordinatX && y == bot.coordinatY) {
            System.out.println("Неверный ход, здесь расположен противник");
            this.firstTurn = true;
            bot.firstTurn = false;
            return;

        }

        if ((x <= coordinatX + numberOfMoves & x >= coordinatX - numberOfMoves) && (y <= coordinatY + numberOfMoves & y >= coordinatY - numberOfMoves)) {
            coordinatX = x;
            coordinatY = y;
            System.out.println(this.name + " пошел на ячейку " + x + ":" + y);
            if (this.heatLev > 0) {
                this.heatLev -= 10;
            }
            this.firstTurn = false;
            bot.firstTurn = true;
        } else {
            System.out.println("Неверный ход");
            this.firstTurn = true;
            bot.firstTurn = false;
        }
    }

    static void turn(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Местоположение " + bot1.name + " " + bot1.coordinatX + ":" + bot1.coordinatY);
            Thread.sleep(200);
            System.out.println("Местоположение противника " + bot2.coordinatX + ":" + bot2.coordinatY);
            Thread.sleep(200);
            System.out.println("Уровень брони " + bot1.armor);
            Thread.sleep(200);
            System.out.println("Перегрев " + bot1.heatLev);
            Thread.sleep(200);
            System.out.println("1.Ход");
            Thread.sleep(200);
            System.out.println("2.Атака");
            Thread.sleep(200);
            System.out.println("3.Пропуск хода");
            Thread.sleep(200);
            String input3 = reader.readLine();

            switch (input3) {
                case ("1"):
                    Thread.sleep(500);
                    System.out.println("Введите координаты хода (у данного Меха не более " + bot1.numberOfMoves + ").");
                    int u = Integer.parseInt(reader.readLine());
                    int i = Integer.parseInt(reader.readLine());

                    bot1.go(u, i, bot2);
                    break;

                case ("2"):
                    Thread.sleep(500);
                    System.out.println("Дальность орудия Меха " + bot1.rangeOfGunAttack);

                    bot1.distAttack(bot2);
                    break;

                case ("3"):
                    Thread.sleep(500);
                    System.out.println("Пропуск хода");
                    bot1.passTurn(bot2);
                    break;
                default:
                    StartBattle.wrongInput();
            }
            break;
        }
    }

    public void passTurn(Robot robot) {
        if (this.heatLev > 0 && this.heatLev != 10) {
            this.heatLev -= 20;
        } else if (this.heatLev == 10) {
            this.heatLev -= 10;
        }
        this.firstTurn = false;
        robot.firstTurn = true;

    }

}

