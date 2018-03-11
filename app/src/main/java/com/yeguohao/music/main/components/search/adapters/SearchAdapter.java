package com.yeguohao.music.main.components.search.adapters;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.javabean.SearchInfo;
import com.yeguohao.music.player.activities.PlayerActivity;
import com.yeguohao.music.player.entities.Song;

public class SearchAdapter extends BaseQuickAdapter<SearchInfo.DataBean.SongBean.ListBean, BaseViewHolder> {

    public SearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.DataBean.SongBean.ListBean item) {
        helper.setText(R.id.search_result_song, item.getSongname() + "-" + item.getSinger().get(0).getName());

        helper.itemView.setOnClickListener(view -> {
            Song info = new Song();
            info.setSongName(item.getSongname());
            info.setSongMid(item.getSongmid());
            info.setSingerName(item.getSinger().get(0).getName());
            info.setAlbumMid(item.getAlbummid());

            MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();
            playerUtil.addSong(info);
            playerUtil.load(playerUtil.getSongs().size() - 1);
            PlayerActivity.startActivity((Activity) view.getContext());
        });
    }
}
