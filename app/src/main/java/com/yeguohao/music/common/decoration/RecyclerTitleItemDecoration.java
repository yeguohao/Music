package com.yeguohao.music.common.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.common.TitleAndView;

public class RecyclerTitleItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "RecyclerTitleItemDecora";

    private final int titleHeight;

    private TitleAndView titleAndView;

    private Paint paint;
    private Paint textPaint;

    private int top;
    private String title;

    private int lastBottom;

    public RecyclerTitleItemDecoration(Context context) {
        Resources resources = context.getResources();
        int textSize = resources.getDimensionPixelSize(R.dimen.sp12);
        this.titleHeight = resources.getDimensionPixelOffset(R.dimen.dp20);

        this.paint = new Paint();
        this.textPaint = new Paint();

        paint.setColor(Color.WHITE);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(textSize);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            int top = child.getTop();

            int decorationTop = top - titleHeight;

            c.drawRect(0, decorationTop, parent.getRight(), top, paint);
            c.drawText(position + "", 0, decorationTop + titleHeight / 2, textPaint);

            if (i == 0) {
                title = titleAndView.getTitle(position);

                int bottom = child.getBottom();
                if (bottom < titleHeight && bottom > 0) {
                    int db = lastBottom - bottom;

                    if (db < 0 && this.top < -titleHeight) this.top = -titleHeight;
                    this.top -= db;
                } else if (bottom >= titleHeight) {
                    this.top = 0;
                }
                lastBottom = bottom;
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }
        c.drawRect(0, top, parent.getRight(), top + titleHeight, paint);
        c.drawText(title, 0, top + (top + titleHeight) / 2, textPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }
        outRect.set(0, titleHeight, 0, 0);
    }

    private boolean isFullVisFirstItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();

        View child = recyclerView.getChildAt(position);
        return position == 0 && child.getTop() == 0;
    }
}