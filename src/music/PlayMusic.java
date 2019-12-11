package music;
import javazoom.jl.player.advanced.*;
import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class PlayMusic extends Thread{
    public static AdvancedPlayer explay;
    public static String muss1 = "Music\\warpigs.mp3";
    public static String muss2 = "Music\\hand.mp3";
    @Override
    public void run() {

        try{
            InputStream potok = new FileInputStream(muss1);
            AudioDevice auDev = new JavaSoundAudioDevice();
            explay = new AdvancedPlayer(potok,auDev);
            explay.play();
            InputStream potok1 = new FileInputStream(muss2);
            AudioDevice auDev1 = new JavaSoundAudioDevice();
            explay = new AdvancedPlayer(potok1,auDev1);
            explay.play();
        }catch(Exception err){err.printStackTrace();}

    }
}
