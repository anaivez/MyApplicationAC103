package com.gdu.myapplicationac103.allview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Debug;

import androidx.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by ZhDu on 2021/4/15 17:05.
 */
public class MyApplication extends Application {

    static List<Activity> activities;

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static List<Activity> getActivities() {
        if (activities == null) {
            return new ArrayList<>();
        }
        return activities;
    }

    public static Activity getActivity(int i) {
        return activities.get(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new ArrayList<>();
        MultiDex.install(this);
        CrashReport.initCrashReport(getApplicationContext(), "efabcf2b43", false);
        CrashReport.setUserId("103");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
