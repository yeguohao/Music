package com.yeguohao.music.main.components.recommend.adapters;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.disc.activities.DiscActivity;
import com.yeguohao.music.javabean.DiscList;

public class RecommendAdapter extends BaseQuickAdapter<DiscList.Data.List, BaseViewHolder> {

    public RecommendAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscList.Data.List item) {
        ImageView imageView = helper.getView(R.id.recommend_disc_image);
        TextView name = helper.getView(R.id.recommend_disc_name);
        TextView dissName = helper.getView(R.id.recommend_disc_dissname);

        Glide.with(helper.itemView).load(item.getImgurl()).into(imageView);
        name.setText(item.getCreator().getName());
        dissName.setText(item.getDissname());

        helper.itemView.setOnClickListener(view -> {
            Activity activity = (Activity) view.getContext();
            Intent intent = new Intent(activity, DiscActivity.class);
            intent.putExtra("disstid", item.getDissid());
            activity.startActivity(intent);
        });
    }
}
