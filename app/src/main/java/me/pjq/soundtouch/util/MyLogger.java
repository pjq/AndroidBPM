package me.pjq.soundtouch.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.pjq.soundtouch.MyApplication;


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
