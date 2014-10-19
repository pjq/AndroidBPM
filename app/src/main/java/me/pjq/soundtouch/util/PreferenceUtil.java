package me.pjq.soundtouch.util;

import android.content.Context;
import android.content.SharedPreferences;

import me.pjq.soundtouch.MyApplication;


/**
 * Created by pengjianqing on 7/2/14.
 */
public class PreferenceUtil {
    private static PreferenceUtil INSTANCE;

    private static final String PREFERENCE = "omniture";
    private static final String PREF_CAPTURE_X = "captureX";
    private static final String PREF_CAPTURE_Y = "captureY";
    private static final String PREF_SWITCH_X = "switchX";
    private static final String PREF_SWITCH_Y = "switchY";

    //Cache some values.
    private int captureX;
    private int captureY;
    private int switchX;
    private int switchY;

    public PreferenceUtil() {
        captureX = get(PREF_CAPTURE_X, 0);
        captureY = get(PREF_CAPTURE_Y, 0);
        switchX = get(PREF_SWITCH_X, 0);
        switchY = get(PREF_SWITCH_Y, 0);
        MyLogger.i(PreferenceUtil.class.getSimpleName(), "captureX = " + captureX + ", captureY = " + captureY + ", switchX = " + switchX + ", switchY = " + switchY);
    }

    public static PreferenceUtil getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new PreferenceUtil();
        }

        return INSTANCE;
    }

    private SharedPreferences.Editor editor() {
        SharedPreferences.Editor editor = preferences().edit();
        return editor;
    }

    private SharedPreferences preferences() {
        Context context = MyApplication.getContext();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

        return preferences;
    }

    private void set(String key, String values) {
        editor().putString(key, values).commit();
    }

    private void set(String key, int values) {
        editor().putInt(key, values).commit();
    }

    private void set(String key, boolean values) {
        editor().putBoolean(key, values).commit();
    }

    private int get(String key, int defValue) {
        return preferences().getInt(key, defValue);
    }

    private String get(String key, String defValue) {
        return preferences().getString(key, defValue);
    }

    private boolean get(String key, boolean defValue) {
        return preferences().getBoolean(key, defValue);
    }

    public int getCaptureX() {
        return captureX;
    }

    public void setCaptureY(int value) {
        set(PREF_CAPTURE_Y, value);
        captureY = value;
    }

    public void setCaptureX(int value) {
        set(PREF_CAPTURE_X, value);
        captureX = value;
    }

    public int getCaptureY() {
        return captureY;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int value) {
        set(PREF_SWITCH_X, value);
        switchX = value;
    }

    public int getSwitchY() {
        return switchY;
    }

    public void setSwitchY(int value) {
        set(PREF_SWITCH_Y, value);
        switchY = value;
    }

    public void clear() {
        editor().clear().commit();
    }

}
