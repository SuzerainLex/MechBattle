package Workshop.Guns;

public class MediumGun extends Gun {
    public MediumGun(boolean leftHand, boolean rightHand) {
        super("MediumGun", 3, 7, 5, 6, 6, 0, 2);
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }
}
