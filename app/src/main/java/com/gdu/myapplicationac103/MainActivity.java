package com.gdu.myapplicationac103;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.gdu.myapplicationac103.allview.HistogramView;
import com.gdu.myapplicationac103.allview.ReView;
import com.gdu.myapplicationac103.allview.RunningView;
import com.gdu.myapplicationac103.allview.TimeCount;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BC implements View.OnTouchListener, ViewTreeObserver.OnTouchModeChangeListener {

    static RunningView runningView;

    ReView reView;
    HistogramView histogramView;
    RecyclerView recyclerView;
    //TextView tvNoName;
    TimeCount timeCount;
    ImageView iv;
    ViewStub viewStub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setNavigationBarStatusBarTranslucent(this);
        findViewById(R.id.rl).setOnTouchListener(this);
        timeCount = findViewById(R.id.tvNoName);
        timeCount.setReString("");
        timeCount.setOnTimeCountListener(new TimeCount.OnTimeCountListener() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "shaco go die", Toast.LENGTH_SHORT).show();
            }
        });
        viewStub = findViewById(R.id.vsRain);
        iv = findViewById(R.id.iv);
        iv.setImageBitmap(
                Gaosi.blurBitmap(
                        this,
                        BitmapFactory.decodeResource(getResources(), R.drawable.bbb)));
        //tvNoName = findViewById(R.id.tvNoName);
        reView = findViewById(R.id.rv);
        histogramView = findViewById(R.id.hv);
        histogramView.addData((new Float[]{1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 24f, 3f, 4.5f, 29f, 5f, 9f}));
        //findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        startActivity(new Intent(getBaseContext(), AC.class));
        //    }
        //});
        //runningView = findViewById(R.id.running_rv_show);
        //runningView.upDataRec(new double[]{3,3,3,3,3,3,3,},1);
        //startService(new Intent(this, Dot.class));
        startService(new Intent(this, AIDLServer.class));
        //timeCount = new TimeCount(1000 * 15, 1000, tvNoName, this);
        //timeCount.start();
        //create();
        try {
            Field[] f = TSBean.class.getDeclaredFields();
            String sql = "create table if not exists user1(" +
                    "_id integer primary key autoincrement";
            for (Field ff : f) {
                String type = "";
                if (ff.getGenericType().toString().equals("long") || ff.getGenericType().toString().equals("float") || ff.getGenericType().toString().equals("double")) {
                    type = "float";
                } else if (ff.getGenericType().toString().equals("int")) {
                    type = "integer";
                } else if (ff.getGenericType().toString().equals("boolean")) {
                    type = "boolean";
                } else {
                    type = "text";
                }
                Log.d(TAG, "field:  " + ff.getName() + "----" + ff.getGenericType().toString());
                sql += "_" + ff.getName() + " " + type + " not null,";
            }
            Class c = Class.forName("com.gdu.myapplicationac103.TSBean.Index");
            Log.d(TAG, "onCreate: " + c.getDeclaredFields().length);
            Log.d(TAG, "sql: " + sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createShortCut() {
        Uri uri = Uri.parse("https://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //不允许重复创建
        intent.putExtra("duplicate", false);
        //需要现实的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "YOUTUBE");
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        sendBroadcast(intent);
    }

    public void create() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //    ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
        //    if (shortcutManager == null) {
        //        return;
        //    }
        //    if (shortcutManager.isRequestPinShortcutSupported()) {
        //        Uri uri = Uri.parse("https://www.youtube.com/");
        //        Intent create = new Intent(Intent.ACTION_VIEW, uri);
        //        create.putExtra(Intent.EXTRA_SHORTCUT_NAME, "RKO2");
        //        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.bt_float);
        //        ShortcutInfo info = new ShortcutInfo.Builder(this, "NoMad")
        //                .setIcon(Icon.createWithResource(this, 13))  //我自己的是int类型大家可以看源码改bitmap类型等
        //                .setShortLabel("YOUTUBE")
        //                .setIntent(create)
        //                .build();
        //        PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(this, 0, create, PendingIntent.FLAG_UPDATE_CURRENT);
        //        shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
        //    }
        //}
        //
        //BoxingMediaLoader.getInstance().init(new IBoxingMediaLoader() {
        //    @Override
        //    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        //
        //    }
        //
        //    @Override
        //    public void displayRaw(@NonNull ImageView img, @NonNull String absPath, int width, int height, IBoxingCallback callback) {
        //
        //    }
        //});
        //Uri uri = Uri.parse("https://www.youtube.com/");
        //Intent create = new Intent(Intent.ACTION_VIEW, uri);
        //create.putExtra(Intent.EXTRA_SHORTCUT_NAME, "RKO");
        //Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher);
        //create.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //sendBroadcast(create);
    }

    public String getTime() {
        return reView.getTime();
    }

    /**
     * 么么屋
     *
     * @param view
     */
    public void onTimeClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                reView.start();
                startActivity(new Intent(this, RainActivity.class));
                Log.d(TAG, "onTimeClick:   " + TAG);
                break;
            case R.id.btn2:
                reView.pause();
                viewStub.inflate();
                break;
            case R.id.btn3:
                reView.stop();
                try {
                    Class<?> clazzClass = ReView.class;
                    Field[] fs = clazzClass.getDeclaredFields();
                    for (Field f : fs) {
                        try {
                            f.setAccessible(true);
                            Log.d(TAG, "onTimeClick on s**t : " + f.getName() + ":" + f.get(ReView.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        List<Float> moveY = new ArrayList<>();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //startActivity(new Intent(getBaseContext(), AC.class));
                break;
            case MotionEvent.ACTION_MOVE:
                moveY.add(motionEvent.getY());
                int isUP = 1;
                if (moveY.size() >= 4) {
                    if (moveY.get(moveY.size() - 3) < moveY.get(moveY.size() - 2)) {
                        if (moveY.get(moveY.size() - 2) < moveY.get(moveY.size() - 1)) {
                            isUP = 1;
                        }
                    }
                    if (moveY.get(moveY.size() - 3) > moveY.get(moveY.size() - 2)) {
                        if (moveY.get(moveY.size() - 2) > moveY.get(moveY.size() - 1)) {
                            isUP = 0;
                        }
                    }
                }
                Log.d(TAG, "onTouch   up:" + isUP + "");
                break;
            case MotionEvent.ACTION_UP:
                moveY.clear();
                break;
        }
        return true;
    }

    @Override
    public void onTouchModeChanged(boolean b) {
        Log.d(TAG, "onTouchModeChanged: " + b);
    }
}
