package Robots;

public class Thor extends Robot {
      public Thor(String playerName, int coordinatX, int coordinatY) {
        super("Thor", 6, 4, 35, 25, 20, 2, 2, 2, 4, 2 ,2);
          this.setCoordinatX(coordinatX);
          this.setCoordinatY(coordinatY);
        this.playerName = playerName;
    }
}

