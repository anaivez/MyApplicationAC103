package com.gdu.myapplicationac103;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * Description:
 * Created by Czm on 2021/8/20 14:17.
 */
public class BFragment extends Fragment implements IWantTrue {

    View view;
    TextView tvAdd, tvNum, tvSub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_te, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvAdd = view.findViewById(R.id.tvAdd);
        tvNum = view.findViewById(R.id.tvNum);
        tvSub = view.findViewById(R.id.tvSub);
        tvAdd.setOnClickListener(new AD());
        tvSub.setOnClickListener(new AD());
        DCSService.setiWantTrue(this);
        iWantFree=new IWantFreeImpl();
    }

    @Override
    public void numChange(int type, int num) {
        if (tvNum != null) {
            tvNum.setText("" + num);
        }
    }
    IWantFree iWantFree;

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
