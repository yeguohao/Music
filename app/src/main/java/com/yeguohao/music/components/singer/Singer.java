package com.yeguohao.music.components.singer;

import android.os.Bundle;

import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;

public class Singer extends BaseFragment {

    @Override
    protected int layout() {
        return R.layout.fragment_singer;
    }

    public static Singer newInstance() {
        
        Bundle args = new Bundle();
        
        Singer fragment = new Singer();
        fragment.setArguments(args);
        return fragment;
    }
}
