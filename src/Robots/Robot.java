package Robots;

import Field.FieldOfBattle;
import Game.StartBattle;
import Interfaces.iFight;
import Interfaces.iMove;
import Workshop.Weapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Robot implements iFight, iMove {

    public static boolean victory = false;
    public int coordinatX, coordinatY, maxWeigth, weaponWeight, guns = 0, rockets = 0, lasers = 0, rocketSockets, laserSockets, gunSockets , maxNumberOfMoves;
    public String name;
    public int mightOfGun;
    public int rangeOfGunAttack;
    public int meleemight;
    public int numberOfMoves;
    public int armor;
    public int initiativa;
    public int radiator;
    public int heatLev = 0;
    public boolean firstTurn = false;
    public List<Weapon> weapons = new ArrayList<>();


    public Robot(String name, int mightOfGun, int rangeOfGunAttack, int meleemight, int initiativa, int armor, int radiator, int maxWeght, int gunSockets, int rocketSockets, int laserSockets, int maxNumberOfMoves) {
        this.name = name;
        this.mightOfGun = mightOfGun;
        this.rangeOfGunAttack = rangeOfGunAttack;
        this.meleemight = meleemight;
        this.maxNumberOfMoves = maxNumberOfMoves;
        this.numberOfMoves = maxNumberOfMoves;
        this.initiativa = initiativa;
        this.armor = armor;
        this.radiator = radiator;
        this.maxWeigth = maxWeght;
        this.laserSockets = laserSockets;
        this.gunSockets = gunSockets;
        this.rocketSockets = rocketSockets;
    }


    public void setMightOfGun(int mightOfGun) {
        this.mightOfGun = mightOfGun;
    }

    public int getMightOfGun() {
        if (mightOfGun <= 0)
            return 0;
        else return mightOfGun;

    }

    public int getInitiativa() {
        return initiativa;
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
    }



    public static void turn(Robot bot1, Robot bot2) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (bot1.numberOfMoves>0) {
            turnMessage(bot1, bot2);
            String input3 = reader.readLine();

            switch (input3) {
                case ("1"):
                    Thread.sleep(500);
                    iMove.go1(bot1, bot2);
                    break;

                case ("2"):
                    Thread.sleep(500);
                    System.out.println("Дальность орудия Меха " + bot1.rangeOfGunAttack);

                    iFight.distAttack(bot1, bot2);
                    break;

                case ("3"):
                    Thread.sleep(500);
                    System.out.println("Пропуск хода");
                    bot1.passTurn(bot2);
                    break;

                default:
                    StartBattle.wrongInput();
                    break;
            }

        }
    }

    public void passTurn(Robot robot) {
        if (this.heatLev > 0 && this.heatLev != 10) {
            this.heatLev -= 20;
        } else if (this.heatLev == 10) {
            this.heatLev -= 10;
        }
     /*   this.firstTurn = false;
        robot.firstTurn = true;*/
     this.numberOfMoves = 0;
     robot.numberOfMoves += robot.maxNumberOfMoves;

    }
    private static void turnMessage(Robot bot1, Robot bot2) throws InterruptedException{
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

    }

}

