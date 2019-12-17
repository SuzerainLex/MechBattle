package music;

import Workshop.Guns.BigGun;
import Workshop.Guns.MediumGun;
import Workshop.Guns.MiniGun;
import Workshop.Lasers.BigLaser;
import Workshop.Lasers.MediumLaser;
import Workshop.Lasers.SmallLaser;
import Workshop.Rockets.LargeDistanceRockets;
import Workshop.Rockets.SmallDistanceRockets;
import Workshop.Weapon;

public final class Sounds extends Thread {
    public final static String TRACK = "musicList\\Mix.mp3";
    public final static String ROCKETSOUND = "sounds\\rocket.mp3";
    public final static String SMALLLASERSOUND = "sounds\\smalllaser.mp3";
    public final static String MEDIUMLASERSOUND = "sounds\\smalllaser.mp3";
    public final static String BIGLASERSOUND = "sounds\\BigLaser.mp3";
    public final static String MINIGUNSOUND = "sounds\\MiniGun.mp3";
    public final static String MEDIUMGUNSOUND = "sounds\\MiddleGun.mp3";
    public final static String BIGGUNSOUND = "sounds\\BigGun.mp3";
    public final static String STEPSOUND = "sounds\\Step.mp3";
    public final static String WARNINGSOUND = "sounds\\warning.mp3";
    public final static String HITSOUND1 = "sounds\\Hit1.mp3";
    public final static String HITSOUND2 = "sounds\\Hit2.mp3";

    public static void chooseSound(Weapon weapon) throws InterruptedException {
        if (weapon instanceof MiniGun) {
            PlaySounds pM = new PlaySounds(Sounds.MINIGUNSOUND);
            explosiveSound(400);
        } else if (weapon instanceof MediumGun) {
            PlaySounds pM = new PlaySounds(Sounds.MEDIUMGUNSOUND);
            explosiveSound(1000);
        } else if (weapon instanceof BigGun) {
            PlaySounds pM = new PlaySounds(Sounds.BIGGUNSOUND);
            explosiveSound(2200);
        } else if (weapon instanceof SmallDistanceRockets || weapon instanceof LargeDistanceRockets) {
            PlaySounds pM = new PlaySounds(Sounds.ROCKETSOUND);
            explosiveSound(3000);
        } else if (weapon instanceof SmallLaser) {
            PlaySounds pM = new PlaySounds(Sounds.SMALLLASERSOUND);
            explosiveSound(500);
        } else if (weapon instanceof MediumLaser) {
            PlaySounds pM = new PlaySounds(Sounds.MEDIUMLASERSOUND);
            explosiveSound(1000);
        } else if (weapon instanceof BigLaser) {
            PlaySounds pM = new PlaySounds(Sounds.BIGLASERSOUND);
            explosiveSound(5200);
        }
    }

    public static void explosiveSound(int number) throws InterruptedException {
        Thread.sleep(number);
        int rand = (int) (Math.random() * 10);
        if (rand > 5) {
            PlaySounds pS = new PlaySounds(Sounds.HITSOUND1);
        } else {
            PlaySounds pS = new PlaySounds(Sounds.HITSOUND2);
        }
    }
}
