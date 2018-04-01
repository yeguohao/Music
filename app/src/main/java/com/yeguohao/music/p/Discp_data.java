package com.yeguohao.music.p;

import com.bumptech.glide.Glide;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.disc.adapters.DiscAdapte;
import com.yeguohao.music.disc.fragments.DiscFragment;
import com.yeguohao.music.javabean.CdInfo;
import com.yeguohao.music.main.components.recommend.apis.RecommendApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class Discp_data {

    public static final String SONG_NAME = "songName";
    public static final String SINGER_NAME = "singerName";
    public static final String ALBUM_NAME = "albumName";
    public static final String MUSICITEM = "music_item";
    
    private DiscAdapte discAdapte;
    private String disstid;

    private RecommendApi recommendApi = RetrofitInstance.Retrofit().create(RecommendApi.class);

    public Discp_data(DiscAdapte adapte, String disstid) {
        this.discAdapte = adapte;
        this.disstid = disstid;
    }

    private void get() {
        recommendApi.getCdInfo(disstid)
                .filter(cdInfo -> cdInfo.getCode() != -1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cdInfo -> {
                    CdInfo.CdlistBean cdlistBean = cdInfo.getCdlist().get(0);
                }, throwable -> {

                });
    }

    public void get(int position, String...names) {

    }



}
