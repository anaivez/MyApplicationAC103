package com.gdu.myapplicationac103;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Description:
 * Created by Czm on 2021/8/14 15:52.
 */
public class ADS extends AppCompatActivity {

    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    int i = 0;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddxx);
        tv = findViewById(R.id.tc);
        startService(new Intent(this, YzService.class));
        valueAnimator.setDuration(1000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                i++;
                //tv.setText("" + i);
            }
        });
        valueAnimator.start();
    }

    public void onClick(View view) {
        sendRun.run();
    }

    private static final String TAG = "ADS";

    public Handler serialPostHandler = new Handler();
    Runnable sendRun = new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
    boolean b=     isServiceRunning(getApplicationContext(),"YzService");
                Log.d(TAG, "run: "+b);
            }
            serialPostHandler.postDelayed(this, 100*10);
        }
    };
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            Log.i("服务运行1：", "" + runningService.get(i).service.getShortClassName().replace(".","").trim());
            if (runningService.get(i).service.getShortClassName().replace(".","").trim().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
