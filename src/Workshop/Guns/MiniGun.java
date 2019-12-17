package Workshop.Guns;

public class MiniGun extends Gun {
    public MiniGun(boolean leftHand, boolean rightHand) {
        super("MiniGun", 1, 4, 7, 5, 10, 0, 1);
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }
}
