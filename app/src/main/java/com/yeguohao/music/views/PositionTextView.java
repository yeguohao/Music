package com.yeguohao.music.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.yeguohao.music.R;
import com.yeguohao.music.common.TimeFormat;

public class PositionTextView extends AppCompatTextView {

    private TimeFormat timeFormat = new TimeFormat();

    private long position;

    public PositionTextView(Context context) {
        super(context);
    }

    public PositionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PositionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPosition(long position) {
        this.position = position;
        if (position == 0) {
            setText(R.string.initial_time);
        } else {
            setText(timeFormat.timeStamp2Date(position));
        }
    }

    public long getPosition() {
        return position;
    }

}
