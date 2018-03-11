package com.yeguohao.music.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yeguohao.music.R;

import butterknife.BindColor;

public class LoadingView extends LinearLayout {

    @BindColor(R.color.textColorLight)
    int textColor;

    private ImageView loading;
    private TextView loadingText;

    private ViewPropertyAnimator propertyAnimator;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChild();
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    private void initChild() {
        loading = new ImageView(getContext());
        loadingText = new TextView(getContext());

        loading.setImageResource(R.drawable.loading);
        loadingText.setText("正在载入中");
        loadingText.setTextColor(textColor);
        loadingText.setTextSize(getResources().getDimension(R.dimen.sp14));

        addView(loading, -2 ,-2);
        addView(loadingText, -2 ,-2);
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = loading.animate().rotationBy(359).setInterpolator(new LinearInterpolator())
                    .setDuration(3000).setStartDelay(0).withEndAction(() -> {
                        propertyAnimator = null;
                        startRotate();
                    });
        }
    }

    private void stopRotate() {
        if (propertyAnimator != null) {
            propertyAnimator.cancel();
            propertyAnimator = null;
        }
    }

    public void start() {
        startRotate();
    }

    public void cancel() {
        stopRotate();
        setVisibility(GONE);
    }
}
