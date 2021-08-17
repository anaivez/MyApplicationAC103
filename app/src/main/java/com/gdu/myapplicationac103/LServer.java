package com.gdu.myapplicationac103;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Description:
 * Created by Czm on 2021/5/31 11:50.
 */
public class LServer extends Service {

    private static final String TAG = "LServer";
    private static String INSTALL_CMD = "install";
    private static String STOP_CMD = "disable-user";
    static Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (stopApp("com.android.vending")) {
                Log.d(TAG, "stop: success    to install google play");
                if (install("mnt/sdcard/Download/google-play.apk")) {
                    Log.d(TAG, "install success ");
                } else {
                    Log.d(TAG, "install failed");
                }
            } else {
                Log.d(TAG, "onClick: failed");
            }

            if (stopApp("com.google.android.gms")) {
                Log.d(TAG, "stop  success   to install google play services");
                if (install("mnt/sdcard/Download/google-play-services.apk")) {
                    Log.d(TAG, "install success ");
                } else {
                    Log.d(TAG, "install failed");
                }
            } else {
                Log.d(TAG, "onClick: failed");
            }
        }
    });

    private static String UNINSTALL_CMD = "uninstall";
    Handler handler = new Handler();
    int unreactedIndex = 0;
    Runnable receiveRun = new Runnable() {
        @Override
        public void run() {
            if (true) {
                unreactedIndex++;
            } else {
                unreactedIndex = 0;
            }
            if (unreactedIndex >= 10) {
                //???
            }
            handler.postDelayed(this, 1000); //循环调用
        }
    };

    public static void startInstall() {
        //thread.run();
        new At().execute("com.android.vending", "com.google.android.gms");
    }

    /**
     * 21      * APK静默安装
     * 22      *
     * 23      * @param apkPath
     * 24      *            APK安装包路径
     * 25      * @return true 静默安装成功 false 静默安装失败
     * 26
     */
    private static boolean install(String apkPath) {
        Log.d(TAG, "install: " + apkPath);
        String[] args = {"pm", INSTALL_CMD, "-r", apkPath};
        String result = apkProcess(args);
        if (result != null
                && (result.endsWith("Success") || result.endsWith("Success\n"))) {
            return true;
        }
        return false;
    }

    ;

    /**
     * 57      * 应用安装、卸载处理
     * 58      *
     * 59      * @param args
     * 60      *            安装、卸载参数
     * 61      * @return Apk安装、卸载结果
     * 62
     */
    private static String apkProcess(String[] args) {
        String result = null;
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    private static boolean uninstall(String packageName) {
        String[] args = {"pm", UNINSTALL_CMD, packageName};
        String result = apkProcess(args);
        Log.d(TAG, "uninstall: " + result);
        if (result != null
                && (result.endsWith("Success") || result.endsWith("Success\n"))) {
            return true;
        }
        return false;
    }

    private static boolean stopApp(String packageName) {
        Log.d(TAG, "stopApp: " + packageName);
        String[] args = {"pm", STOP_CMD, packageName};
        String result = apkProcess(args);
        Log.d(TAG, "stopApp: " + result);
        if (result != null
                && (result.endsWith("Package " + packageName + " new state: disabled-user") || result.endsWith("Package " + packageName + " new state: disabled-user\n"))) {
            return true;
        }
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiveRun.run();//接收线程
    }

    static class At extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            for (String packageName : strings) {
                if (stopApp(packageName)) {
                    Log.d(TAG, "stop: success    to install  " + packageName);
                    if (packageName.equals("com.android.vending")) {
                        if (install("mnt/sdcard/Download/google-play.apk")) {
                            Log.d(TAG, "install google-play success");
                        } else {
                            Log.d(TAG, "install google-play failed");
                        }
                    } else {
                        if (install("mnt/sdcard/Download/google-play-services.apk")) {
                            Log.d(TAG, "install google-play-services success");
                        } else {
                            Log.d(TAG, "install google-play-services failed");
                        }
                    }

                } else {
                    Log.d(TAG, "onClick: failed");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: ");
        }
    }

}
