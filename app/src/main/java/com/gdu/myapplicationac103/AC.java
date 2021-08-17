package com.gdu.myapplicationac103;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.gdu.myapplicationac103.allview.MWebView;
import com.google.gson.Gson;

import java.lang.reflect.Method;

/**
 * Description:
 * Created by ZhDu on 2021/3/31 14:32.
 */
public class AC extends BC {

    MWebView webView;
    @SuppressLint("HandlerLeak")
    Handler backHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final KeyEvent evDown = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 4, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);

            final KeyEvent evUp = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 4, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);
            Object object = new Object();
            Method getInstance;
            Method injectInputEvent;
            try {
                getInstance = Class.forName("android.hardware.input.InputManager").getMethod("getInstance");
                InputManager mInputManager = (InputManager) getInstance.invoke(object);
                injectInputEvent = Class.forName("android.hardware.input.InputManager").getMethod("injectInputEvent", InputEvent.class, int.class);
                injectInputEvent.invoke(mInputManager, evDown, 0);
                injectInputEvent.invoke(mInputManager, evUp, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void create() {
        Uri uri = Uri.parse("https://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //不允许重复创建
        intent.putExtra("duplicate", false);
        //需要现实的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "YOUTUBE");
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        sendBroadcast(intent);
    }

    //@Override
    //public boolean onKeyDown(int keyCode, KeyEvent event) {
    //    if (event.getAction()==KeyEvent.KEYCODE_BACK) {
    //        Intent intent = new Intent();
    //        intent.setAction("android.intent.action.VIEW");
    //        Uri content_url = Uri.parse("https://www.bilibili.com");
    //        intent.setData(content_url);
    //        startActivity(intent);
    //    }
    //    return super.onKeyDown(keyCode, event);
    //}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = webView.getSettings();

        //create();
    }

    public void onClick(View view) {
        //Intent intent = new Intent();
        //intent.setAction("android.intent.action.VIEW");
        //Uri content_url = Uri.parse("https://www.bilibili.com");
        //intent.setData(content_url);
        //startActivity(intent);
        Log.d(TAG, "onCreate: " + webView.getTitle());
        Log.d(TAG, "onCreate: " + webView.getOriginalUrl());
        Log.d(TAG, "onCreate: " + webView.getUrl());
        backHandler.sendEmptyMessageDelayed(0, 1);

        String json = "{\"viewName\":\"WebView\"}";
        Dex2x dex2x = new Gson().fromJson(json, Dex2x.class);
        Log.d(TAG, "onClick: " + dex2x.viewName);
    }

    class Dex2x {
        private String viewName;
    }
}
