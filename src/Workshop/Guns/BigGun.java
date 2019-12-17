package Workshop.Guns;

public class BigGun extends Gun{
    public BigGun(boolean leftHand, boolean rightHand) {
        super("BigGun", 6, 12, 3, 8, 4, 0 , 3);
        this.leftHand = leftHand; this.rightHand = rightHand;
    }
}
