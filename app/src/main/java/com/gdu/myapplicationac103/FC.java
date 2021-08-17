package com.gdu.myapplicationac103;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Description:
 * Created by Czm on 2021/5/24 16:24.
 */
public abstract class FC extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layoutId(), container, false);
        return view;
    }

    protected abstract int layoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initWidget();
    }

    public View findViewById(int id) {
        return view.findViewById(id);
    }


    protected abstract void init();

    protected abstract void initWidget();
}
