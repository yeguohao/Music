package com.yeguohao.music.components.search.dispose;

import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.javabean.SearchInfo;

public class SearchDispose extends RecyclerDispose<SearchInfo.DataBean.SongBean.ListBean> {

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, SearchInfo.DataBean.SongBean.ListBean dataItem) {
        TextView textView = holder.getTextView(R.id.search_result_song);
        textView.setText(dataItem.getSongname() + "-" + dataItem.getSinger().get(0).getName());
    }
}
