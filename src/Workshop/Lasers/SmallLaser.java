package Workshop.Lasers;

public class SmallLaser extends Laser {
    public SmallLaser(boolean leftHand, boolean rightHand) {
        super("SmallLaser", 1, 3, 6, 5, 10, 5, 1);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }
}
