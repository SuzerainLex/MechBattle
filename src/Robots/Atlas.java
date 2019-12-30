package Robots;

public class Atlas extends Robot{
    public Atlas(String playerName, int coordinatX, int coordinatY) {
        super("Atlas", 8, 3, 50, 30, 30, 4, 2, 4, 3, 4, 4);
        this.setCoordinatX(coordinatX);
        this.setCoordinatY(coordinatY);
        this.playerName = playerName;

    }
}
