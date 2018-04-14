package com.yeguohao.music.flavour.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.main.components.search.adapters.SearchAdapter;

public class FlavourAndRecentlyAdapter extends BaseQuickAdapter<MusicItem, BaseViewHolder> {

    public FlavourAndRecentlyAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> {
            if (listener != null) {
                listener.onSelected(getItem(position));
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicItem item) {
        MusicItem.Description description = item.getDescription();
        helper.setText(R.id.song_name, description.getSongName())
                .setText(R.id.album_name, description.getSingerName());
    }

    private SearchAdapter.OnSearchItemSelectedListener listener;

    public void setListener(SearchAdapter.OnSearchItemSelectedListener listener) {
        this.listener = listener;
    }

}
