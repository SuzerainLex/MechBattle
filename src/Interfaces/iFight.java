package Interfaces;

import Robots.Robot;
import Workshop.Weapon;
import java.io.IOException;

public interface iFight {
       void distAttack(Robot bot, Weapon weapon) throws IOException, InterruptedException;

}
