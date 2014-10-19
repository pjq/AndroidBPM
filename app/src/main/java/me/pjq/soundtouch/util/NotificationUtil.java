
package me.pjq.soundtouch.util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import me.pjq.soundtouch.Constants;
import me.pjq.soundtouch.R;

public class NotificationUtil {
    private static final String TAG = NotificationUtil.class.getSimpleName();

    public static final int NOTIFICATION_ID = 1000;

    private static PendingIntent createPendingIntent(Context context, Class activityClass) {
        PendingIntent pendingIntent = null;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, activityClass);
        pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    @SuppressWarnings("deprecation")
    public static void showNotificationNormal(Context context, Class activityClass, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false);

        PendingIntent pi = createPendingIntent(context, activityClass);

        builder.setContentIntent(pi);
        builder.setContentTitle(context.getString(R.string.app_name) + "(" + title + ")");
        builder.setContentText(message);
        builder.setTicker(context.getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.icon);

        builder.setWhen(System.currentTimeMillis());
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        nm.notify(NOTIFICATION_ID, notification);
    }

    public static PendingIntent getTakeVideoPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_TAKE_VIDEO);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static PendingIntent getTakePicturePendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_TAKE_PICTURE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    public static PendingIntent getTakePicturePendingIntentBroadcast(Context context) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_TAKE_PICTURE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static PendingIntent getTakeVideoPendingIntentBroadcast(Context context) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_TAKE_VIDEO);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void showNotification(Context context, Class activityClass, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        boolean useBroadcast = true;
        if (useBroadcast) {
            remoteView.setOnClickPendingIntent(R.id.notification_take_picture, getTakePicturePendingIntentBroadcast(context));
            remoteView.setOnClickPendingIntent(R.id.notification_take_video, getTakeVideoPendingIntentBroadcast(context));
        } else {
            remoteView.setOnClickPendingIntent(R.id.notification_take_video, getTakeVideoPendingIntent(context));
            remoteView.setOnClickPendingIntent(R.id.notification_take_picture, getTakePicturePendingIntent(context));
        }

        PendingIntent pi = createPendingIntent(context, activityClass);
        remoteView.setOnClickPendingIntent(R.id.notification_icon, pi);

        builder.setContent(remoteView);
        builder.setAutoCancel(false);

        //builder.setContentIntent(pi);
        builder.setContentTitle(context.getString(R.string.app_name) + "(" + title + ")");
        builder.setContentText(message);
        builder.setTicker(context.getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.icon);

        builder.setWhen(System.currentTimeMillis());
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void showNotification(Context context, Class activityClass, String title, String message, boolean isMuted) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        if (isMuted) {
            remoteView.setTextViewText(R.id.notification_take_video, "MUTE ON");
        } else {
            remoteView.setTextViewText(R.id.notification_take_video, "MUTE OFF");
        }

        boolean useBroadcast = true;
        if (useBroadcast) {
            remoteView.setOnClickPendingIntent(R.id.notification_take_picture, getTakePicturePendingIntentBroadcast(context));
            remoteView.setOnClickPendingIntent(R.id.notification_take_video, getTakeVideoPendingIntentBroadcast(context));
        } else {
            remoteView.setOnClickPendingIntent(R.id.notification_take_video, getTakeVideoPendingIntent(context));
            remoteView.setOnClickPendingIntent(R.id.notification_take_picture, getTakePicturePendingIntent(context));
        }

        PendingIntent pi = createPendingIntent(context, activityClass);
        remoteView.setOnClickPendingIntent(R.id.notification_icon, pi);

        builder.setContent(remoteView);
        builder.setAutoCancel(false);

        //builder.setContentIntent(pi);
        builder.setContentTitle(context.getString(R.string.app_name) + "(" + title + ")");
        builder.setContentText(message);
        builder.setTicker(context.getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.ic_launcher);

        builder.setWhen(System.currentTimeMillis());
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void dismissNotification(Context context) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_ID);
    }
}
