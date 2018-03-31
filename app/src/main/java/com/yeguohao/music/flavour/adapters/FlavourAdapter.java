package com.yeguohao.music.flavour.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.impl.MusicItem;

public class FlavourAdapter extends BaseQuickAdapter<MusicItem, BaseViewHolder> {

    public FlavourAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicItem item) {
        MusicItem.Description description = item.getDescription();
        helper.setText(R.id.song_name, description.getSongName())
                .setText(R.id.album_name, description.getSingerName());
    }

}
