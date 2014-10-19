package me.pjq.soundtouch.library;

import java.io.IOException;

import me.pjq.soundtouch.util.LocalPathResolver;

/**
 * Created by pjq on 10/19/14.
 */
public class SoundTouchUtils {
    public static void play(String fullPathToAudioFile) {

        //the last two parameters are speed of playback and pitch in semi-tones.
        com.smp.soundtouchandroid.SoundTouchPlayable st = null;
        try {
            st = new com.smp.soundtouchandroid.SoundTouchPlayable(fullPathToAudioFile, 0, 1.0f, 0.0f);
            new Thread(st).start();
            st.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void play() {
        String path = LocalPathResolver.getBaseDir() + "/aliza.mp3";
        play(path);
    }

    public static void soundtouch() {
    }
}
