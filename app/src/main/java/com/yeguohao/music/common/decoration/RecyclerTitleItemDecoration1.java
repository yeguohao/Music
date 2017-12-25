package com.yeguohao.music.common.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.common.TitleAndView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class RecyclerTitleItemDecoration1 extends RecyclerView.ItemDecoration {

    private static final String TAG = "RecyclerTitleItemDecora";

    private TitleAndView titleAndView;
    private String fixedTitle;
    private FrameLayout titleView;

    private Paint paint = new Paint();

    private ArrayMap<Integer, String> arrayMap = new ArrayMap<>();

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }

        int titleHeight = titleView.getMeasuredHeight();

        int fixedTop = 0;
        int fixedBottom = titleHeight;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            String title = arrayMap.get(position);
            if (title != null) {
                int top = child.getTop();
                int decorationTop = top - titleHeight;
                drawText(c, decorationTop, title);

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
            }
        }
        if (fixedTitle != null) {
            drawText(c, fixedTop, fixedTitle);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (titleAndView == null) {
            if (parent.getAdapter() instanceof TitleAndView) {
                titleAndView = (TitleAndView) parent.getAdapter();
            } else return;
        }

        if (titleView == null) {
            titleView = inflateTitleView(parent);
        }

        int position = parent.getChildLayoutPosition(view);
        String title = titleAndView.getTitle(position);
        if (!arrayMap.containsValue(title)) {
            arrayMap.put(position, title);
            outRect.set(0, titleView.getMeasuredHeight(), 0, 0);
        }
    }

    private void drawText(Canvas canvas, int top, String title) {
        Log.e(TAG, "drawText: " + top);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        TextView textView = (TextView) titleView.getChildAt(0);
        textView.setText(title);
        canvas.save();
        canvas.clipRect(0, top, titleView.getMeasuredWidth(), titleView.getMeasuredHeight());
        titleView.draw(canvas);
        canvas.restore();
    }

    private FrameLayout inflateTitleView(RecyclerView parent) {
        FrameLayout titleView = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.decoration_layout, parent, false);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(300, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(300, View.MeasureSpec.EXACTLY);

        ViewGroup.LayoutParams params = titleView.getLayoutParams();
        int childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, params.width);
        int childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, params.height);

        titleView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        Log.e(TAG, "inflateTitleView: " + titleView.getMeasuredHeight());
        return titleView;
    }

    private boolean isFullVisFirstItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();

        View child = recyclerView.getChildAt(position);
        return position == 0 && child.getTop() == 0;
    }
}