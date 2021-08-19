package com.gdu.myapplicationac103;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Description:
 * Created by ZhDu on 2021/4/9 9:53.
 */
public class Dot extends Service implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "Dot";
    static DisplayMetrics dm;
    private static RelativeLayout roundLayout;
    private static LinearLayout mFloatLayout;
    private static MainActivity mainActivity;
    public Handler serialPostHandler = new Handler();
    boolean isMove;
    WindowManager mWindowManagers = null;
    WindowManager.LayoutParams wmParamss;
    int widthsss;
    int heightsss;
    @SuppressLint("HandlerLeak")
    Handler backHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: back");
            final KeyEvent evDown = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 4, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);

            final KeyEvent evUp = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 4, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);
            Object object = new Object();
            Method getInstance;
            Method injectInputEvent;
            try {
                getInstance = Class.forName("android.hardware.input.InputManager").getMethod("getInstance");
                InputManager mInputManager = (InputManager) getInstance.invoke(object);
                injectInputEvent = Class.forName("android.hardware.input.InputManager").getMethod("injectInputEvent", InputEvent.class, int.class);
                injectInputEvent.invoke(mInputManager, evDown, 0);
                injectInputEvent.invoke(mInputManager, evUp, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    TextView tv;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String speed = (String) msg.obj;
            if (tv != null) {
                tv.setText(speed);
            }
            //打印你所需要的网速值，单位默认为kb/s
            //Log.i(TAG, "current net speed  = " + speed);
        }
    };
    boolean isShow = false;
    Runnable sendRun = new Runnable() {
        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean canDrawOverlays = Settings.canDrawOverlays(getApplicationContext());
                if (canDrawOverlays) {
                    createFloatImageView();
                } else {
                    serialPostHandler.postDelayed(this, 100 * 10);
                }
                Log.d(TAG, "run  canDrawOverlays: " + canDrawOverlays);
            }
        }
    };
    private ImageView sub_iv;
    private boolean isClicks;
    private float x;
    private float y;
    private NetSpeedTimer mNetSpeedTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        initData();
    }

    private void initData() {
        mainActivity = new MainActivity();
        mNetSpeedTimer = new NetSpeedTimer(this, new NetSpeed(), handler).setDelayTime(1000).setPeriodTime(2000);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer.startSpeedTimer();
        Log.d(TAG, "initData: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean canDrawOverlays = Settings.canDrawOverlays(getApplicationContext());
            Log.d(TAG, "initData   canDrawOverlays : " + canDrawOverlays);
            if (canDrawOverlays) {
                createFloatImageView();
            } else {
                sendRun.run();
            }
        }
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

    //创建圆点菜单
    private void createFloatImageView() {
        if (isShow) {
            return;
        }
        isShow = true;
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
        roundLayout = (RelativeLayout) inflater.inflate(R.layout.window_round, null);
        mWindowManagers.addView(roundLayout, wmParamss);
        tv = roundLayout.findViewById(R.id.tvNet);
        //sub_iv = (ImageView) roundLayout.findViewById(R.id.sub_iv);
        //sub_iv.setOnClickListener(this);
        //sub_iv.setOnTouchListener(this);
        //sub_iv.setOnLongClickListener(new View.OnLongClickListener() {
        //    @Override
        //    public boolean onLongClick(View v) {
        //        isMove = true;
        //        return false;
        //    }
        //});
        roundLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {

    }

    private void onUmbrella(){

    }

    private void updateViewPosition() {
//        更新浮动窗口位置参数
//        wmParam.x = (int) Math.abs(mTouchStartX - x);
        dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：3200px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：1280px）
        widthsss = screenWidth - roundLayout.getWidth() * 5 / 2;
        heightsss = screenHeight / 2 - roundLayout.getWidth() * 135 / 100;
        wmParamss.x = (int) (screenWidth - x - roundLayout.getWidth() / 2);
        wmParamss.y = (int) (y - screenHeight / 2);
//        wmParam.y = (int) Math.abs(y - mTouchStartY);
        mWindowManagers.updateViewLayout(roundLayout, wmParamss);
    }

    private void facing() {//自动贴边
        new Thread() {
            @Override
            public void run() {
                super.run();
                dm = new DisplayMetrics();
                dm = getResources().getDisplayMetrics();
                for (; ; ) {
                    boolean isBack = false;
                    //循环条件
                    if (wmParamss.x > dm.widthPixels / 2) {//往左边靠
                        wmParamss.x += 10;
                    } else {//往右边靠
                        wmParamss.x -= 10;
                    }
                    if (wmParamss.x > (widthsss + roundLayout.getWidth() * 3 / 2)) {//往左边靠
                        isBack = true;
                        wmParamss.x = widthsss + roundLayout.getWidth() * 3 / 2;

                    } else if (wmParamss.x < 0) {//往右边靠
                        isBack = true;
                        wmParamss.x = -2;

                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWindowManagers.updateViewLayout(roundLayout, wmParamss);
                        }
                    });

                }
            }
        }.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                isClicks = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMove) {
                    isClicks = false;
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                    updateViewPosition();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP:ACTION_UP");
                if (!isClicks) {
                    facing();
                    isClicks = true;
                }
                //startFG();
                backHandler.sendEmptyMessageDelayed(0, 1);
                break;
        }
        return isMove;
    }

    private void startFG() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationBuilder builder = new NotificationBuilder(this);
            final Notification notification = builder.buildNotification();
            startForeground(NotificationBuilder.NOTIFICATION_ID, notification);
        }
    }
}
