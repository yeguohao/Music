package com.yeguohao.music.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yeguohao.music.R;

public class OverHeightRecyclerView extends RecyclerView {

    public OverHeightRecyclerView(Context context) {
        super(context);
    }

    public OverHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int size = (int) getContext().getResources().getDimension(R.dimen.dp190);
        int height = MeasureSpec.getSize(heightSpec);
//        heightSpec = MeasureSpec.makeMeasureSpec(size + height, MeasureSpec.getMode(heightSpec));
        super.onMeasure(widthSpec, heightSpec);
    }
}
