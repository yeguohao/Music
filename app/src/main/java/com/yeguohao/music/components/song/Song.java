package com.yeguohao.music.components.song;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.components.song.dispose.SongDispose;
import com.yeguohao.music.javabean.SingerSongList;
import com.yeguohao.music.javabean.SongList;
import com.yeguohao.music.javabean.TopSongList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.yeguohao.music.components.player.PlayerConstance.SINGER_IMG_URL;
import static com.yeguohao.music.components.song.SongActivity.SINGER_MID;
import static com.yeguohao.music.components.song.SongActivity.TOP_ID;
import static com.yeguohao.music.components.song.SongActivity.TYPE;
import static com.yeguohao.music.components.song.SongActivity.TYPE_SINGER;

public class Song extends BaseFragment {

    private static final String TAG = "Song";
    @BindView(R.id.song_recycler)
    RecyclerView recycler;

    @BindView(R.id.song_title)
    TextView title;

    @BindView(R.id.song_background)
    ImageView background;

    private BaseRecyclerAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initView(View root) {
        SongDispose dispose = new SongDispose();
        dispose.setType(getArguments().getString(TYPE, TYPE_SINGER));
        adapter = new BaseRecyclerAdapter<>(R.layout.item_song, dispose);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
    }

    @Override
    protected void fetch() {
        String type = getArguments().getString(TYPE, TYPE_SINGER);
        Instance instance = new Instance();
        if (type.equals(TYPE_SINGER)) {
            String singerMid = getArguments().getString(SINGER_MID, "");
            instance.Singer().songList(singerMid)
                    .filter(singerSongList -> singerSongList.getCode() == 0)
                    .map(this::conver)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::result, this::error);
        } else {
            String topId = getArguments().getString(TOP_ID, "");
            instance.Rank().topSongList(topId)
                    .filter(topSongList -> topSongList.getCode() == 0)
                    .map(this::conver)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::result, this::error);
        }
    }

    private void error(Throwable throwable) {
        Log.e(TAG, "error: " + throwable);
        throwable.printStackTrace();
    }

    private void result(SongList songList) {
        title.setText(songList.getTitle());
        Glide.with(this).load(songList.getImgUrl()).into(background);
        adapter.setData(songList.getSongs());
    }

    private SongList conver(TopSongList topSongList) {
        SongList songList = new SongList();
        List<SongList.Song> songs = new ArrayList<>();

        TopSongList.Topinfo topinfo = topSongList.getTopinfo();
        songList.setTitle(topinfo.getListName());
        songList.setImgUrl(topinfo.getPic_album());

        List<TopSongList.Songlist> list = topSongList.getSonglist();
        for (TopSongList.Songlist s : list) {
            TopSongList.Songlist.Data data = s.getData();
            SongList.Song song = new SongList.Song();
            song.setSongName(data.getSongname());
            song.setSongMid(data.getSongmid());
            song.setAlbumName(data.getAlbumname());
            song.setSingerName(data.getSinger().get(0).getName());
            songs.add(song);
        }
        songList.setSongs(songs);
        return songList;
    }

    private SongList conver(SingerSongList singerSongList) {
        SongList songList = new SongList();
        List<SongList.Song> songs = new ArrayList<>();

        SingerSongList.Data data = singerSongList.getData();
        songList.setTitle(data.getSinger_name());
        songList.setImgUrl(String.format(SINGER_IMG_URL, data.getSinger_mid()));

        for (SingerSongList.Data.List list : data.getList()) {
            SingerSongList.Data.List.MusicData musicData = list.getMusicData();
            SongList.Song song = new SongList.Song();
            song.setSongName(musicData.getSongname());
            song.setSongMid(musicData.getSongmid());
            song.setAlbumName(musicData.getAlbumname());
            song.setSingerName(musicData.getSinger().get(0).getName());
            songs.add(song);
        }
        songList.setSongs(songs);
        return songList;
    }

    public static Song newInstance(String type, String topId, String singerMid) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(TOP_ID, topId);
        args.putString(SINGER_MID, singerMid);
        Song fragment = new Song();
        fragment.setArguments(args);
        return fragment;
    }
}
