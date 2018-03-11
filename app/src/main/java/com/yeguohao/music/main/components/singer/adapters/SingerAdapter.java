package com.yeguohao.music.main.components.singer.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.common.TitleAndView;
import com.yeguohao.music.javabean.V8;
import com.yeguohao.music.song.activities.SongActivity;

import static com.yeguohao.music.player.PlayerConstance.SINGER_IMG_URL;

public class SingerAdapter extends BaseQuickAdapter<V8.Data.List, BaseViewHolder> implements TitleAndView {

    private RequestOptions options = RequestOptions.circleCropTransform();

    public SingerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, V8.Data.List item) {
        ImageView img = helper.getView(R.id.singer_img);
        Glide.with(helper.itemView).load(String.format(SINGER_IMG_URL, item.getFsinger_mid()))
                .apply(options).into(img);
        helper.setText(R.id.singer, item.getFsinger_name());

        helper.itemView.setOnClickListener(view ->
                SongActivity.start((Activity) view.getContext(), SongActivity.TYPE_SINGER, "", item.getFsinger_mid()));
    }

    @Override
    public String getTitle(int position) {
        if (position < 10) return "热门";
        else return getItem(position).getFindex();
    }
}
