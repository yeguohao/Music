package com.yeguohao.music.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.InnerViewHolder> {

    private List<T> data = new ArrayList<>();
    private int layout;
    private RecyclerDispose<T> dispose;

    private LayoutInflater inflater;

    public BaseRecyclerAdapter(int layout, RecyclerDispose<T> dispose) {
        this.layout = layout;
        this.dispose = dispose;
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerAdapter.InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = inflater.inflate(layout, parent, false);
        return new InnerViewHolder(itemView, dispose);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.InnerViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {

        private RecyclerDispose<T> dispose;
        private T t;

        InnerViewHolder(View itemView, RecyclerDispose<T> dispose) {
            super(itemView);
            this.dispose = dispose;
        }

        void setData(T t) {
            this.t = t;
            dispose.refreshUI(this, getLayoutPosition(), t);
        }

        public TextView getTextView(int viewId) {
            return itemView.findViewById(viewId);
        }

        public ImageView getImageView(int viewId) {
            return itemView.findViewById(viewId);
        }

        public void setTextViewClick(int viewId) {
            getTextView(viewId).setOnClickListener(view -> dispose.textViewClick(this, getLayoutPosition(), (TextView) view));
        }

        public void setImageViewClick(int viewId) {
            getImageView(viewId).setOnClickListener(view -> dispose.imageViewClick(this, getLayoutPosition(), (ImageView) view));
        }

        public void setItemViewClick() {
            itemView.setOnClickListener(view -> dispose.itemViewClick(this, getLayoutPosition(), t, view));
        }

        public void removeItem() {
            if (data.remove(t)) {
                notifyDataSetChanged();
            }
        }

    }
}
