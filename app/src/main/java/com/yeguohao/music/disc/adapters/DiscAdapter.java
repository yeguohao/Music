package com.yeguohao.music.disc.adapters;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.rx.RxArrays;
import com.yeguohao.music.javabean.CdInfo;
import com.yeguohao.music.player.activities.PlayerActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yeguohao.music.common.Util.log;

public class DiscAdapter extends BaseQuickAdapter<CdInfo.CdlistBean.SonglistBean, BaseViewHolder> {

    public DiscAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> click(position, view.getContext()));
    }

    @Override
    protected void convert(BaseViewHolder helper, CdInfo.CdlistBean.SonglistBean item) {
        helper.setText(R.id.disc_songname, item.getSongname());
        helper.setText(R.id.disc_albumname, item.getSinger().get(0).getName() + "-" + item.getAlbumname());
    }

    private void click(int position, Context context) {
        RxArrays.fill(Observable.fromIterable(getData()), this::bean2MusicItem)
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicItems -> {
                    Activity activity = (Activity) context;
                    PlayerActivity.startActivity(activity, musicItems, position);
                });
    }

    private MusicItem bean2MusicItem(CdInfo.CdlistBean.SonglistBean bean) {
        MusicItem musicItem = new MusicItem();
        MusicItem.Description description = musicItem.getDescription();
        description.setSongName(bean.getSongname());
        description.setSingerName(bean.getSinger().get(0).getName());
        description.setSongMid(bean.getSongmid());
        description.setAlbumMid(bean.getAlbummid());
        return musicItem;
    }

}
