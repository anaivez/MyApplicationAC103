package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

public class TimeCount extends AppCompatTextView {

    CountDownTimer countDownTimer;
    boolean isRuning = false;
    OnTimeCountListener onTimeCountListener;
    String reString = "";

    public TimeCount(Context context) {
        super(context);
    }

    public TimeCount(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (reString.isEmpty()) {
                reString = getText().toString();
            }
            if (!isRuning) {
                countDownTimer = new CountDownTimer(1000 * 5, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        isRuning = true;
                        setText("" + millisUntilFinished / 1000 + "S后可以继续");
                        if (onTimeCountListener != null) {
                            onTimeCountListener.onTick(millisUntilFinished);
                        }
                    }

                    @Override
                    public void onFinish() {
                        isRuning = false;
                        setClickable(true);
                        setText(reString);
                        if (onTimeCountListener != null) {
                            onTimeCountListener.onFinish();
                        }
                    }
                };
                countDownTimer.start();
            }
        }
        return true;
    }

    public void setReString(String reString) {
        this.reString = reString;
    }

    public void setOnTimeCountListener(OnTimeCountListener onTimeCountListener) {
        this.onTimeCountListener = onTimeCountListener;
    }

    public interface OnTimeCountListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }

}
