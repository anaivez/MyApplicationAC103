package com.gdu.myapplicationac103;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Description:
 * Created by Czm on 2021/5/18 17:59.
 */
public class Czm extends BC {

    private String channelId_default = "subscribe";
    private String channelId_high = "chat";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xxxxxxxx);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "消息订阅";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId_default, channelName, importance);

            channelName = "消息通知";
            importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId_high, channelName, importance);
        }
    }

    //创建通知渠道
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void onClick(View view) {
        sendNotification("");
    }

    private void sendNotification(String data) {
        Log.d(TAG, "sendNotification: ");
        String title = "一个标题";
        String content = "";
        Intent msgIntent = new Intent();
        msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = null;

        content = "一个渴望日常的普通上班族";
        // 传递数据 启动activity
        //msgIntent.putExtra("orderNo", message.getId());
        //msgIntent.setClass(getInstance(), OrderDetailsActivity.class);
        pendingIntent = PendingIntent.getActivity(getContext(),
                R.string.app_name, msgIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //主界面弹框
//       notification = new NotificationCompat.Builder(getInstance(), "chat")
            //不弹框 仅在通知栏显示
            Notification.Builder notic = new Notification.Builder(getContext(), channelId_default)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.jz_003)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jz_000))
                    .setAutoCancel(true);
            if (pendingIntent != null) {
                notic.setContentIntent(pendingIntent);
            }
            notification = notic.build();
        } else {
            //8.0以下系统创建notification
            NotificationCompat.Builder notic = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.drawable.jz_004)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jz_002))
                    .setContentTitle(title)
                    .setContentText(content)
                    .setTicker(title);
            if (pendingIntent != null) {
                notic.setContentIntent(pendingIntent);
            }
            notification = notic.build();
        }
        if (notification != null) {
            manager.notify(1, notification);
        }

    }
}
