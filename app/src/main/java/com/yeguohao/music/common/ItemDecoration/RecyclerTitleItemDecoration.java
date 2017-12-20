package com.yeguohao.music.common.ItemDecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class RecyclerTitleItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "RecyclerTitleItemDecora";

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.e(TAG, "view: " + view );
        Log.e(TAG, "parent: " + parent );
    }
}
