package com.yeguohao.music.song.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.javabean.SingerSongList;
import com.yeguohao.music.javabean.SongList;
import com.yeguohao.music.javabean.TopSongList;
import com.yeguohao.music.main.components.rank.apis.RankApi;
import com.yeguohao.music.main.components.singer.apis.SingerApi;
import com.yeguohao.music.song.adapters.SongAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.yeguohao.music.player.PlayerConstance.SINGER_IMG_URL;
import static com.yeguohao.music.song.activities.SongActivity.SINGER_MID;
import static com.yeguohao.music.song.activities.SongActivity.TOP_ID;
import static com.yeguohao.music.song.activities.SongActivity.TYPE;
import static com.yeguohao.music.song.activities.SongActivity.TYPE_SINGER;

public class SongFragment extends BaseFragment {

    @BindView(R.id.song_recycler) RecyclerView recycler;
    @BindView(R.id.song_title) TextView title;
    @BindView(R.id.song_background) ImageView background;

    private SingerApi singerApi = RetrofitInstance.Retrofit().create(SingerApi.class);
    private RankApi rankApi = RetrofitInstance.Retrofit().create(RankApi.class);
    private SongAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initView(View root) {
        adapter = new SongAdapter(R.layout.item_song);
        adapter.setType(getArguments().getString(TYPE, TYPE_SINGER));
//        ((ViewGroup) root).removeView(background);
//        adapter.setHeaderView(background);

        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
    }

    @Override
    protected void fetch() {
        String type = getArguments().getString(TYPE, TYPE_SINGER);
        if (type.equals(TYPE_SINGER)) {
            String singerMid = getArguments().getString(SINGER_MID, "");
            singerApi.songList(singerMid)
                    .filter(singerSongList -> singerSongList.getCode() == 0)
                    .map(this::cover)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::result, this::error);
        } else {
            String topId = getArguments().getString(TOP_ID, "");
            rankApi.topSongList(topId)
                    .filter(topSongList -> topSongList.getCode() == 0)
                    .map(this::cover)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::result, this::error);
        }
    }

    private void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void result(SongList songList) {
        title.setText(songList.getTitle());
        Glide.with(this).load(songList.getImgUrl()).into(background);
        adapter.replaceData(songList.getSongs());
    }

    private SongList cover(TopSongList topSongList) {
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

    private SongList cover(SingerSongList singerSongList) {
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

    public static SongFragment newInstance(String type, String topId, String singerMid) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(TOP_ID, topId);
        args.putString(SINGER_MID, singerMid);
        SongFragment fragment = new SongFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
