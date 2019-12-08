package Robots;

import Field.FieldOfBattle;
import Interfaces.Fight;
import Interfaces.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public abstract class Robots implements Fight, Move {

    public static boolean victory = false;
    public int coordinatX;
    public int coordinatY;
    private String name;
    private int mightOfGun;
    private int rangeOfGunAttack;
    private int meleemight;
    private int numberOfMoves;
    private int initiativa;
    private int armor;
    public boolean firstTurn = false;
    // private int critChance;
   /* private int radiator;
    private int heatLev = 0;*/

    public Robots(String name, int mightOfGun, int rangeOfGunAttack, int meleemight, int numberOfMoves, int initiativa, int armor) {
        this.name = name;
        this.mightOfGun = mightOfGun;
        this.rangeOfGunAttack = rangeOfGunAttack;
        this.meleemight = meleemight;
        this.numberOfMoves = numberOfMoves;
        this.initiativa = initiativa;
        this.armor = armor;

        // this.critChance = critChance;

    }

    public int getInitiativa() {
        return initiativa;
    }

    public void setMightOfGun(int mightOfGun) {
        this.mightOfGun = mightOfGun;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public int getMightOfGun() {
        if (mightOfGun <= 0)
            return 0;
        else return mightOfGun;

    }

    public int getNumberOfMoves() {
        if (numberOfMoves <= 0)
            return 0;
        else return numberOfMoves;

    }

    public void distAttack(Robots robot) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input4 = -1;
        int rand = (int) (Math.random() * 10);
        int number;
        if ((robot.coordinatX <= coordinatX + rangeOfGunAttack & robot.coordinatX >= coordinatX - rangeOfGunAttack) && (robot.coordinatY <= coordinatY + rangeOfGunAttack & robot.coordinatY >= coordinatY - rangeOfGunAttack)) {
           /* if ((robot.coordinatX <= coordinatX + rangeOfGunAttack-1 & robot.coordinatX >= coordinatX - rangeOfGunAttack-1) && (robot.coordinatY <= coordinatY + rangeOfGunAttack-1 & robot.coordinatY >= coordinatY - rangeOfGunAttack-1)) {
                     }        */
            System.out.println("Введите число от 0 до 10: ");
            while (input4 < 0 || input4 > 10) {
                input4 = Integer.parseInt(reader.readLine());
            }
            if (rand == input4) {
                System.out.println("Попадание в голову!");
                robot.armor = robot.armor - mightOfGun * 3;
                System.out.println(this.name + " нанес " + mightOfGun * 3 + " урона " + robot.name);
                System.out.println("У противника" + " осталось " + robot.armor + " брони.");
            } else if ((rand - 1) == input4 || (rand + 1) == input4) {
                System.out.println("Попадание в корпус!");
                robot.armor = robot.armor - mightOfGun;
                System.out.println(this.name + " нанес " + mightOfGun + " урона " + robot.name);
                System.out.println("У противника" + " осталось " + robot.armor + " брони.");
            } else if ((rand - 2) == input4) {
                System.out.println("Попадание в ногу!");
                robot.armor = robot.armor - (mightOfGun - 2);
                number = robot.getNumberOfMoves() - 1;
                robot.setNumberOfMoves(number);
                System.out.println(this.name + " нанес " + (mightOfGun - 2) + " урона " + robot.name);
                System.out.println("У противника" + " осталось " + robot.armor + " брони.");
            } else if ((rand + 2) == input4) {
                System.out.println("Попадание в руку!");
                robot.armor = robot.armor - (mightOfGun - 2);
                number = robot.getMightOfGun() - 1;
                robot.setMightOfGun(number);
                System.out.println(this.name + " нанес " + (mightOfGun - 2) + " урона " + robot.name);
                System.out.println("У противника" + " осталось " + robot.armor + " брони.");

            } else System.out.println("Промазал!!!");
            {
                this.firstTurn = false;
                robot.firstTurn = true;
            }

            if (robot.armor <= 0) {
                System.out.println("Враг уничожен!");
                Thread.sleep(500);
                System.out.println(this.name + " победил!!!");
                // this.firstTurn = false;
                robot.firstTurn = false;
                Robots.victory = true;
            }

        } else {
            System.out.println("Не достаю до противника");
            this.firstTurn = true;
        }


    }


    public FieldOfBattle go(int x, int y) {
        if (x < 0 || y < 0 || x > FieldOfBattle.SIDEX || y > FieldOfBattle.SIDEY) {
            System.out.println("Неверный ход");
            firstTurn = true;

            return null;
        }
        if ((x <= coordinatX + numberOfMoves & x >= coordinatX - numberOfMoves) && (y <= coordinatY + numberOfMoves & y >= coordinatY - numberOfMoves)) {
            coordinatX = x;
            coordinatY = y;
            System.out.println(this.name + " пошел на ячейку " + x + ":" + y);
            firstTurn = false;
            return new FieldOfBattle(coordinatX, coordinatY);
        } else {
            System.out.println("Неверный ход");
            firstTurn = true;
            return null;
        }
    }

    public static void turn(Robots bot1, Robots bot2) throws IOException, InterruptedException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Местоположение " + bot1.name + " " + bot1.coordinatX + ":" + bot1.coordinatY);
            System.out.println("Местоположение противника" + bot2.coordinatX + ":" + bot2.coordinatY);
            System.out.println("1.Ход");
            System.out.println("2.Атака");
            String input3 = reader.readLine();
            if (input3.equals("1")) {
                System.out.println("Введите координаты хода (у данного Меха не более " + bot1.numberOfMoves + ").");
                int u = Integer.parseInt(reader.readLine());
                int i = Integer.parseInt(reader.readLine());
                bot1.go(u, i);
                if (!bot1.firstTurn) {
                    bot2.firstTurn = true;
                    break;
                }
            }
            if (input3.equals("2")) {
                System.out.println("Дальность орудия Меха " + bot1.rangeOfGunAttack);

                bot1.distAttack(bot2);
                break;
            } else
                StartBattle.neverniVivod();
        }
    }

}

