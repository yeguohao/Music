package com.yeguohao.music.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected abstract int layout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutID = layout();
        if (layoutID == -1) {
            throw new IllegalArgumentException("Layout ID should be no -1!");
        }
        View root = inflater.inflate(layoutID, container, false);
        ButterKnife.bind(this, root);
        initView(root);
        fetch();
        return root;
    }

    protected void initView(View root) {

    }

    protected void fetch() {

    }

}
