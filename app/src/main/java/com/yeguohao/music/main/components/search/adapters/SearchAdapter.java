package com.yeguohao.music.main.components.search.adapters;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.javabean.SearchInfo;
import com.yeguohao.music.player.activities.PlayerActivity;

public class SearchAdapter extends BaseQuickAdapter<SearchInfo.DataBean.SongBean.ListBean, BaseViewHolder> {

    public SearchAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> {
            SearchInfo.DataBean.SongBean.ListBean item = getItem(position);

            MusicItem musicItem = new MusicItem();
            MusicItem.Description description = musicItem.getDescription();
            description.setSongName(item.getSongname());
            description.setSingerName(item.getSinger().get(0).getName());
            description.setSongMid(item.getSongmid());
            description.setAlbumMid(item.getAlbummid());

            SongStore songStore = PlayerInstance.getSongStore();
            MusicController musicController = PlayerInstance.getMusicController();
            musicController.add(musicItem);

            int index = songStore.songs().indexOf(musicItem);
            musicController.switchSong(index);
            PlayerActivity.startActivity((Activity) view.getContext());
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.DataBean.SongBean.ListBean item) {
        helper.setText(R.id.search_result_song, item.getSongname() + "-" + item.getSinger().get(0).getName());
    }

}
