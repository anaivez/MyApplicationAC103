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

    //创建圆点菜单
    private void createFloatImageView() {
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManagers = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParamss = new WindowManager.LayoutParams();
        // 设置窗体显示类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParamss.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParamss.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParamss.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
        wmParamss.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParamss.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT; //调整悬浮窗显示位置
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
            // 通知渠道的id
            String id = "my_channel_01";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getString(R.string.app_name);
//         用户可以看到的通知渠道的描述
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
            mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         最后在notificationmanager中创建该通知渠道 //
            mNotificationManager.createNotificationChannel(mChannel);

            // 为该通知设置一个id
            int notifyID = 1;
            // 通知渠道的id
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
