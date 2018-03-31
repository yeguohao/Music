package com.yeguohao.music.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yeguohao.music.R;
import com.yeguohao.music.adapters.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistory extends FrameLayout {

    @BindView(R.id.search_clear) ImageView clear;
    @BindView(R.id.search_history_recycler) RecyclerView recycler;

    private SearchRecord record = SearchRecord.getSearchRecord();
    private SearchHistoryAdapter adapter;

    private SearchKeySelectedListener listener;

    public SearchHistory(@NonNull Context context) {
        this(context, null);
    }

    public SearchHistory(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchHistory(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.search_history, this);
        ButterKnife.bind(this);

        initView();
        post(this::fetchData);
    }

    private void fetchData() {
        SearchRecord.restoreInfo(getContext());
    }

    private void initView() {
        recycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter(R.layout.item_search_history, record);
        recycler.setAdapter(adapter);

        RecyclerView.LayoutManager lm = recycler.getLayoutManager();
        lm.setAutoMeasureEnabled(false);

        record.setListener(this::changeUi);
        clear.setOnClickListener(view -> record.clear());
    }

    private void changeUi() {
        if (record.isEmpty()) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            adapter.setNewData(null);
            adapter.addData(record.searchKeys);
            adapter.notifyDataSetChanged();
        }
    }

    public void pause() {
        SearchRecord.storeInfo(getContext());
    }

    public void setListener(SearchKeySelectedListener listener) {
        this.listener = listener;
        adapter.setListener(listener);
    }

    public static class SearchRecord {

        private interface OnDataChangedListener {
            void onDataChanged();
        }

        private static final String SEARCH_HISTORY_FILE = "search_history_file";

        private final List<String> searchKeys = new ArrayList<>();

        private static SearchRecord searchRecord;

        private OnDataChangedListener listener;

        public static SearchRecord getSearchRecord() {
            if (searchRecord == null) {
                searchRecord = new SearchRecord();
            }
            return searchRecord;
        }

        private static void restoreInfo(Context context) {
            searchRecord.searchKeys.clear();
            SharedPreferences preferences = context.getSharedPreferences(SEARCH_HISTORY_FILE, Context.MODE_PRIVATE);
            int size = preferences.getInt("size", 0);
            for (int i = 0; i < size; i++) {
                searchRecord.searchKeys.add(i, preferences.getString(i + "", null));
            }

            if (searchRecord.listener != null) {
                searchRecord.listener.onDataChanged();
            }
        }

        private static void storeInfo(Context context) {
            SharedPreferences preferences = context.getSharedPreferences(SEARCH_HISTORY_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear().apply();
            int size = searchRecord.searchKeys.size();
            editor.putInt("size", size);
            for (int i = 0; i < size; i++) {
                editor.putString(i + "", searchRecord.searchKeys.get(i));
            }
            editor.apply();
            searchRecord.listener = null;
        }

        public void push(String searchKey) {
            if (!searchKeys.contains(searchKey)) {
                searchKeys.add(searchKey);
            } else {
                searchKeys.remove(searchKey);
                searchKeys.add(searchKeys.size(), searchKey);
            }
            listener.onDataChanged();
        }

        public void delete(int index) {
            searchKeys.remove(index);
            listener.onDataChanged();
        }

        public void delete(String val) {
            if (searchKeys.remove(val)) {
                listener.onDataChanged();
            }
        }

        public void clear() {
            searchKeys.clear();
            listener.onDataChanged();
        }

        public boolean isEmpty() {
            return searchKeys.size() == 0;
        }

        public void setListener(OnDataChangedListener listener) {
            this.listener = listener;
        }
    }

    public interface SearchKeySelectedListener {
        void onSelected(String searchKey);
    }
}
