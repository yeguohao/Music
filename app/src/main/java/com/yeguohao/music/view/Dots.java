package com.yeguohao.music.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Dots extends View {

    private Paint unselectedPaint;
    private Paint selectedPaint;
    private int dotCount;
    private int dotRadius = 7;
    private int dotSpace = 12;
    private int selectedDotIndex = -1;

    public Dots(Context context) {
        this(context, null);
    }

    public Dots(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Dots(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
        requestLayout();
    }

    public void setSelectedDotIndex(int selectedDotIndex) {
        this.selectedDotIndex = selectedDotIndex;
        invalidate();
    }

    private void init() {
        unselectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unselectedPaint.setColor(Color.parseColor("#858483"));

        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(Color.parseColor("#d2cdcd"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (dotCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int width = dotCount * dotRadius * 2 + (dotCount - 1) * dotSpace;
        int height = dotRadius * 2;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < dotCount; i++) {
            int cx = i * (dotSpace + dotRadius * 2) + dotRadius;
            if (i == selectedDotIndex) drawSelectedDot(canvas, cx);
            else drawUnSelectedDot(canvas, cx);
        }
    }

    private void drawSelectedDot(Canvas canvas, int cx) {
        canvas.drawCircle(cx, dotRadius, dotRadius, selectedPaint);
    }

    private void drawUnSelectedDot(Canvas canvas, int cx) {
        canvas.drawCircle(cx, dotRadius, dotRadius, unselectedPaint);
    }
}
