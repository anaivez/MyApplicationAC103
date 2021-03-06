package com.gdu.myapplicationac103;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.ALog;
import com.gdu.myapplicationac103.allview.HistogramViewV3;
import com.gdu.myapplicationac103.allview.KeepBtton;
import com.gdu.myapplicationac103.allview.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Czm on 2021/5/28 10:18.
 */
public class Dym extends BC {

    private static final String TAG = "Dym";
    public Handler serialPostHandler = new Handler();
    TextView textView;
    RTextView rTextView;
    Handler hh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    LoadingView loadingView;
    SeekBar seekBar;
    int i = 0;
    IntentFilter intentFilter;
    KeepBtton keepBtton;
    HistogramViewV3 histogramViewV2;
    int count = 30;
    int money = 1000;
    RTextView rtv;
    float x, y;
    List<Map<String, Float>> list = new ArrayList<>();
    List<List<Map<String, Float>>> history = new ArrayList<>();
    Runnable sendRun = new Runnable() {
        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean canDrawOverlays = Settings.canDrawOverlays(getContext());
                if (canDrawOverlays) {
                    Intent intent = new Intent(getContext(), Dot.class);
                    startService(intent);
                }
                Log.d(TAG, "run  canDrawOverlays: " + canDrawOverlays);
            }
            serialPostHandler.postDelayed(this, 100 * 10);
        }
    };
    private MyUsbBroad myUsb;
    private WifiManager wifiManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        StringBuffer sb = new StringBuffer();

        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            sb = new StringBuffer();
            for (ScanResult sr : scanResults) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    sb.append("-" + sr.venueName);
                }
            }
            Log.d(TAG, "onReceive: " + sb.toString());
        }
    };

    public static void onActivityResult(Activity activity,
                                        int requestCode,
                                        int resultCode,
                                        Intent data,
                                        OnWindowPermissionListener onWindowPermissionListener) {
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !Settings.canDrawOverlays(activity)) {
                Toast.makeText(activity.getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
                if (onWindowPermissionListener != null)
                    onWindowPermissionListener.onFailure();
            } else {
                Toast.makeText(activity.getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
                if (onWindowPermissionListener != null)
                    onWindowPermissionListener.onSuccess();
            }
        }
    }

    public boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(activity)) {
            Toast.makeText(activity, "???????????????????????????", Toast.LENGTH_SHORT).show();
            activity.startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + activity.getPackageName())), 0);
            return false;
        }
        return true;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Debug.startMethodTracing("app_trace");
        setContentView(R.layout.noc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent().setClass(this, Dot.class));
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(broadcastReceiver, filter);
        histogramViewV2 = findViewById(R.id.hv2);
        loadingView = findViewById(R.id.lv);
        seekBar = findViewById(R.id.seekBar);
        keepBtton = findViewById(R.id.keep);
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LServer.startInstall();
            }
        });
        findViewById(R.id.lv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loadingView.setGe(10 + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        myUsb = new MyUsbBroad();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme("file");
        registerReceiver(myUsb, intentFilter);
        checkPermission(this);
        rtv = findViewById(R.id.rtv);
        rtv.setText("Nullecception", "zxczxc");
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    if (!Settings.canDrawOverlays(getApplicationContext())) {
        //        //??????Activity???????????????
        //        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        //        intent.setData(Uri.parse("package:" + getPackageName()));
        //        startActivityForResult(intent, 100);
        //    } else {
        //        Intent intent = new Intent(this, Dot.class);
        //        startService(intent);
        //    }
        //}
// ??????????????????????????????????????????????????????
// ???????????????????????????4??????????????????????????????????????????Aria?????????????????????
//        Aria.get(this).getDownloadConfig().setMaxTaskNum(3);
//        Aria.download(this)
//                .load("http://39.108.225.155/vc_tp/vc_file/apks/other/Netflix.apk") // ????????????
//                .setFilePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/Netflix.apk") // ????????????????????????
//                .create();
        keepBtton.setColor(Color.RED);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        findViewById(R.id.bb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName())), 0);

                //Intent intent = new Intent();
                //?????? ??????+?????????????????????
                //intent.setClassName("com.softwinner.fireplayer", "com.softwinner.fireplayer.ui.FourKMainActivity");
                //startActivity(intent);

                //new Thread(new Runnable() {
                //    @Override
                //    public void run() {
                //        buyTick2(-100);
                //
                //        //for (int i = 0; i < 5; i++) {
                //        //    buyTick("A");
                //        //}
                //    }
                //}).start();
                //new Thread(new Runnable() {
                //    @Override
                //    public void run() {
                //        buyTick2(100);
                //        //for (int i = 0; i < 5; i++) {
                //        //    buyTick("B");
                //        //}
                //    }
                //}).start();
                //new Thread(new Runnable() {
                //    @Override
                //    public void run() {
                //        for (int i = 0; i < 5; i++) {
                //            buyTick("C");
                //        }
                //    }
                //}).start();
            }
        });
        keepBtton.setKeepListener(new KeepBtton.KeepListener() {
            @Override
            public void onClick() {
                Log.d(TAG, "onClick: ");
                //wifiManager.startScan();

            }

            @Override
            public void onBegin() {
                Log.d(TAG, "onBegin: " + System.currentTimeMillis());
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: " + System.currentTimeMillis());

            }
        });
        histogramViewV2.setDatas(new Float[]{1f, 2.5f, 4f, 5.5f, 7f, 8.5f, 10f, 11.5f, 13f, 15f});
    }

    private synchronized void buyTick(String user) {
        int i = count;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i--;
        count = i;
        Log.d(TAG, user + " buyTick now count:" + count);
    }

    private synchronized void buyTick2(int mmoney) {
        int i = money;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = i + mmoney;
        money = i;
        Log.d(TAG, mmoney + " tick now money:" + money);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        if (task.getKey().equals("http://39.108.225.155/vc_tp/vc_file/apks/other/Netflix.apk")) { // ?????????????????????????????????
            ALog.d(TAG, "isRunning");
            //progress.setProgress(task.getPercent()); // ?????????????????????
            //speed.setSpeed(task.getConvertSpeed()); // ????????????
            Log.d(TAG, "progress: " + task.getPercent() + "  speed" + task.getConvertSpeed());
        }
    }

    /*
     * ????????????
     */
    @Download.onTaskComplete
    protected void taskComplete(DownloadTask task) {
        if (task.getKey().equals("http://39.108.225.155/vc_tp/vc_file/apks/other/Netflix.apk")) {
            Log.d(TAG, "taskComplete: ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.d(TAG, "onActivityResult: ??????DOT");
                } else {
                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION??????????????????", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.stopMethodTracing();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int count = event.getPointerCount();
        int action = (event.getAction() & MotionEvent.ACTION_MASK) % 5;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                list.clear();
                for (int i = 0; i < count; i++) {
                    x = event.getX(count);
                    y = event.getY(count);
                    Map<String, Float> map = new HashMap<>();
                    map.put("x", x);
                    map.put("y", y);
                    list.add(map);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                history.add(list);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * "NAME varchar(20)," +
     * "TIME integer," +
     * "CALORIE integer," +
     * "MILEAGE integer," +
     * "AVG_VELOCITY integer,"+
     * "CREATE_DATE interger
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnA:
               long id= SQLData.getInstance(getContext()).install();
                Log.d(TAG, "onClick: "+id);
                break;
            case R.id.btnB:
               boolean b= SQLData.getInstance(getContext()).del(1);
                Log.d(TAG, "onClick: "+b);
                break;
            case R.id.btnC:
                List<User> userList = SQLData.getInstance(getContext()).select();
                for (User u : userList) {
                    Log.d(TAG, "onClick: "+u.toString());
                }
                break;
            case R.id.btnD:
                break;
        }
    }

    public interface OnWindowPermissionListener {
        void onSuccess();

        void onFailure();
    }

    //google package   com.android.vending
    public class MyUsbBroad extends BroadcastReceiver {

        public MyUsbBroad() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: USB");
            Toast.makeText(Dym.this, "UUUUUUUUUUUSSSSSBB", Toast.LENGTH_SHORT).show();
            if ((Intent.ACTION_MEDIA_MOUNTED).equals(intent.getAction())) {
                String path = intent.getDataString();
                String pathString = path.split("file://")[1];
                Log.d(TAG, "onReceive: ?????? " + pathString);
            }

            if ((Intent.ACTION_MEDIA_EJECT).equals(intent.getAction())) {
                Log.d(TAG, "onReceive: ??????");
            }
        }
    }
}
