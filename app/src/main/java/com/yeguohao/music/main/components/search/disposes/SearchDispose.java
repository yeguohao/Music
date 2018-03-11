package com.yeguohao.music.main.components.search.disposes;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.player.activities.PlayerActivity;
import com.yeguohao.music.player.entities.Song;
import com.yeguohao.music.javabean.SearchInfo;

public class SearchDispose extends RecyclerDispose<SearchInfo.DataBean.SongBean.ListBean> {

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, SearchInfo.DataBean.SongBean.ListBean dataItem) {
        TextView textView = holder.getTextView(R.id.search_result_song);
        textView.setText(dataItem.getSongname() + "-" + dataItem.getSinger().get(0).getName());
        holder.setItemViewClick();
    }

    @Override
    protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, SearchInfo.DataBean.SongBean.ListBean dataItem, View itemView) {
        Song info = new Song();
        info.setSongName(dataItem.getSongname());
        info.setSongMid(dataItem.getSongmid());
        info.setSingerName(dataItem.getSinger().get(0).getName());
        info.setAlbumMid(dataItem.getAlbummid());

        MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();
        playerUtil.addSong(info);
        playerUtil.load(playerUtil.getSongs().size() - 1);
        PlayerActivity.startActivity((Activity) itemView.getContext());
    }
}
