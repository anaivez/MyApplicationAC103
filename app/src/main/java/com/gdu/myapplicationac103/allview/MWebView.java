package com.gdu.myapplicationac103.allview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/4/20 14:11.
 */
public class MWebView extends WebView {


    public MWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public MWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @SuppressLint("JavascriptInterface")
    private void init() {
        //支持javascript
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        //// 设置可以支持缩放
        getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        //getSettings().setBuiltInZoomControls(true);
        ////扩大比例的缩放
        //getSettings().setUseWideViewPort(true);
        ////自适应屏幕
        //getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        setWebChromeClient(new WebChromeClient());
        //getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        loadUrl("file:///android_asset/sic.html");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
