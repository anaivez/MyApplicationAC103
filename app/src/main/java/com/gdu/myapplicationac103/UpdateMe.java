package com.gdu.myapplicationac103;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Description:
 * Created by Czm on 2021/5/17 14:23.
 */
public class UpdateMe extends BC {

    private static final String TAG = "UpdateMe";

    EditText editText;

    public static void installApk(Activity activity, File apkfile) {
        if (!apkfile.exists()) {
            Log.d(TAG, "installApk: 不存在地址   " + apkfile.getPath());
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {
            //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(activity,
                    "com.vigorchip.treadmill.wr2",
                    apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }

        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_3_tea);
        editText = findViewById(R.id.et);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (File ff : getExternalMediaDirs()) {

            }
        }
    }

    public void onClick(View view) {
        new Task().execute();
    }

    class Task extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return "mnt/sdcard/Download";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            File file = new File(editText.getText().toString());
            installApk(UpdateMe.this, file);
        }
    }
}
