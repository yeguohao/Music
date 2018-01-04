package com.yeguohao.music.components.player.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeguohao.music.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LyricRecyclerAdapter extends RecyclerView.Adapter<LyricRecyclerAdapter.LyricHolder> {

    private static final String TAG = "LyricRecyclerAdapter";
    private Map<String, String> data = new HashMap<>();
    private List<String> indexes = new ArrayList<>();

    private int selectedPosition = -1;
    private int colorWhite;
    private int colorDark;

    public LyricRecyclerAdapter(Activity activity) {
        colorWhite = activity.getResources().getColor(R.color.textColorWhite);
        colorDark = activity.getResources().getColor(R.color.textColorDark);
    }

    public void setData(Map<String, String> data, List<String> index) {
        this.indexes = index;
        this.data.clear();
        this.data.putAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        this.data.clear();
        this.indexes = null;
        notifyDataSetChanged();
    }

    public void setSelectedLyric(int selectedPosition) {
        if (this.selectedPosition != selectedPosition) {
            this.selectedPosition = selectedPosition;
            notifyDataSetChanged();
        }
    }

    @Override
    public LyricHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lyric, parent, false);
        return new LyricHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LyricHolder holder, int position) {
        if (selectedPosition == position) {
            Log.e(TAG, "onBindViewHolder: " + position );
            holder.textView.setTextColor(colorWhite);
        } else {
            holder.textView.setTextColor(colorDark);
        }
        holder.textView.setText(data.get(indexes.get(position)));
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
