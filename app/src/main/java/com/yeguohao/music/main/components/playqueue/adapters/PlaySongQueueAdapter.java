package com.yeguohao.music.main.components.playqueue.adapters;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;

public class PlaySongQueueAdapter extends BaseQuickAdapter<MusicItem, BaseViewHolder> {

    public PlaySongQueueAdapter(int layoutResId) {
        super(layoutResId);
        setListener();
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicItem item) {
        MusicItem.Description description = item.getDescription();
        helper.setText(R.id.song_name, description.getSongName());
        helper.getView(R.id.favorite).setSelected(item.isFavorite());
        helper.getView(R.id.playing).setSelected(item.isPlaying());
        helper.addOnClickListener(R.id.favorite).addOnClickListener(R.id.remove);
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
    }

    private void favorite(MusicItem item, View favorite) {
        MusicController musicController = PlayerInstance.getMusicController();
        musicController.favorite(item);
        favorite.setSelected(item.isFavorite());
    }

    private void removeFromQueue(MusicItem item, int position) {
        SongStore songStore = PlayerInstance.getSongStore();
        songStore.removeSong(item);
        if (item.isPlaying()) {
            PlayerInstance.getMusicController().next();
        }
        remove(position);
    }

}
