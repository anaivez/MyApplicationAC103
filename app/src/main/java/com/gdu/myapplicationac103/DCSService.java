package com.gdu.myapplicationac103;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Description:
 * Created by Czm on 2021/8/20 14:56.
 */
public class DCSService extends Service {

    public static int lovnum = 9;
    public static List<IWantTrue> wantTrue = new ArrayList<>();
    private static RelativeLayout roundLayout;
    public Handler serialPostHandler = new Handler();
    Runnable sendRun = new Runnable() {
        @Override
        public void run() {
            if (wantTrue != null) {
                for (IWantTrue iWantTrue : wantTrue) {
                    iWantTrue.numChange(1, lovnum);
                }
            }
            if (tv!=null) {
                tv.setText(""+lovnum);
            }
            serialPostHandler.postDelayed(this, 100 * 1);
        }
    };
    WindowManager mWindowManagers = null;
    WindowManager.LayoutParams wmParamss;
    IWantFree iWantFree;
    TextView tv;

    public static void setiWantTrue(IWantTrue iWantTrue) {
        wantTrue.add(iWantTrue);
    }

    public static void add() {
        lovnum++;
    }

    public static void sub() {
        lovnum--;
    }

    public static void setNum(int num) {
        lovnum = num;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendRun.run();
        iWantFree = new IWantFreeImpl();
        createNotificationChannel();
        createFloatImageView();
    }

    //??????????????????
    private void createFloatImageView() {
        //????????????WindowManagerImpl.CompatModeWrapper
        mWindowManagers = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParamss = new WindowManager.LayoutParams();
        // ????????????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParamss.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParamss.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParamss.format = PixelFormat.RGBA_8888;//??????????????????????????????????????????
        wmParamss.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParamss.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT; //???????????????????????????
        wmParamss.width = WRAP_CONTENT;
        wmParamss.height = WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        roundLayout = (RelativeLayout) inflater.inflate(R.layout.window_round2, null);
        mWindowManagers.addView(roundLayout, wmParamss);
        roundLayout.findViewById(R.id.tvAdd).setOnClickListener(new AD());
        roundLayout.findViewById(R.id.tvSub).setOnClickListener(new AD());
        tv = roundLayout.findViewById(R.id.tvNum);

    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // ???????????????id
            String id = "my_channel_01";
            // ??????????????????????????????????????????.
            CharSequence name = getString(R.string.app_name);
//         ??????????????????????????????????????????
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(id, name, importance);
//         ???????????????????????????
            mChannel.setDescription(description);
//         ??????????????????????????????????????? android ?????????????????????
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
//         ??????????????????????????????????????? android ?????????????????????
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         ?????????notificationmanager???????????????????????? //
            mNotificationManager.createNotificationChannel(mChannel);

            // ????????????????????????id
            int notifyID = 1;
            // ???????????????id
            String CHANNEL_ID = "my_channel_01";
            // Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("New Message").setContentText("You've received new messages.")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setChannelId(CHANNEL_ID)
                    .build();
            startForeground(1, notification);
        }
    }

    class AD implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvAdd) {
                iWantFree.add();
            } else {
                iWantFree.sub();
            }
        }
    }
}
