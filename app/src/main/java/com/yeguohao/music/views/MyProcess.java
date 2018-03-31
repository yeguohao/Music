package com.yeguohao.music.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MyProcess extends View {

    private Paint paint;
    private long bufferedPosition;
    private float lineStep;
    private float circleStep;
    private long max = 100;
    private long progress;
    private boolean loading;
    private int radius = 15;
    private int lineSize = 6;
    private int height = 50;
    private int zq;

    private boolean fromUser;

    private GestureDetector gestureDetector;

    public MyProcess(Context context) {
        this(context, null);
    }

    public MyProcess(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProcess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);

        gestureDetector = new GestureDetector(context, listener);
    }

    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            fromUser = true;
            if (changeListener != null) {
                changeListener.onStartTrackingTouch(MyProcess.this);
            }
            float p = x / lineStep;
            setProgress((int) p);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int p = (int) (distanceX / lineStep);
            setProgress(getProgress() - p);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return super.onSingleTapUp(e);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            fromUser = false;
            if (changeListener != null) {
                changeListener.onStopTrackingTouch(this);
            }
            return true;
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void setBufferedPosition(long bufferedPosition) {
        this.bufferedPosition = bufferedPosition;
        postInvalidate();
    }

    public long getBufferedPosition() {
        return bufferedPosition;
    }

    public  void setProgress(long progress) {
        this.progress = progress;
        if (changeListener != null) {
            changeListener.onProgressChanged(this, progress, fromUser);
        }
        invalidate();
    }

    public long getProgress() {
        return progress;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
        getStep(max);
    }

    public void setLoading(boolean loading) {
//        this.loading = loading;
//        invalidate();
    }

    private void getStep(float max) {
        float width = getMeasuredWidth();
        float diameter = radius * 2;
        lineStep = width / max;
        circleStep = (width - diameter) / max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, height);

        getStep(getMax());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float startY = height / 2;

        paint.setStrokeWidth(lineSize);
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, startY, getWidth(), startY, paint);

        paint.setColor(Color.GRAY);
        canvas.drawLine(0, startY, bufferedPosition * lineStep, startY, paint);

        float progress = getProgress();
        paint.setColor(Color.RED);
        canvas.drawLine(0, startY, progress * lineStep, startY, paint);

        if (loading) {
            drawQ(canvas);
        } else {
            float centerX = progress * circleStep + radius;
            float centerY = height / 2;
            if (Float.isNaN(centerX) || Float.isInfinite(centerX)) {
                centerX = radius;
            }
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }

    // 画转圈
    private void drawQ(Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.save();
        zq++;
        if (zq == 72) {
            zq = 0;
        }
        int x = (int) (getProgress() * lineStep + 15);
        canvas.rotate(zq * 5, x, 15);

        canvas.save();
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(x, 7, x, 0, paint);
            canvas.rotate(36, x, 15);
        }
        canvas.restore();
        canvas.restore();

        postInvalidateDelayed(1000 / 60);
    }

    public void setChangeListener(OnChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    private OnChangeListener changeListener;

    public interface OnChangeListener {
        void onProgressChanged(MyProcess process, long progress, boolean fromUser);
        void onStartTrackingTouch(MyProcess process);
        void onStopTrackingTouch(MyProcess process);
    }

}
