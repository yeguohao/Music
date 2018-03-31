package com.yeguohao.music.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.commit451.nativestackblur.NativeStackBlur;

public class BlurImageView extends AppCompatImageView {

    public BlurImageView(Context context) {
        super(context);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null) {
            return;
        }
        Bitmap process = NativeStackBlur.process(bm, 45);
        super.setImageBitmap(process);
    }

}
