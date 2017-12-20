package com.yeguohao.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class PlayerProgress extends RelativeLayout {

    public PlayerProgress(Context context) {
        this(context, null);
    }

    public PlayerProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
