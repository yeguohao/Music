package com.yeguohao.music.disc.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.disc.adapters.DiscAdapter;
import com.yeguohao.music.javabean.CdInfo;
import com.yeguohao.music.main.components.recommend.apis.RecommendApi;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DiscFragment extends BaseFragment {

    @BindView(R.id.disc_backdrop) ImageView backdrop;
    @BindView(R.id.disc_recycler) RecyclerView recycler;
    @BindView(R.id.disc_toolbar) Toolbar toolbar;

    private RecommendApi recommendApi = RetrofitInstance.Retrofit().create(RecommendApi.class);
    private DiscAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_disc;
    }

    @Override
    protected void initView(View root) {
        adapter = new DiscAdapter(R.layout.item_disc);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void fetch() {
        String disstid = getArguments().getString("disstid");
        recommendApi.getCdInfo(disstid)
                .filter(cdInfo -> cdInfo.getCode() != -1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cdInfo -> {
                    CdInfo.CdlistBean cdlistBean = cdInfo.getCdlist().get(0);
                    toolbar.setTitle(cdlistBean.getDissname());
                    adapter.replaceData(cdlistBean.getSonglist());
                    Glide.with(getActivity()).load(cdlistBean.getLogo()).into(backdrop);
                });
    }

    public static DiscFragment newInstance(String disstid) {
        Bundle args = new Bundle();
        args.putString("disstid", disstid);
        DiscFragment fragment = new DiscFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
