package com.gdu.myapplicationac103;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Description:
 * Created by Czm on 2021/7/28 10:30.
 */
public class GetIpAddress {


    public static String IP;
    public static int PORT;

    public static String getIP(){
        return IP;
    }
    public static int getPort(){
        return PORT;
    }
    public static void getLocalIpAddress(ServerSocket serverSocket){

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    String mIP = inetAddress.getHostAddress().substring(0, 3);
                    if(mIP.equals("192")){
                        IP = inetAddress.getHostAddress();    //获取本地IP
                        PORT = serverSocket.getLocalPort();    //获取本地的PORT
                        Log.e("IP",""+IP);
                        Log.e("PORT",""+PORT);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}
