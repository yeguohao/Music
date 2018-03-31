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

    private static final String TAG = "RecyclerTitleItemDecora";
    private TitleAndView titleAndView;
    private String fixedTitle;

    private int titleHeight;
    private float descent;
    private Paint paint = new Paint();

    private ArrayMap<Integer, String> arrayMap = new ArrayMap<>();
    private ArrayMap<Integer, String> fakeArrayMap = new ArrayMap<>();
    private int fixedTop;
    private int fixedBottom;
    private int marginTop;

    private int textColor;
    private int textBgColor;

    public RecyclerTitleItemDecoration1() {
        paint.setTextSize(40);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        descent = fontMetrics.descent;
        marginTop = (int) (fontMetrics.descent-fontMetrics.ascent);
        titleHeight = marginTop * 3;
        paint.setTextSize(30);

        textColor = Color.parseColor("#939393");
        textBgColor = Color.parseColor("#333333");
    }

    public int getMarginTop() {
        return marginTop;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        fixedTop = 0;
        fixedBottom = marginTop;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            String title = arrayMap.get(position);
            if (title != null) {
                int top = child.getTop();
                int d = marginTop * 2;
                int decorationTop = top - d;
                drawText(c,parent, decorationTop, title);

                int decorationBottom = top - (d - marginTop);
                if (i == 0 && top > d) {
                    if (decorationTop <= marginTop) {
                        fixedTop = decorationTop - marginTop;
                        fixedBottom = decorationTop;
                    } else if (decorationBottom >= marginTop && decorationTop <= marginTop) {
                        fixedTop = decorationTop - marginTop;
                        fixedBottom = decorationTop;
                    }
                    fixedTitle = fakeArrayMap.get(position - 1);
                    fixedTitle = fixedTitle == null ? arrayMap.get(position - 1) : fixedTitle;
                } else if (i == 0) {
                    fixedTop = 0;
                    fixedBottom = marginTop;
                    fixedTitle = title;
                    if (listener != null) {
                        listener.onChanged(fixedTitle);
                    }
                }
            } else if (i == 0) {
                fixedTitle = titleAndView.getTitle(position);
                if (listener != null) {
                    listener.onChanged(fixedTitle);
                }
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
        if (!arrayMap.containsValue(title) || title.equals(arrayMap.get(position))) {
            arrayMap.put(position, title);
            if (position == 0) outRect.set(0, marginTop * 2, 0, 0);
            else outRect.set(0, titleHeight, 0, 0);
        } else if (position == state.getItemCount() - 1) {
            outRect.set(0, marginTop, 0, marginTop);
        } else {
            fakeArrayMap.put(position, title);
            outRect.set(0, marginTop, 0, 0);
        }
    }

    private void drawText(Canvas canvas, RecyclerView parent, int top, String title) {
        int height = top + marginTop;
        paint.setTextAlign(Paint.Align.CENTER);
        RectF rectF = new RectF(0,top,parent.getRight(),height);
        paint.setColor(textBgColor);
        canvas.drawRect(rectF, paint);
        paint.setColor(textColor);
        int x = title.equals("热门") ? 50 : 30;
        canvas.drawText(title, x, rectF.bottom - descent, paint);
    }


    private OnFixedTextChangedListener listener;

    public void setListener(OnFixedTextChangedListener listener) {
        this.listener = listener;
    }

    public interface OnFixedTextChangedListener {
        void onChanged(String text);
    }

    private boolean isFullVisFirstItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();

        View child = recyclerView.getChildAt(position);
        return position == 0 && child.getTop() == 0;
    }

}