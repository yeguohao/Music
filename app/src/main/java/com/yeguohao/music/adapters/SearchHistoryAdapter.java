package com.yeguohao.music.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.views.SearchHistory;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private SearchHistory.SearchRecord record;
    private SearchHistory.SearchKeySelectedListener listener;

    public SearchHistoryAdapter(int layoutResId, SearchHistory.SearchRecord record) {
        super(layoutResId);
        this.record = record;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.search_key, item);
        helper.getView(R.id.search_history_delete).setOnClickListener(view -> record.delete(item));

        setOnItemClickListener((adapter, view, position) -> {
            if (listener != null) {
                listener.onSelected(getItem(position));
            }
        });
    }

    public void setListener(SearchHistory.SearchKeySelectedListener listener) {
        this.listener = listener;
    }

}
