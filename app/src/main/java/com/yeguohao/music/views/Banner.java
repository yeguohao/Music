package com.yeguohao.music.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.yeguohao.music.R;


public class Banner extends FrameLayout {

    private ViewPager viewPager;
    private Dots dots;
    private PagerAdapter pagerAdapter;
    private InnerPageAdapter adapter;

    private boolean autoPlay;
    private int interval;

    private Handler handler = new Handler();

    private Runnable play = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            Banner.this.run();
        }
    };

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        initView();
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Banner, defStyleAttr, 0);
        autoPlay = ta.getBoolean(R.styleable.Banner_autoPlay, true);
        interval = ta.getInt(R.styleable.Banner_interval, 3000);
        ta.recycle();
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    private void initView() {
        Activity activity = (Activity) getContext();

        viewPager = new InnerViewPager(activity);
        adapter = new InnerPageAdapter();
        viewPager.setAdapter(adapter);
        addView(viewPager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dots = new Dots(activity);
        LayoutParams params = new LayoutParams(-2, -2, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        params.bottomMargin = 12;
        addView(dots, params);

        RxViewPager.pageSelections(viewPager).map(adapter::getRealPosition).subscribe(dots::setSelectedDotIndex);
    }

    private void stop() {
        handler.removeCallbacks(play);
    }

    private void run() {
        handler.postDelayed(play, interval);
    }

    public void pause() {
        stop();
    }

    public void resume() {
        if (autoPlay) {
            run();
        }
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        dots.setDotCount(pagerAdapter.getCount());
    }

    private class InnerViewPager extends ViewPager {

        public InnerViewPager(@NonNull Context context) {
            super(context);
        }

        public InnerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (autoPlay && ev.getAction() == MotionEvent.ACTION_DOWN) {
                stop();
            } else if (autoPlay && ev.getAction() == MotionEvent.ACTION_UP) {
                run();
            }
            return super.dispatchTouchEvent(ev);
        }
    }

    private class InnerPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return (pagerAdapter == null || pagerAdapter.getCount() == 0) ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return pagerAdapter.isViewFromObject(view, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return pagerAdapter.instantiateItem(container, getRealPosition(position + Integer.MAX_VALUE / 2 - 3));
        }

        int getRealPosition(int position) {
            return pagerAdapter != null ? position % pagerAdapter.getCount() : 0;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            pagerAdapter.destroyItem(container, position, object);
        }

    }
}
