package me.pjq.soundtouch.library;

import com.smp.soundtouchandroid.SoundTouchPlayable;

import java.io.IOException;

import me.pjq.soundtouch.util.LocalPathResolver;
import me.pjq.soundtouch.util.MyLogger;

/**
 * Created by pjq on 10/19/14.
 */
public class SoundTouchUtils {
    private static final String TAG = SoundTouchUtils.class.getSimpleName();

    public static void play(String fullPathToAudioFile) {
        MyLogger.i(TAG, "play " + fullPathToAudioFile);

        SoundTouchPlayable.OnProgressChangedListener listener = new SoundTouchPlayable.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int track, double currentPercentage, long position) {
                //MyLogger.i(TAG, "onProgressChanged, track = " + track + ", currentPercentage = " + currentPercentage + ", position = " + position);

            }

            @Override
            public void onTrackEnd(int track) {
                MyLogger.i(TAG, "onTrackEnd, track = " + track);

            }
        };
        //the last two parameters are speed of playback and pitch in semi-tones.
        try {
            final com.smp.soundtouchandroid.SoundTouchPlayable st = new com.smp.soundtouchandroid.SoundTouchPlayable(listener, fullPathToAudioFile, 0, 1.1f, 0.0f);
            new Thread(st).start();
            st.play();
            MyLogger.i(TAG, "getBPM in thread");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    float bpm = st.getBPM();
                    MyLogger.i(TAG, "bpm = " + bpm);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void play() {
        String path = LocalPathResolver.getBaseDir() + "/aliza.mp3";
        play(path);
    }

}
