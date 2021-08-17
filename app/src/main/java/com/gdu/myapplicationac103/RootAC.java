package com.gdu.myapplicationac103;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.DataOutputStream;

/**
 * Description:
 * Created by Czm on 2021/8/11 16:33.
 */
public class RootAC extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rootac);
    }

    public void onClick(View view){
        new Thread(() -> {
            DataOutputStream os = null;
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("su");
                os = new DataOutputStream(process.getOutputStream());
                os.writeBytes("mount -o remount /system" + "\n");
                os.flush();
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
