package music;

import javazoom.jl.player.advanced.*;
import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class PlaySounds extends Thread {
    private static AdvancedPlayer explay;
    private String sound;

    public PlaySounds(String sound) {
        this.sound = sound;
        start();
    }


    public void run() {
            try (InputStream potok = new FileInputStream(sound)) {
                AudioDevice auDev = new JavaSoundAudioDevice();
                explay = new AdvancedPlayer(potok, auDev);
                explay.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

