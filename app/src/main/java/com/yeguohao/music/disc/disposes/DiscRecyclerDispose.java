package com.yeguohao.music.disc.disposes;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.player.activities.PlayerActivity;
import com.yeguohao.music.player.entities.Song;
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
        List<Song> songs = new ArrayList<>();
        List<CdInfo.CdlistBean.SonglistBean> datas = holder.getAdapter().getData();
        for (CdInfo.CdlistBean.SonglistBean bean : datas) {
            Song info = new Song();
            info.setSongName(bean.getSongname());
            info.setSongMid(bean.getSongmid());
            info.setSingerName(bean.getSinger().get(0).getName());
            info.setAlbumMid(bean.getAlbummid());
            songs.add(info);
        }
        MediaPlayerUtil.getPlayerUtil().addSongs(songs);
        MediaPlayerUtil.getPlayerUtil().load(position);
        PlayerActivity.startActivity((Activity) itemView.getContext());
    }
}
