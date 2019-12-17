package Workshop.Lasers;

public class MediumLaser extends Laser {
    public MediumLaser(boolean leftHand, boolean rightHand) {
        super("MediumLaser", 3, 6, 6, 6, 8, 7, 2);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }
}
