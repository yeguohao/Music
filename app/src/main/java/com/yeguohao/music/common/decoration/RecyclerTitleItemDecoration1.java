package com.yeguohao.music.common.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;

import com.yeguohao.music.common.TitleAndView;

public class RecyclerTitleItemDecoration1 extends RecyclerView.ItemDecoration {

    private TitleAndView titleAndView;
    private String fixedTitle;

    private int titleHeight;
    private float descent;
    private Paint paint = new Paint();

    private ArrayMap<Integer, String> arrayMap = new ArrayMap<>();
    private int fixedTop;
    private int fixedBottom;
    private int marginTop;

    public RecyclerTitleItemDecoration1() {
        paint.setTextSize(40);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        descent = fontMetrics.descent;
        titleHeight = (int) (fontMetrics.descent-fontMetrics.ascent);
        marginTop = titleHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        fixedTop = 0;
        fixedBottom = titleHeight;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            String title = arrayMap.get(position);
            if (title != null) {
                int top = child.getTop();
                int decorationTop = top - titleHeight;
                drawText(c,parent, decorationTop, title);

                int childDecorationTop = top - titleHeight;
                int childDecorationBottom = top;
                if (i == 1) {
                    if (childDecorationTop <= titleHeight) {
                        fixedTop = childDecorationTop - titleHeight;
                        fixedBottom = childDecorationTop;
                    } else if (childDecorationBottom >= titleHeight && childDecorationTop <= titleHeight) {
                        fixedTop = childDecorationTop - titleHeight;
                        fixedBottom = childDecorationTop;
                    }
                } else if (i == 0) {
                    fixedTitle = title;
                }
            } else if (i == 0) {
                fixedTitle = titleAndView.getTitle(position);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (fixedTitle != null) {
            drawText(c, parent, fixedTop, fixedTitle);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }
        int position = parent.getChildLayoutPosition(view);
        String title = titleAndView.getTitle(position);
        if (!arrayMap.containsValue(title)) {
            arrayMap.put(position, title);
            outRect.set(0, titleHeight, 0, 0);
        } else if (title.equals(arrayMap.get(position))){
            outRect.set(0, titleHeight, 0, 0);
        } else {
            outRect.set(0, marginTop, 0, 0);
        }
    }

    private void drawText(Canvas canvas, RecyclerView parent, int top, String title) {
        int height = top + titleHeight;
        paint.setTextAlign(Paint.Align.CENTER);
        RectF rectF = new RectF(0,top,parent.getRight(),height);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rectF, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(title, rectF.left + rectF.width() / 2, rectF.bottom - descent, paint);
    }

    private boolean isFullVisFirstItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();

        View child = recyclerView.getChildAt(position);
        return position == 0 && child.getTop() == 0;
    }
}