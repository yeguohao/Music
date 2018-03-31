package com.yeguohao.music.main.components.recommend.adapters;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.disc.activities.DiscActivity;
import com.yeguohao.music.javabean.DiscList;

public class RecommendAdapter extends BaseQuickAdapter<DiscList.Data.List, BaseViewHolder> {

    public RecommendAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemClickListener((adapter, view, position) -> {
            Activity activity = (Activity) view.getContext();
            DiscList.Data.List item = getItem(position);

            Intent intent = new Intent(activity, DiscActivity.class);
            intent.putExtra("disstid", item.getDissid());
            activity.startActivity(intent);
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscList.Data.List item) {
        ImageView imageView = helper.getView(R.id.recommend_disc_image);

        Glide.with(helper.itemView).load(item.getImgurl()).into(imageView);
        helper.setText(R.id.recommend_disc_name, item.getCreator().getName())
                .setText(R.id.recommend_disc_name, item.getDissname());

    }

}
