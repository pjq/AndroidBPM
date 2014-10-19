package me.pjq.soundtouch.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalPathResolver {

    private static final String TAG = LocalPathResolver.class.getSimpleName();

    private static final String BASE_DIR = "/androidsoundtouch";
    private static String base;

    public static void init(Context context) {
        String baseDir = getDir(context);
        LocalPathResolver.base = baseDir;

        File file = new File(getBaseDir());
        if (!file.isDirectory()) {
            boolean result = file.mkdirs();
            if (result) {

            }
        }
    }

    // Gets the root file storage directory.
    private static String getDir(Context context) {
        String base;
        boolean usingSdcard = true;
        if (usingSdcard) {
            base = Environment.getExternalStorageDirectory().getPath();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                base = context.getExternalCacheDir().getPath();
            } else {
                File filesDir = context.getApplicationContext().getFilesDir();
                base = filesDir.getPath();
            }
        }

        return base;
    }

    public static String getBaseDir() {
        return base + BASE_DIR;
    }

    public static String getVideoDir() {
        return getOutputMediaFile(MEDIA_TYPE_VIDEO).getAbsolutePath();
    }

    public static String getImageDir() {
        return getOutputMediaFile(MEDIA_TYPE_IMAGE).getAbsolutePath();
    }

    public static String getLogDir() {
        return getBaseDir() + "/log";
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(getBaseDir() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(getBaseDir() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
