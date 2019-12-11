package Robots;


import Interfaces.iFight;
import Interfaces.iMove;
import Workshop.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Robot implements iFight, iMove {

    public static boolean victory = false;
    public final int maxWeigth, armor;
    public int headArmor, bodyArmor, handsArmor, legsArmor;
    public int coordinatX, coordinatY, weaponWeight, guns = 0, rockets = 0, lasers = 0, rocketSockets, laserSockets, gunSockets, maxNumberOfMoves, rangeOfGunAttack, mightOfGun, numberOfMoves, meleemight, initiativa, radiator, heatLev = 0, maxLeftHandSlots, maxRightHandSlots, leftHandSlots = 0, rightHandSlots = 0;
    public String name;
    public boolean firstTurn = false;
    public List<Weapon> weapons = new ArrayList<>();
    public List<Weapon> leftHandWeapon = new ArrayList<>();
    public List<Weapon> rightHandWeapon = new ArrayList<>();
    public List<Weapon> allRockets = new ArrayList<>();

    public Robot(String name, int mightOfGun, int rangeOfGunAttack, int meleemight, int initiativa, int armor, int radiator, int maxWeght, int gunSockets, int rocketSockets, int laserSockets, int maxNumberOfMoves, int maxLeftHandSlots, int maxRightHandSlots) {
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
        this.maxLeftHandSlots = maxLeftHandSlots;
        this.maxRightHandSlots = maxRightHandSlots;
        this.headArmor = armor / 4;
        this.bodyArmor = armor / 2;
        this.handsArmor = armor / 4;
        this.legsArmor = armor / 3;
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


}

