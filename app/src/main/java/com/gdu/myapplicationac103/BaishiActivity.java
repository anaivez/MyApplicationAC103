package com.gdu.myapplicationac103;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Description:
 * Created by Czm on 2021/8/20 14:10.
 */
public class BaishiActivity extends BC implements IWantTrue {


    TextView tvAdd, tvNum, tvSub;
    IWantFree iWantFree;

    private FragmentManager fragmentManager1, fragmentManager2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baishi);
        iWantFree = new IWantFreeImpl();
        Intent intent = new Intent(getContext(), DCSService.class);
        Intent intent2x = new Intent(getContext(), AIDLServer.class);
        startService(intent2x);
        //startService(intent);
        fragmentManager1 = getSupportFragmentManager();
        fragmentManager2 = getSupportFragmentManager();
        showFragment();
        showBFragment();
        tvAdd = findViewById(R.id.tvAdd);
        tvNum = findViewById(R.id.tvNum);
        tvSub = findViewById(R.id.tvSub);
        tvAdd.setOnClickListener(new AD());
        tvSub.setOnClickListener(new AD());
        DCSService.setiWantTrue(this);


    }



    private void showFragment() {
        FragmentTransaction transaction = fragmentManager1.beginTransaction();
        transaction.add(R.id.fLeft, new AFragment());
        transaction.commitAllowingStateLoss();

        for (int i = 0; i < 11; ) {
            continue;
        }
    }

    private void showBFragment() {
        FragmentTransaction transaction = fragmentManager2.beginTransaction();
        transaction.add(R.id.fRight, new BFragment());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void numChange(int type, int num) {
        if (tvNum != null) {
            tvNum.setText("" + num);
        }
    }

    class AD implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvAdd) {
                iWantFree.add();
            } else {
                iWantFree.sub();
            }
        }
    }
}
