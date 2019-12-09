package Interfaces;

import Robots.Robot;
import Workshop.Weapon;

import java.io.IOException;

public interface iFight {

    void distAttack(Robot robot) throws IOException, InterruptedException;

}