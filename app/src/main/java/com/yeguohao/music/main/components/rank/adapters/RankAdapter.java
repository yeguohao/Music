package com.yeguohao.music.main.components.rank.adapters;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.javabean.Rank;
import com.yeguohao.music.song.activities.SongActivity;

public class RankAdapter extends BaseQuickAdapter<Rank.DataBean.TopListBean, BaseViewHolder> {

    public RankAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> {
            Rank.DataBean.TopListBean item = getItem(position);
            SongActivity.start((Activity) view.getContext(), SongActivity.TYPE_RANK, item.getId() + "", "");
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, Rank.DataBean.TopListBean item) {
        ImageView img = helper.getView(R.id.rank_img);
        TextView rank1 = helper.getView(R.id.rank_1);
        TextView rank2 = helper.getView(R.id.rank_2);
        TextView rank3 = helper.getView(R.id.rank_3);
        Glide.with(helper.itemView.getContext()).load(item.getPicUrl()).into(img);

        Rank.DataBean.TopListBean.SongListBean songListBean1 = item.getSongList().get(0);
        Rank.DataBean.TopListBean.SongListBean songListBean2 = item.getSongList().get(1);
        Rank.DataBean.TopListBean.SongListBean songListBean3 = item.getSongList().get(2);

        rank1.setText("1 " + songListBean1.getSongname() + "-" + songListBean1.getSingername());
        rank2.setText("2 " + songListBean2.getSongname() + "-" + songListBean1.getSingername());
        rank3.setText("3 " + songListBean3.getSongname() + "-" + songListBean1.getSingername());
    }

}
