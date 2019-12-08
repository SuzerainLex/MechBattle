package Interfaces;

import Robots.Robots;

import java.io.IOException;

public interface Fight {

    void distAttack(Robots robot) throws IOException, InterruptedException;

}