package com.gdu.myapplicationac103;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

/**
 * Description:
 * Created by Czm on 2021/7/30 10:03.
 */
public class SoundPoolActivity extends AppCompatActivity {

    String CHANNEL_ID = "DEASET";
    int currentSount, maxSound;
    ProgressBar pb;
    private HashMap<Integer, Integer> mSoundMap;
    private SoundPool mSoundPool;
    private AudioManager audioManager;
    private MyVolumeReceiver mVolumeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dd);
        mSoundMap = new HashMap<>();
        pb = findViewById(R.id.progressBar2);
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        loadSound(1, R.raw.p10);
        loadSound(2, R.raw.p11);
        loadSound(3, R.raw.p12);
        mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(mVolumeReceiver, filter);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        pb.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
    }

    private void startFG() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationBuilder builder = new NotificationBuilder(this);
            final Notification notification = builder.buildNotification();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(11, notification);
            //startForeground(NotificationBuilder.NOTIFICATION_ID, notification);
        }
    }

    public void onClick1(View view) {
        //playSound(1);
        //com.android.music/.TrackBrowserActivity
        Intent intent = new Intent();
        //包名 包名+类名（全路径）
        intent.setClassName("com.android.music", "com.android.music.MusicBrowserActivity");
        startActivity(intent);
    }

    public void onClick2(View view) {
        currentSount = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxSound = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取系统音量最大值
        currentSount++;
        if (currentSount > maxSound) {
            currentSount = maxSound;
        }
        //audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 3);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentSount, 0);//广播给系统的
        //audioManager.setStreamVolume(3, currentSount, 3);
    }

    public void onClick3(View view) {
        //playSound(3);
        currentSount = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        currentSount--;
        if (currentSount < 0) {
            currentSount = 0;
        }
        //audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 3);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentSount, 3);//广播给系统的
        //audioManager.setStreamVolume(3, currentSount, 3);
    }

    private void loadSound(int seq, int resid) {
        int soundID = mSoundPool.load(this, resid, 1);
        mSoundMap.put(seq, soundID);

    }

    private void playSound(int seq) {
        int soundID = mSoundMap.get(seq);
        mSoundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                pb.setProgress(currVolume);
            }
        }
    }





}
