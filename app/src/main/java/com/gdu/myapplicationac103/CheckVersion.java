package com.gdu.myapplicationac103;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Description:
 * Created by Czm on 2021/5/27 10:25.
 */
public class CheckVersion {

    private static final String fileName = "config";
    static CheckVersion checkVersion;
    public onCheckVersion onCheckVersion;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public CheckVersion() {

    }

    public static CheckVersion getInstance() {
        if (checkVersion == null) {
            checkVersion = new CheckVersion();
        }
        return checkVersion;
    }

    public void checkVersion(Context context, onCheckVersion onCheckVersion) {
        if (onCheckVersion != null) {
            onCheckVersion.checkVersion(getSp(context), false,false, "");
        }
    }

    public void installApk(Activity activity, File apkfile) {
        if (!apkfile.exists()) {
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

    public void ignoreVersion(Context context, boolean b) {
        initSP(context);
        editor.putBoolean("ignoreVersion", b);
    }

    private boolean getSp(Context context) {
        initSP(context);
        return sp.getBoolean("ignoreVersion", true);
    }

    private void initSP(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        editor = sp.edit();
    }

    public interface onCheckVersion {
        /**
         * @param isNeglect    该版本是否被忽略
         * @param isNewVersion 是否有新版本下载
         * @param src          下载地址
         * @param isConstraint 是否强制更新
         */
        void checkVersion(boolean isNeglect, boolean isNewVersion,boolean isConstraint, String src);
    }


}
