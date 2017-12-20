package com.yeguohao.music.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerDispose<T> {

    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, T dataItem) {

    }

    protected void textViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, TextView textView) {

    }

    protected void imageViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position, ImageView imageView) {

    }

    protected void itemViewClick(BaseRecyclerAdapter.InnerViewHolder holder, int position,T dataItem, View itemView) {

    }

}
