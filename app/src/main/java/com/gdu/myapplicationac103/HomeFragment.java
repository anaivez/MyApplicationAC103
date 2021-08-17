package com.gdu.myapplicationac103;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:
 * Created by Czm on 2021/5/24 16:24.
 */
public class HomeFragment extends FC {

    RecyclerView rcvHome;

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        rcvHome = (RecyclerView) findViewById(R.id.rcv);
    }

    @Override
    protected void initWidget() {
        rcvHome.setAdapter(new FAdapter());
        rcvHome.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    class FAdapter extends RecyclerView.Adapter<FAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(getContext(), R.layout.item_img, null));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
