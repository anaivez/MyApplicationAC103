package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Description:
 * Created by ZhDu on 2021/4/12 17:08.
 */
public class NumEditTextView extends AppCompatEditText {
    public NumEditTextView(Context context) {
        super(context);
        setTextW();
    }

    public NumEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextW();
    }

    private void setTextW() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    int num = Integer.parseInt(charSequence.toString());
                    if (num >= 255) {
                        setText("255");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    int num = Integer.parseInt(editable.toString());
                    if (num < 5) {
                        setText("5");
                    }
                }
            }
        });
    }
}
