package com.yeguohao.music.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistory extends FrameLayout {

    private static final String TAG = "SearchHistory";
    @BindView(R.id.search_clear)
    ImageView clear;

    @BindView(R.id.search_history_recycler)
    RecyclerView recycler;

    private SearchRecord record = SearchRecord.getSearchRecord();
    private BaseRecyclerAdapter<String> adapter;

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
        adapter = new BaseRecyclerAdapter<>(R.layout.item_search_history, new HistoryDispose());
        adapter.addData(record.searchKeys);
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
            adapter.notifyDataSetChanged();
        }
    }

    public void pause() {
        SearchRecord.storeInfo(getContext());
    }

    public void setListener(SearchKeySelectedListener listener) {
        this.listener = listener;
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
            int size = Integer.parseInt(preferences.getString("size", "0"));
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
            editor.putString("size", size + "");
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

    private class HistoryDispose extends RecyclerDispose<String> {

        @Override
        protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, String dataItem) {
            TextView title = holder.getTextView(R.id.search_key);
            ImageView delete = holder.getImageView(R.id.search_history_delete);

            title.setText(dataItem);
            delete.setOnClickListener(view -> {
                record.delete(dataItem);
                holder.removeItem();
            });
            holder.setItemViewClick();
        }

        @Override
        protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, String dataItem, View itemView) {
            if (listener != null) {
                listener.onSelected(dataItem);
            }
        }

    }

    public interface SearchKeySelectedListener {
        void onSelected(String searchKey);
    }
}
