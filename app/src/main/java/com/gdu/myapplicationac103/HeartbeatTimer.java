package com.gdu.myapplicationac103;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * Created by Czm on 2021/7/29 15:05.
 */
public class HeartbeatTimer {


    private Timer timer;
    private TimerTask task;
    private OnScheduleListener mListener;

    public HeartbeatTimer() {
        timer = new Timer();
    }

    public void startTimer(long delay, long period) {
        task = new TimerTask() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onSchedule();
                }
            }
        };
        timer.schedule(task, delay, period);
    }

    public void exit() {
        if (timer != null) {
            timer.cancel();
        }
        if (task != null) {
            task.cancel();
        }
    }

    public interface OnScheduleListener {
        void onSchedule();
    }

    public void setOnScheduleListener(OnScheduleListener listener) {
        this.mListener = listener;
    }

}
