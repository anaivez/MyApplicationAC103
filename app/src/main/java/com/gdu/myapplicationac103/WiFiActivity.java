package com.gdu.myapplicationac103;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 * Created by Czm on 2021/7/28 10:28.
 */
public class WiFiActivity extends AppCompatActivity {
    private static final String TAG = "WiFiActivity";
    TextView tvIP, tvC;
    EditText ed;
    StringBuffer stringBuffer = new StringBuffer();
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String obj = msg.obj.toString();
            Log.d(TAG, "handleMessage: " + obj);
            switch (msg.what) {
                case 1:
                    tvIP.setText(obj == null ? "" : obj);
                    break;
                case 2:
                    ed.setText(obj == null ? "" : obj);
                    stringBuffer.setLength(0);
                    break;

            }

        }
    };
    private ServerSocket serverSocket = null;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_sk);
        tvIP = findViewById(R.id.tvIP);
        tvC = findViewById(R.id.tvContent);
        ed = findViewById(R.id.et);
        findViewById(R.id.llC).setVisibility(View.GONE);
        receiveData();
    }

    /*
    服务器端接收数据
    需要注意以下一点：
    服务器端应该是多线程的，因为一个服务器可能会有多个客户端连接在服务器上；
    */
    public void receiveData() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                /*指明服务器端的端口号*/
                try {
                    serverSocket = new ServerSocket(YZ.PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                GetIpAddress.getLocalIpAddress(serverSocket);

                Message message_1 = handler.obtainMessage();
                message_1.what = 1;
                message_1.obj = "IP:" + GetIpAddress.getIP() + " PORT: " + GetIpAddress.getPort();
                handler.sendMessage(message_1);

                while (true) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                        inputStream = socket.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    new ServerThread(socket, inputStream).start();

                }
            }
        };
        thread.start();

    }

    /*当按返回键时，关闭相应的socket资源*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fin(View view) {
        finish();
    }

    class ServerThread extends Thread {

        private Socket socket;
        private InputStream inputStream;
        private StringBuffer stringBuffer = WiFiActivity.this.stringBuffer;


        public ServerThread(Socket socket, InputStream inputStream) {
            this.socket = socket;
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            int len;
            byte[] bytes = new byte[20];
            boolean isString = false;

            try {
                //在这里需要明白一下什么时候其会等于 -1，其在输入流关闭时才会等于 -1，
                //并不是数据读完了，再去读才会等于-1，数据读完了，最结果也就是读不到数据为0而已；
                while ((len = inputStream.read(bytes)) != -1) {
                    for (int i = 0; i < len; i++) {
                        if (bytes[i] != '\0') {
                            stringBuffer.append((char) bytes[i]);
                        } else {
                            isString = true;
                            break;
                        }
                    }
                    if (isString) {
                        Message message_2 = handler.obtainMessage();
                        message_2.what = 2;
                        message_2.obj = stringBuffer;
                        handler.sendMessage(message_2);
                        isString = false;
                    }

                }
                //当这个异常发生时，说明客户端那边的连接已经断开
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    inputStream.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }
    }
}