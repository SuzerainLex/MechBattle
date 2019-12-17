package Robots;

public class Locust extends Robot{

    public Locust(String playerName, int coordinatX, int coordinatY) {
        super("Locust", 2, 7, 30, 18, 15, 2, 1, 2, 7, 2 , 2 );
        this.coordinatX = coordinatX;
        this.coordinatY = coordinatY;
        this.playerName = playerName;
    }
}
