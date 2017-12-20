package com.yeguohao.music.components.singer.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.javabean.V8;
import com.yeguohao.music.common.TitleAndView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;
import static com.yeguohao.music.components.player.PlayerConstance.SINGER_IMG_URL;

public class SingerRecycler extends RecyclerView.Adapter<SingerRecycler.SingerHolder> implements TitleAndView {

    private static final String TAG = "SingerRecycler";
    private java.util.List<V8.Data.List> data = new ArrayList<>();

    private RequestOptions options = RequestOptions.circleCropTransform();

    public void setData(List<V8.Data.List> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public SingerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_singer, parent, false);
        return new SingerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SingerHolder holder, int position) {
        V8.Data.List dataItem = data.get(position);
        Glide.with(holder.itemView).load(String.format(SINGER_IMG_URL, dataItem.getFsinger_mid()))
                .apply(options).into(holder.img);
        holder.singer.setText(dataItem.getFsinger_name());
    }

    public String getTitle(int position) {
        if (position < 10) return "热门";
        else return data.get(position).getFindex();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SingerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.singer_img)
        ImageView img;

        @BindView(R.id.singer)
        TextView singer;

        public SingerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}