package Interfaces;

import Robots.Robot;

import java.io.IOException;

public interface iFight {

    void distAttack(Robot robot) throws IOException, InterruptedException;

}