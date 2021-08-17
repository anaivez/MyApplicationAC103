package com.gdu.myapplicationac103;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Czm on 2021/5/24 16:01.
 */
public class WatchActivityV1 extends BC {


    List<Fragment> fragments = new ArrayList<>();
    int currentIndex = 0;
    Fragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wwwwwwwa);
        fragmentManager = getSupportFragmentManager();
        initFragment();
        showFragment();
        CheckVersion.getInstance().checkVersion(this, new CheckVersion.onCheckVersion() {
            @Override
            public void checkVersion(boolean isNeglect, boolean isNewVersion,boolean is, String src) {
            }
        });
    }

    private void initFragment() {
        currentFragment = new HomeFragment();
        fragments.clear();
        fragments.add(currentFragment);
    }

    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if (!fragments.get(currentIndex).isAdded()) {
            transaction.detach(currentFragment).add(R.id.content_fragment, fragments.get(currentIndex), "" + currentIndex);  //第三个参数为添加当前的frgment时绑定一个tag
        } else {
            transaction.detach(currentFragment);
        }
        currentFragment = fragments.get(currentIndex);
        transaction.attach(currentFragment);
        transaction.commitAllowingStateLoss();
    }
}
