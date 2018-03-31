package com.yeguohao.music.disc.adapters;

import android.app.Activity;

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

public class DiscAdapter extends BaseQuickAdapter<CdInfo.CdlistBean.SonglistBean, BaseViewHolder> {

    public DiscAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CdInfo.CdlistBean.SonglistBean item) {
        helper.setText(R.id.disc_songname, item.getSongname());
        helper.setText(R.id.disc_albumname, item.getSinger().get(0).getName() + "-" + item.getAlbumname());

        helper.itemView.setOnClickListener(
                view -> RxArrays.fill(Observable.fromIterable(getData()), this::bean2MusicItem)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicItems -> {
                    Activity activity = (Activity) helper.itemView.getContext();
                    int position = helper.getLayoutPosition();
                    PlayerActivity.startActivity(activity, musicItems, position);
                }));
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
