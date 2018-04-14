package com.yeguohao.music.common;

import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

public class XHAnimator {

    private ViewPropertyAnimator propertyAnimator;

    private View view;
    private float rotation = 359;
    private TimeInterpolator interpolator;
    private long duration = 16000;

    private XHAnimator(View view) {
        this.view = view;
        this.interpolator = new LinearInterpolator();
    }

    public static XHAnimator instance(View view) {
        return new XHAnimator(view);
    }

    public XHAnimator rotationBy(float value) {
        this.rotation = value;
        return this;
    }

    public XHAnimator setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public XHAnimator setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public void start() {
        if (propertyAnimator == null) {
            propertyAnimator = view
                    .animate()
                    .rotationBy(rotation)
                    .setInterpolator(interpolator)
                    .setDuration(duration).setStartDelay(0).withEndAction(() -> {
                        propertyAnimator = null;
                        start();
                    });
        }
        propertyAnimator.start();
    }

    public void stop() {
        if (propertyAnimator != null) {
            propertyAnimator.cancel();
            propertyAnimator = null;
        }
    }

}
