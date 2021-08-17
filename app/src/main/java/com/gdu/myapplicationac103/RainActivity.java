package com.gdu.myapplicationac103;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/4/23 9:39.
 */
public class RainActivity extends BC {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigationBarStatusBarTranslucent(this);
        setContentView(R.layout.activity_rain);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
