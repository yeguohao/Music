package com.yeguohao.music.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yeguohao.music.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.yeguohao.music.common.Util.log;

public class SearchEditText extends FrameLayout {

    @BindView(R.id.search_edit) EditText editText;
    @BindView(R.id.search_close) ImageView close;

    public SearchEditText(@NonNull Context context) {
        this(context, null);
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.search_edittext, this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        close.setOnClickListener(view -> editText.setText(""));

        RxTextView.textChanges(editText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handle, throwable -> {
                    log(throwable);
                });
    }

    private void handle(CharSequence key) {
        if (TextUtils.isEmpty(key)) {
            close.setVisibility(GONE);
            if (listener != null) {
                listener.onCancel();
            }
        } else {
            SearchHistory.SearchRecord.getSearchRecord().push(key.toString());
            String encodeKey = null;
            try {
                encodeKey = URLEncoder.encode(key.toString(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            close.setVisibility(VISIBLE);
            if (listener != null) {
                listener.onStart(encodeKey);
            }
        }
    }

    private OnSearchListener listener;

    public void setListener(OnSearchListener listener) {
        this.listener = listener;
    }

    public void setText(String searchKey) {
        editText.setText(searchKey);
        editText.setSelection(searchKey.length());
    }

    public interface OnSearchListener {
        void onStart(String key);
        void onCancel();
    }

}
