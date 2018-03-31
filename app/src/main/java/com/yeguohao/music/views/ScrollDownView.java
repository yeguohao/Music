package com.yeguohao.music.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static com.yeguohao.music.common.Util.setDialogWindowAnimations;

public class ScrollDownView extends FrameLayout {

    private LinearLayoutManager manager;
    private Scroller scroller;
    private float lastY;

    private Dialog dialog;

    public ScrollDownView(@NonNull Context context) {
        this(context, null);
    }

    public ScrollDownView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDownView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RecyclerView recycler = (RecyclerView)((ViewGroup) getChildAt(0)).getChildAt(1);
        manager = (LinearLayoutManager) recycler.getLayoutManager();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == ACTION_DOWN) {
            lastY = ev.getY();
        } else if (action == ACTION_MOVE) {
            float dy = ev.getY() - lastY;
            boolean first = manager.findFirstCompletelyVisibleItemPosition() == 0;
            boolean down = dy > 0;
            boolean up = dy < 0;

            lastY = ev.getY();
            if (down && first) {
                scrollBy(0, (int) -dy);
                return true;
            } else if (up && getScrollY() < 0) {
                scrollBy(0, (int) -dy);
                return true;
            }
        } else if (action == ACTION_UP) {
            int w = getHeight() / 2;

            int absScrollY = Math.abs(getScrollY());
            if (absScrollY != 0 && absScrollY < w) {
                scroller.startScroll(getScrollX(), absScrollY, 0, -absScrollY, 300);
                invalidate();
                return true;
            } else if (absScrollY > w) {
                scroller.startScroll(getScrollX(), absScrollY, 0, getHeight() - absScrollY, 300);
                invalidate();
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, -scroller.getCurrY());
            invalidate();

            if (scroller.getFinalY() != 0 && scroller.getFinalY() == scroller.getCurrY()) {
                dismiss();
            }
        }
    }

    private void dismiss() {
        setDialogWindowAnimations(dialog, -1);
        dialog.dismiss();
    }

}
