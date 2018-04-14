package com.yeguohao.music.main.components.playqueue.adapters;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.main.components.playqueue.fragments.PlaySongQueue;

public class PlaySongQueueAdapter extends BaseQuickAdapter<MusicItem, BaseViewHolder> {

    private PlaySongQueue playSongQueue;

    public PlaySongQueueAdapter(int layoutResId) {
        super(layoutResId);
        setListener();
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicItem item) {
        MusicItem.Description description = item.getDescription();
        helper.setText(R.id.song_name, description.getSongName());
        helper.getView(R.id.favorite).setSelected(item.isFavorite());
        helper.getView(R.id.playing).setSelected(isPlaying(item));
        helper.addOnClickListener(R.id.favorite).addOnClickListener(R.id.remove);
    }

    private boolean isPlaying(MusicItem item) {
        SongStore songStore = PlayerInstance.getSongStore();
        String curSongMid = songStore.song(songStore.currentIndex()).getDescription().getSongMid();
        return curSongMid.equals(item.getDescription().getSongMid());
    }

    private void setListener() {
        setOnItemChildClickListener((adapter, view, position) -> {
            MusicItem item = getItem(position);
            int id = view.getId();
            if (id == R.id.favorite) {
                favorite(item, view);
            } else if (id == R.id.remove) {
                removeFromQueue(item, position);
            }
        });
        setOnItemClickListener((adapter, view, position) -> {
            MusicItem item = getItem(position);
            assert item != null;

            if (!item.isPlaying()) {
                MusicController musicController = PlayerInstance.getMusicController();
                musicController.switchSong(position);
                playSongQueue.switchSong();
                notifyDataSetChanged();
            }
        });
    }

    private void favorite(MusicItem item, View favorite) {
        MusicController musicController = PlayerInstance.getMusicController();
        musicController.favorite(item);
        favorite.setSelected(item.isFavorite());
    }

    private void removeFromQueue(MusicItem item, int position) {
        SongStore songStore = PlayerInstance.getSongStore();
        songStore.removeSong(item);
        remove(position);
        if (songStore.songs().isEmpty()) {
            playSongQueue.dismiss();
        } else if (item.isPlaying()) {
            MusicController musicController = PlayerInstance.getMusicController();
            musicController.switchSong(position);
            musicController.play();
        }
    }

    public void setFragment(PlaySongQueue playSongQueue) {
        this.playSongQueue = playSongQueue;
    }

}
