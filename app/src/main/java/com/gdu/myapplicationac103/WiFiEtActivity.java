package com.gdu.myapplicationac103;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description:
 * Created by Czm on 2021/7/28 10:56.
 */
public class WiFiEtActivity extends AppCompatActivity {

    private static final String TAG = "WiFiEtActivity";
    TextView tvIP, tvC;
    EditText ed, etIP;
    private OutputStream outputStream = null;
    private Socket socket = null;
    private String ip;
    private String data;
    private boolean socketStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_sk);

        etIP = (EditText) findViewById(R.id.etIP);
        ed = (EditText) findViewById(R.id.et);

    }

    public void connect(View view) {

        ip = etIP.getText().toString();
        if (ip == null) {
            Toast.makeText(WiFiEtActivity.this, "please input Server IP", Toast.LENGTH_SHORT).show();
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                if (!socketStatus) {

                    try {
                        Log.d(TAG, "run: "+ip);
                        socket = new Socket(ip, YZ.PORT);
                        if (socket == null) {
                        } else {
                            socketStatus = true;
                        }
                        outputStream = socket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        thread.start();

    }

    public void send(View view) {

        data = ed.getText().toString();
        if (data == null) {
            Toast.makeText(WiFiEtActivity.this, "please input Sending Data", Toast.LENGTH_SHORT).show();
        } else {
            //在后面加上 '\0' ,是为了在服务端方便我们去解析；
            data = data + '\0';
        }
        Log.d(TAG, "send: " + data);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                if (socketStatus) {
                    try {
                        outputStream.write(data.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        thread.start();

    }

    /*当客户端界面返回时，关闭相应的socket资源*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*关闭相应的资源*/
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fin(View view){
        finish();
    }
}