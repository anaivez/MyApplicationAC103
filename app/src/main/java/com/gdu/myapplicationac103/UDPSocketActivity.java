package com.gdu.myapplicationac103;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:
 * Created by Czm on 2021/7/29 15:20.
 */
public class UDPSocketActivity  extends AppCompatActivity {

    private UDPSocket socket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        final EditText etMessage = (EditText) findViewById(R.id.et_message);
        Button btSend = (Button) findViewById(R.id.bt_send);


        socket = new UDPSocket(this);
        socket.startUDPSocket();


        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.sendMessage(etMessage.getText().toString());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.stopUDPSocket();
    }
}