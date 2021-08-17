package com.gdu.myapplicationac103;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.core.view.accessibility.AccessibilityViewCommand;

/**
 * Description:
 * Created by Czm on 2021/8/10 10:58.
 */
public class RTextView extends TextView {


    String reText = "";
    private BufferType mBufferType = BufferType.NORMAL;

    public RTextView(Context context) {
        super(context);
    }

    public RTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text, int resId) {
        reText = getResources().getString(resId);
        setText(getString(text), mBufferType);
    }

    public void setText(String text, String str) {
        reText = str;
        setText(getString(text), mBufferType);
    }

    private String getString(String text) {
        String str = text;
        if (TextUtils.isEmpty(str)) {
            str = reText;
        }
        if (str.toLowerCase().trim().equals("null")) {
            str = reText;
        }
        return str;
    }

    public void setText(String text) {
        setText(getString(text), mBufferType);
    }


}
