package com.yeguohao.music.components.recommend.dispose;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.components.disc.DiscActivity;
import com.yeguohao.music.javabean.DiscList;

public class RecommendRecyclerDispose extends RecyclerDispose<DiscList.Data.List> {

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, DiscList.Data.List dataItem) {
        ImageView imageView = holder.getImageView(R.id.recommend_disc_image);
        TextView name = holder.getTextView(R.id.recommend_disc_name);
        TextView dissName = holder.getTextView(R.id.recommend_disc_dissname);

        Glide.with(holder.itemView).load(dataItem.getImgurl()).into(imageView);
        name.setText(dataItem.getCreator().getName());
        dissName.setText(dataItem.getDissname());

        holder.setItemViewClick();
    }

    @Override
    protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, DiscList.Data.List dataItem, View itemView) {
        Activity activity = (Activity) itemView.getContext();
        Intent intent = new Intent(activity, DiscActivity.class);
        intent.putExtra("disstid", dataItem.getDissid());
        activity.startActivity(intent);
    }

}
