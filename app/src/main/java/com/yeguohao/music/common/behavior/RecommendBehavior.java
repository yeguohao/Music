package com.yeguohao.music.common.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.view.Banner;

public class RecommendBehavior extends CoordinatorLayout.Behavior {


    private static final String TAG = "RecommendBehavior";
    private int limit = -1;

    public RecommendBehavior() {
    }

    public RecommendBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Banner banner = (Banner) coordinatorLayout.getChildAt(0);
        TextView textView = (TextView) coordinatorLayout.getChildAt(1);
        RecyclerView recyclerView = (RecyclerView) child;

        int top = textView.getTop();
        if (limit == -1) limit = top;

        if ((dy > 0 && top > 0) || (dy < 0 && isFirstItem(recyclerView) && top < limit)) {
            textView.offsetTopAndBottom(-dy);
            banner.offsetTopAndBottom(-dy);
            child.offsetTopAndBottom(-dy);

            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    private boolean isFirstItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();

        View child = recyclerView.getChildAt(position);
        return position == 0 && child.getTop() == 0;
    }
}
