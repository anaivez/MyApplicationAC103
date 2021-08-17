package com.gdu.myapplicationac103.allview;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gdu.myapplicationac103.BC;
import com.gdu.myapplicationac103.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

;
;

/**
 * Description:
 * Created by ZhDu on 2021/5/13 16:40.
 */
public class CamreraView extends BC {

    Camera.AutoFocusCallback afcb = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean b, Camera camera) {
            if (b) {
                Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG);
                camera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {

                    }
                });
            } else {
                Toast.makeText(getBaseContext(), "faile", Toast.LENGTH_LONG);
            }
        }
    };
    ExecutorService executorService;
    AnimationDrawable mAnimationDrawable;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private View ivAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaecamera);
        ivAnim = findViewById(R.id.ivAnim);
        mAnimationDrawable = (AnimationDrawable) ivAnim.getBackground();
        //init();
    }

    private void init() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_camera2_activity);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                }
            }
        });
    }

    private void initCamera() {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(180);
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewFpsRange(30, 60);
            parameters.set("jpeg-quality", 90);
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int dd=0;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_show_camera2_activity:
                if (mCamera != null) {
                    Toast.makeText(CamreraView.this, "开始", Toast.LENGTH_SHORT).show();
                    mCamera.autoFocus(afcb);
                }
                //new TsTask().execute(5, 10, 15);
                //initExecutorService();
                break;
            case R.id.surface_view_camera2_activity:
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }else {
                    mAnimationDrawable.start();
                }
                break;
            default:
                break;
        }
    }



    private void initExecutorService() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(1);
        }
        doAsync();
    }

    private void doAsync() {
        executorService.submit(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                while (i < 10) {
                    try {
                        Thread.sleep(1000);
                        i++;
                        doOnUi();
                        Log.d(TAG, "doInBackground for " + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void doOnUi() {
        Handler uiH = new Handler(Looper.getMainLooper());
        uiH.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
            }
        });
    }


    class Task extends AsyncTask<Map<Integer, String>, ArrayList<String>, Integer> {


        @Override
        protected Integer doInBackground(Map<Integer, String>... maps) {
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<String>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }


    class TsTask extends AsyncTask<Integer, Integer, Boolean> {

        int i = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }


        @Override
        protected Boolean doInBackground(Integer... integers) {
            for (int x : integers) {
                i = 0;
                while (i < x) {
                    try {
                        Thread.sleep(1000);
                        i++;
                        publishProgress(x, i);
                        Log.d(TAG, "doInBackground for " + x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate  for  " + values[0] + " ... in " + values[1]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute: " + aBoolean);
        }
    }

}
