package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/3/31 16:18.
 */
public class CardView extends View {

    Paint dPaint, cardPaint;
    float w, h;
    float opTopY , opBottomY , centen = (opBottomY - opTopY) / 2;
    float edTopY , edBottomY = h;
    float cardTopY , cardBottomY ;
    float downY = 0;

    boolean isF=true;

    public CardView(Context context) {
        super(context);
        init();
    }
    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dPaint = new Paint();
        dPaint.setColor(Color.GREEN);
        dPaint.setAntiAlias(true);
        dPaint.setDither(true);

        cardPaint = new Paint();
        cardPaint.setColor(Color.BLUE);
        cardPaint.setAntiAlias(true);
        cardPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        h = getHeight();
        w = getWidth();
        if (isF) {
             opTopY = (float) (h * 0.4) ;
             opBottomY = (float) (h*0.8 );
             centen =  (float) (h * 0.6);

             edTopY = (float) (h * 0.6);
             edBottomY = (float) (h);

             cardTopY = opTopY;
             cardBottomY =opBottomY;

             isF=false;
        }
        RectF rectF = new RectF(0f, 0f, w, (float) (h * 0.6));
        canvas.drawRoundRect(rectF, 15f, 15f, dPaint);


        Paint dd = new Paint();
        dd.setColor(Color.BLUE);
        canvas.drawText("dnmd",w/2,(float) (h * 0.6)-10,dd);

        RectF cardF = new RectF(0f, cardTopY, w, cardBottomY);
        canvas.drawRoundRect(cardF, 15f, 15f, cardPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = (downY - y)/10;
                moveY=0-moveY;
                cardTopY = cardTopY + moveY;
                cardBottomY = cardBottomY + moveY;
                if (cardBottomY >= h) {
                    cardBottomY = edBottomY;
                    cardTopY = edTopY;
                }
                if (cardTopY <= opTopY) {
                    cardBottomY = opBottomY;
                    cardTopY = opTopY;
                }
                Log.d("DCS", "onTouchEvent: TY:"+cardTopY+" BY:"+cardBottomY);
                break;
            case MotionEvent.ACTION_UP:
                if (centen>cardTopY) {
                    cardBottomY = opBottomY;
                    cardTopY = opTopY;
                }else{
                    cardBottomY = edBottomY;
                    cardTopY = edTopY;
                }
                break;
            default:
                break;
        }
        this.postInvalidate();
        return true;
    }
}
