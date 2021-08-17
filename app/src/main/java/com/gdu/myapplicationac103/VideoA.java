package com.gdu.myapplicationac103;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotSaveListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;

/**
 * Description:
 * Created by Czm on 2021/5/21 11:25.
 */
public class VideoA extends BC {

    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vvvvvvvvvvvvvvv);
        //videoPlayer = findViewById(R.id.videoView);
        //Debuger.disable();
        //model_V1();


    }

    private void model_V1() {
        //String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String source1 = "http://39.108.225.155/vc_tp/vc_file/apks/other/count_down.mp4";
        videoPlayer.setUp(source1, true, "wake up");

        //增加封面
        //ImageView imageView = new ImageView(this);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageResource(R.mipmap.ic_launcher);
        //videoPlayer.setThumbImageView(imageView);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        ////设置旋转
        //orientationUtils = new OrientationUtils(this, videoPlayer);
        //
        ////设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        //videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        orientationUtils.resolveByClick();
        //    }
        //});
        videoPlayer.setLooping(true);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        try {
            //先返回正常状态
            if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                videoPlayer.getFullscreenButton().performClick();
                return;
            }
            //释放所有
            videoPlayer.setVideoAllCallBack(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }


    public void onClick(View view) {
        if (videoPlayer!=null) {


        videoPlayer.saveFrame(new File("mnt/sdcard/tm" + System.currentTimeMillis() + ".png"), new GSYVideoShotSaveListener() {
            @Override
            public void result(boolean success, File file) {
                Log.d(TAG, "result: " + videoPlayer.getNetSpeedText());
            }
        });   }
        SoundPool soundPool;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setMaxStreams(10);
            soundPool = spb.build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        //int videoId=soundPool.load("mnt/sdcard/do.mp3", 23);
        //soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
        //    @Override
        //    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        //        soundPool.play(videoId,2,10,1000,0,1);
        //    }
        //});

    }


}
