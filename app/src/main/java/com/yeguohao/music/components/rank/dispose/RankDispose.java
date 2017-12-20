package com.yeguohao.music.components.rank.dispose;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.javabean.Rank;

public class RankDispose extends RecyclerDispose<Rank.DataBean.TopListBean> {

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, Rank.DataBean.TopListBean dataItem) {
        ImageView img = holder.getImageView(R.id.rank_img);
        TextView rank1 = holder.getTextView(R.id.rank_1);
        TextView rank2 = holder.getTextView(R.id.rank_2);
        TextView rank3 = holder.getTextView(R.id.rank_3);

        Glide.with(holder.itemView.getContext()).load(dataItem.getPicUrl()).into(img);
        Rank.DataBean.TopListBean.SongListBean songListBean1 = dataItem.getSongList().get(0);
        Rank.DataBean.TopListBean.SongListBean songListBean2 = dataItem.getSongList().get(1);
        Rank.DataBean.TopListBean.SongListBean songListBean3 = dataItem.getSongList().get(2);

        rank1.setText("1 " + songListBean1.getSongname() + "-" + songListBean1.getSingername());
        rank2.setText("2 " + songListBean2.getSongname() + "-" + songListBean1.getSingername());
        rank3.setText("3 " + songListBean3.getSongname() + "-" + songListBean1.getSingername());
    }

}
