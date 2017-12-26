package com.yeguohao.music.components.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeguohao.music.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class LyricRecyclerAdapter extends RecyclerView.Adapter<LyricRecyclerAdapter.LyricHolder> {

    private Map<String, String> data = new LinkedHashMap<>();
    private String[] index;

    public void setData(Map<String, String> data, String[] index) {
        this.index = index;
        this.data.clear();
        this.data.putAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        this.data.clear();
        this.index = null;
        notifyDataSetChanged();
    }

    @Override
    public LyricHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lyric, parent, false);
        return new LyricHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LyricHolder holder, int position) {
        holder.textView.setText(data.get(index[position]));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class LyricHolder extends RecyclerView.ViewHolder {

        TextView textView;

        LyricHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
