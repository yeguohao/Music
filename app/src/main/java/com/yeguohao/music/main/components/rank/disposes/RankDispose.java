package com.yeguohao.music.main.components.rank.disposes;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.song.activities.SongActivity;
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

        holder.setItemViewClick();
    }

    @Override
    protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, Rank.DataBean.TopListBean dataItem, View itemView) {
        SongActivity.start((Activity) itemView.getContext(), SongActivity.TYPE_RANK, dataItem.getId() + "", "");
    }

    private void setText(String str, TextView textView) {
        Spannable wordtoSpan = new SpannableString(str);
        wordtoSpan.setSpan(new ForegroundColorSpan(0xffff0000), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(0xff00ffff), 20, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordtoSpan);
    }
}
