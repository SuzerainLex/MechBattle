package Interfaces;

import Game.StartBattle;
import Game.Victory;
import Robots.Robot;
import Workshop.Guns.Gun;
import Workshop.Lasers.Laser;
import Workshop.Weapon;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public interface iFight {
       void distAttack(Robot bot, Weapon weapon) throws IOException, InterruptedException;

}
