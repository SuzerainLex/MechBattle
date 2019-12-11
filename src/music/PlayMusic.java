package music;

import javazoom.jl.player.advanced.*;
import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class PlayMusic extends Thread {
    public static AdvancedPlayer explay;
    public static String track1 = "musicList\\warpigs.mp3";
    public static String track2 = "musicList\\hand.mp3";

    @Override
    public void run() {

        try (InputStream potok = new FileInputStream(track1); InputStream potok1 = new FileInputStream(track2)) {
            AudioDevice auDev = new JavaSoundAudioDevice();
            AudioDevice auDev1 = new JavaSoundAudioDevice();
            explay = new AdvancedPlayer(potok, auDev);
            explay.play();
            explay = new AdvancedPlayer(potok1, auDev1);
            explay.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
