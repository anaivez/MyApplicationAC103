package com.gdu.myapplicationac103;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.gdu.myapplicationac103.allview.MyApplication;

/**
 * Description:
 * Created by ZhDu on 2021/4/15 11:57.
 */
public class AIDLServer extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBin();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    class MyBin extends IMyAidlInterface.Stub {

        @Override
        public String getIDS() throws RemoteException {
            MainActivity mainActivity = null;
            for (Activity ac : MyApplication.getActivities()) {
                if (ac.getClass().getSimpleName().equals("MainActivity")) {
                    mainActivity = ((MainActivity) ac);
                }
            }
            return mainActivity.getTime();
        }

        @Override
        public String getIDName(String name) throws RemoteException {
            return name.replace(" ", "");
        }

        @Override
        public void startActivity() throws RemoteException {
        }

        @Override
        public DUser getUser() throws RemoteException {
            return new DUser(3, "Faker");
        }
    }
}
