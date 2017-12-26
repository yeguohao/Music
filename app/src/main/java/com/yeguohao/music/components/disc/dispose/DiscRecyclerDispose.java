package com.yeguohao.music.components.disc.dispose;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.common.MediaPlayerUtil;
import com.yeguohao.music.common.SongInfo;
import com.yeguohao.music.components.player.PlayerActivity;
import com.yeguohao.music.javabean.CdInfo;

import java.util.ArrayList;
import java.util.List;

public class DiscRecyclerDispose extends RecyclerDispose<CdInfo.CdlistBean.SonglistBean> {

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, CdInfo.CdlistBean.SonglistBean dataItem) {
        TextView songName = holder.getTextView(R.id.disc_songname);
        TextView albumname = holder.getTextView(R.id.disc_albumname);

        songName.setText(dataItem.getSongname());
        albumname.setText(dataItem.getSinger().get(0).getName() + "-" + dataItem.getAlbumname());

        holder.setItemViewClick();
    }

    @Override
    protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, CdInfo.CdlistBean.SonglistBean dataItem, View itemView) {
        List<MediaPlayerUtil.Song> songs = new ArrayList<>();
        List<CdInfo.CdlistBean.SonglistBean> datas = holder.getAdapter().getData();
        for (CdInfo.CdlistBean.SonglistBean bean : datas) {
            MediaPlayerUtil.Song info = new MediaPlayerUtil.Song();
            info.setSongName(dataItem.getSongname());
            info.setSongMid(dataItem.getSongmid());
            info.setSingerName(dataItem.getSinger().get(0).getName());
            info.setAlbumMid(dataItem.getAlbummid());
            songs.add(info);
        }
        MediaPlayerUtil.getPlayerUtil().setSongs(songs, position);
        PlayerActivity.startActivity((Activity) itemView.getContext());
    }
}
