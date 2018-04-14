package com.yeguohao.music.common;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class Util {

    private static final String TAG = "Utils";

    public static void log(Object... msg) {
        for (Object o : msg) {
            String s = Objects.toString(o);
            Log.d(TAG, s);
        }
    }

    public static int screenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static void setDialogWindowAnimations(Dialog dialog, int styleId) {
        Window window = dialog.getWindow();
        assert window != null;

        WindowManager.LayoutParams params = window.getAttributes();
        params.windowAnimations = styleId;
        window.setAttributes(params);
    }

    public static int randomRange(int start, int end) {
        double n = (end - start + 1) * Math.random();
        return (int) Math.floor(start + n);
    }

}
