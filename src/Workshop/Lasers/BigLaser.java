package Workshop.Lasers;

public class BigLaser extends Laser {
    public BigLaser(boolean leftHand, boolean rightHand) {
        super("BigLaser", 6, 10, 4, 8, 6, 10, 3);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }
}
