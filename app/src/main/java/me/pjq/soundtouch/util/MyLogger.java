package me.pjq.soundtouch.util;

import android.util.Log;

/**
 * The log util.
 *
 * @author Jianqing.Peng
 * @since 1.0
 */
public class MyLogger {
    @SuppressWarnings("unused")
    private static final String TAG = MyLogger.class.getSimpleName();
    private static final boolean DEBUG = true;

    public static void i(String tag, String message){
        Log.i(tag, message);
    }

    public static void d(String tag, String message){
        Log.i(tag, message);
    }

}
