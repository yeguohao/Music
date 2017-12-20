package com.yeguohao.music.common.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RankSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceSize;

    private Paint paint;

    public RankSpaceItemDecoration(int spaceSize, int spaceColor) {
        super();
        this.spaceSize = spaceSize;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setColor(spaceColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);


    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int parentRight = parent.getRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            int top = child.getTop();
            int bottom = child.getBottom();

            c.drawRect(0, bottom, parentRight, bottom + spaceSize, paint);
            c.drawRect(0, top, spaceSize, bottom, paint);
            c.drawRect(child.getRight(), top, parentRight, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, spaceSize);
    }
}
