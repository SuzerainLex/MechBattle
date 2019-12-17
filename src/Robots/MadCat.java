package Robots;

public class MadCat extends Robot {

    public MadCat(String playerName, int coordinatX, int coordinatY) {
        super("Mad Cat", 4, 5, 30, 20, 20, 2, 2, 2, 5, 2, 2);
        this.coordinatX = coordinatX;
        this.coordinatY = coordinatY;
        this.playerName = playerName;

    }
}

