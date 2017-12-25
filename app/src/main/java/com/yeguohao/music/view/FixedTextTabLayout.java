package com.yeguohao.music.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeguohao.music.R;

public class FixedTextTabLayout extends ViewGroup {

    private static final String TAG = "FixedTextTabLayout";

    private SlideColorView underSlideView;
    private int childCount, childWidth;
    private float p;
    private ViewPager viewPager;
    private View selectedView;

    private int tabTextColor;
    private int tabSelectedTextColor;
    private int tabIndicatorColor;

    private Paint paint;

    public FixedTextTabLayout(Context context) {
        this(context, null);
    }

    public FixedTextTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedTextTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context, attrs, defStyleAttr);
    }

    private void init() {
        paint = new Paint();
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FixedTextTabLayout, defStyleAttr, 0);
        tabTextColor = ta.getColor(R.styleable.FixedTextTabLayout_tabTextColor, Color.WHITE);
        tabSelectedTextColor = ta.getColor(R.styleable.FixedTextTabLayout_tabSelectedTextColor, Color.RED);
        tabIndicatorColor = ta.getColor(R.styleable.FixedTextTabLayout_tabIndicatorColor, Color.RED);

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childCount = getChildCount();
        p = 100f / childCount;
        underSlideView = new SlideColorView(getContext(), tabIndicatorColor, 0);
        addView(underSlideView, new MarginLayoutParams(LayoutParams.MATCH_PARENT, 3));
        addListener();
        TextView textView = (TextView) getChildAt(0);
        paint.setTextSize(textView.getTextSize());

        setSelectedView(currentPagePosition);
        setLocation(currentPagePosition);
    }

    private void addListener() {
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == underSlideView) continue;

            final int j = i;
            child.setOnClickListener(v -> {
                if (viewPager != null && viewPager.getCurrentItem() != j) {
                    viewPager.setCurrentItem(j);
                }
            });
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = width / childCount;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == underSlideView) continue;

            measureChildWithMargins(child, widthMeasureSpec, width - childWidth, heightMeasureSpec, 0);
        }

        measureChildWithMargins(underSlideView, widthMeasureSpec, 0, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == underSlideView) continue;
            int left = i * childWidth;
            int right = left + childWidth;
            child.layout(left, t, right, b);
            if (i == 0) {
                setLocation(0);
            }
        }
        underSlideView.layout(l, b - 30, r, b - 27);
    }

    private void setSelectedView(int position) {
        View view = getChildAt(position);

        if (view != underSlideView && view != selectedView) {
            adjustSelectedViewStyle(view, false);
            adjustSelectedViewStyle(selectedView, true);
            this.selectedView = view;
        }
    }

    private void adjustSelectedViewStyle(View view, boolean naral) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            if (naral) {
                textView.setTextColor(tabTextColor);
            } else {
                textView.setTextColor(tabSelectedTextColor);
            }
        }
    }

    private void setLocation(int position) {
        TextView textView = (TextView) getChildAt(position);

        underSlideView.setTextLeft(textView.getLeft());
        underSlideView.setTextRight(textView.getRight());
        underSlideView.setShowWidth((int) paint.measureText(textView.getText().toString()));
    }

    private int currentPagePosition;

    public void setupWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float x = positionOffset / childCount;
//                underSlideView.setOffsetX(((position * p / 100f)) + x);
            }

            @Override
            public void onPageSelected(int position) {
                currentPagePosition = position;
                setLocation(position);
                setSelectedView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        currentPagePosition = ((Bundle) state).getInt("currentPagerPosition", 0);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt("currentPagerPosition", currentPagePosition);
        return bundle;
    }
}