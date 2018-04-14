package com.yeguohao.music.main.components.search.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.javabean.SearchInfo;

public class SearchAdapter extends BaseQuickAdapter<SearchInfo.DataBean.SongBean.ListBean, BaseViewHolder> {

    public SearchAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> {
            SearchInfo.DataBean.SongBean.ListBean item = getItem(position);
            MusicItem musicItem = listBean2MusicItem(item);
            if (listener != null) {
                listener.onSelected(musicItem);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.DataBean.SongBean.ListBean item) {
        helper.setText(R.id.search_result_song, item.getSongname() + "-" + item.getSinger().get(0).getName());
    }

    private MusicItem listBean2MusicItem(SearchInfo.DataBean.SongBean.ListBean bean) {
        MusicItem musicItem = new MusicItem();
        MusicItem.Description description = musicItem.getDescription();
        description.setSongName(bean.getSongname());
        description.setSingerName(bean.getSinger().get(0).getName());
        description.setSongMid(bean.getSongmid());
        description.setAlbumMid(bean.getAlbummid());
        return musicItem;
    }

    private OnSearchItemSelectedListener listener;

    public void setListener(OnSearchItemSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnSearchItemSelectedListener {
        void onSelected(MusicItem item);
    }

}
