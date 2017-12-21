package com.yeguohao.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

    private LeftTop[] viewLines;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewLines = new LeftTop[getChildCount()];
        for (int i = 0; i < viewLines.length; i++) {
            viewLines[i] = new LeftTop(0, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec) - getPaddingEnd();
        int h = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom();

        int parentPaddingStart = getPaddingStart();
        int width = parentPaddingStart;
        int height = getPaddingTop();
        int childCount = getChildCount();
        int lineHeight = height;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childW = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childH = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            if (width + childW < w) {
                viewLines[i].left = width + params.leftMargin;
                viewLines[i].top = height + params.topMargin;
                width += childW;
                lineHeight = Math.max(lineHeight, childH + height);
            } else {
                width = parentPaddingStart + childW;
                height = lineHeight;
                viewLines[i].left = parentPaddingStart + params.leftMargin;
                viewLines[i].top = height + params.topMargin;
                lineHeight = Math.max(lineHeight, childH + height);
            }
        }
        height = lineHeight;
        setMeasuredDimension(w, Math.max(h, height));
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LeftTop lt = viewLines[i];
            child.layout(lt.left, lt.top, lt.left + child.getMeasuredWidth(), lt.top + child.getMeasuredHeight());
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    private class LeftTop {
        private int left;
        private int top;

        LeftTop(int left, int top) {
            this.left = left;
            this.top = top;
        }
    }
}
