package com.yeguohao.music.flavour.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FlavourAdapter extends BaseQuickAdapter<List<String>, BaseViewHolder> {

    public FlavourAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<String> item) {

    }
}
