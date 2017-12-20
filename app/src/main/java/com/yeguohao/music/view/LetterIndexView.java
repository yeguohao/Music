package com.yeguohao.music.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yeguohao.music.R;

public class LetterIndexView extends LinearLayout {

    private static final String TAG = "LetterIndexView";
    private String[] letters = {"热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int textSize;
    private int unselectedColor;
    private int selectedColor;

    private TextView selectedTextView;

    private LetterSelectedListener letterSelectedListener;
    private OnClickListener listener = this::selected;

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        Resources resources = getResources();
        textSize = resources.getDimensionPixelSize(R.dimen.sp6);
        unselectedColor = resources.getColor(R.color.textColorDark);
        selectedColor = resources.getColor(R.color.textColorLight);
        initChild();
    }

    public void setLetterSelectedListener(LetterSelectedListener letterSelectedListener) {
        this.letterSelectedListener = letterSelectedListener;
    }

    private void initChild() {
        for (String letter : letters) {
            initChild(letter);
        }
    }

    private void initChild(String letter) {
        LayoutParams params = new LayoutParams(30, -2);
        if (!letter.equals("Z")) params.bottomMargin = 2;

        TextView child = new TextView(getContext());
        child.setIncludeFontPadding(false);
        child.setTextSize(textSize);
        child.setText(letter);
        child.setTextColor(unselectedColor);
        child.setOnClickListener(listener);
        addView(child, params);

        if (selectedTextView == null) {
            selectedTextView = child;
            selectedTextView.setTextColor(selectedColor);
        }
    }

    private void selected(View view) {
        if (view != selectedTextView) {
            selectedTextView.setTextColor(unselectedColor);
            selectedTextView = (TextView) view;
            selectedTextView.setTextColor(selectedColor);

            if (letterSelectedListener != null) {
                letterSelectedListener.onLetterSelected(selectedTextView.getText().toString());
            }
        }
    }

    public void nextPosition() {
        int index = indexOfChild(selectedTextView);
        index--;
        if (index < 0) index = 0;
        selected(getChildAt(index));
    }

    public interface LetterSelectedListener {
        void onLetterSelected(String letter);
    }
}
