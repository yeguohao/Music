package com.yeguohao.music.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yeguohao.music.R;

public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";
    private LeftTop[] viewLines;
    private int layoutChildCount;

    private int maxLine;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
        maxLine = ta.getInteger(R.styleable.FlowLayout_maxLine, 3);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initLeftTopArray();
    }

    private void initLeftTopArray() {
        viewLines = new LeftTop[getChildCount()];
        for (int i = 0; i < viewLines.length; i++) {
            viewLines[i] = new LeftTop(0, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        layoutChildCount = 0;
        int w = MeasureSpec.getSize(widthMeasureSpec) - getPaddingEnd();
        int h = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom();

        int parentPaddingStart = getPaddingStart();
        int width = parentPaddingStart;
        int height = getPaddingTop();
        int childCount = getChildCount();
        int lineHeight = height;
        int line = 1;

        if (viewLines.length < childCount) initLeftTopArray();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int startMargin = params.getMarginStart();
            int endMargin = params.getMarginEnd();
            int childW = child.getMeasuredWidth() + startMargin + endMargin;
            int childH = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            if (width + childW < w) {
                viewLines[i].left = width + startMargin;
                viewLines[i].top = height + params.topMargin;
                width += childW;
                lineHeight = Math.max(lineHeight, childH + height);
                layoutChildCount++;
            } else {
                if (line >= maxLine) break;
                layoutChildCount++;
                line++;
                width = parentPaddingStart + childW;
                height = lineHeight;
                viewLines[i].left = parentPaddingStart + startMargin;
                viewLines[i].top = height + params.topMargin;
                lineHeight = Math.max(lineHeight, childH + height);
            }
        }
        height = lineHeight;
        setMeasuredDimension(w, Math.min(h, height));
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        for (int i = 0; i < layoutChildCount; i++) {
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

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-2, -2);
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
