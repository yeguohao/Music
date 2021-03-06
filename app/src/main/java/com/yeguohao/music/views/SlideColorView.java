package com.yeguohao.music.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class SlideColorView extends View {

    private int color;
    private float offsetX;
    private int showWidth;
    private int width;
    private float preLeft = -1;
    private int jia;
    private Paint paint;

    private int textLeft;
    private int textRight;

    private String showTitle;

    public SlideColorView(Context context, int color, int offsetX) {
        super(context);
        this.color = color;
        this.offsetX = offsetX;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setColor(color);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = textLeft + (textRight - textLeft) / 2 - showWidth / 2;
        canvas.drawRect(left, 0, left + showWidth, getHeight(), paint);
    }

    public void setOffsetX(float offsetX) {
        if (this.offsetX != offsetX) {
            this.offsetX = offsetX;
            postInvalidate();
        }
    }

    public void setShowWidth(int showWidth) {
        this.showWidth = showWidth;
        postInvalidate();
    }

    public void setTextLeft(int textLeft) {
        this.textLeft = textLeft;
    }

    public void setTextRight(int textRight) {
        this.textRight = textRight;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
        this.showWidth = (int) paint.measureText(showTitle);
    }

}