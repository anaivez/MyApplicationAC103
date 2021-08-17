package com.gdu.myapplicationac103;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * Description:
 * Created by Czm on 2021/7/9 15:32.
 */
public class NotificationBuilder {
    public static final String NOTIFICATION_CHANNEL_ID = "com.demo.CHANNEL_ID";
    public static final int NOTIFICATION_ID = 0xD660;

    private final Context mContext;
    private final NotificationManager mNotificationManager;

    public NotificationBuilder(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public Notification buildNotification() {
        if (shouldCreateNowPlayingChannel()) {
            createNowPlayingChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);

        return builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("")
                .setContentTitle("")
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean nowPlayingChannelExists() {
        return mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null;
    }

    private boolean shouldCreateNowPlayingChannel() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowPlayingChannelExists();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNowPlayingChannel() {
        final NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "dot",
                NotificationManager.IMPORTANCE_HIGH);
        mNotificationManager.createNotificationChannel(channel);
    }
}