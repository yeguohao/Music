package com.yeguohao.music.components.search;

import android.os.Bundle;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;

public class Search extends BaseFragment {

    @Override
    protected int layout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void fetch() {

    }

    public static Search newInstance() {
        Bundle args = new Bundle();
        Search fragment = new Search();
        fragment.setArguments(args);
        return fragment;
    }
}
