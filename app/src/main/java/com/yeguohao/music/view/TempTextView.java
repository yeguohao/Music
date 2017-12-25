package com.yeguohao.music.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class TempTextView extends TextView {

    private static final String TAG = "TempTextView";

    public TempTextView(Context context) {
        super(context);
    }

    public TempTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TempTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "getWidth: " + canvas.getWidth() );
        Log.e(TAG, "getHeight: " + canvas.getHeight() );
    }
}
