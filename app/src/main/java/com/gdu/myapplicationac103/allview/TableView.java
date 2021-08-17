package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by ZhDu on 2021/4/23 10:18.
 */
public class TableView extends View {
    Paint tablePaint;
    Paint textPaint;
    List<TableLine> tableLines = new ArrayList<>();
    List<TextTs> textTs = new ArrayList<>();

    public TableView(Context context) {
        super(context);
        init();
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        tablePaint = new Paint();
        tablePaint.setAntiAlias(true);
        tablePaint.setColor(Color.GRAY);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float height = 30f;
        float weight = 65f;
        float maxHeight=0;
        float maxWeight=0;
        //draw X
        for (int i = 1; i <= 10; i++) {
            tableLines.add(
                    new TableLine(
                            0,
                            height * i,
                            weight * 6,
                            height * i));
            maxWeight=maxWeight+weight;
        }
        //draw y
        for (int i = 0; i <= 6; i++) {
            tableLines.add(
                    new TableLine(
                            weight * i,
                            0,
                            weight * i,
                            height * 10));
            maxHeight=maxHeight+height;
        }
        for (int i = 0; i < 10; i++) {
            for (int u = 0; u < 6; u++) {
                textTs.add(
                        new TextTs(
                                (weight / 2) + (weight * (u )),
                                getTextHeight(textPaint.getFontMetrics()) / 2 + (height / 2) + (height * (i)),
                                "Fa"));
            }
        }
        ViewGroup.LayoutParams lp=getLayoutParams();
        lp.width=(int)maxWeight;
        lp.height=(int)maxHeight;
        setLayoutParams(lp);
    }


    //获取text的高度值
    private float getTextHeight(Paint.FontMetrics fms) {
        float t = Math.abs(fms.top), b = Math.abs(fms.bottom);
        return Math.abs(t - b);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (TableLine tableLine : tableLines) {
            canvas.drawLine(tableLine.startXforX, tableLine.startYforX, tableLine.stopXforX, tableLine.stopYforX, tablePaint);
        }
        for (TextTs ts : textTs) {
            canvas.drawText(ts.text, ts.startXforX, ts.startYforX, textPaint);
        }
    }




    class TableLine {

        float startXforX;
        float startYforX;
        float stopXforX;
        float stopYforX;

        public TableLine(float startXforX, float startYforX, float stopXforX, float stopYforX) {
            this.startXforX = startXforX;
            this.startYforX = startYforX;
            this.stopXforX = stopXforX;
            this.stopYforX = stopYforX;
        }
    }

    class TextTs {
        float startXforX;
        float startYforX;
        String text;

        public TextTs(float startXforX, float startYforX, String text) {
            this.startXforX = startXforX;
            this.startYforX = startYforX;
            this.text = text;
        }
    }
}
