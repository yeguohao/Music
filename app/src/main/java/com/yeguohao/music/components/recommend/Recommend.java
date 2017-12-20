package com.yeguohao.music.components.recommend;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.common.ItemDecoration.RecyclerTitleItemDecoration;
import com.yeguohao.music.components.recommend.adapter.RecommendPagerAdapter;
import com.yeguohao.music.components.recommend.dispose.RecommendRecyclerDispose;
import com.yeguohao.music.view.Banner;
import com.yeguohao.music.view.LoadingView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class Recommend extends BaseFragment {

    @BindView(R.id.recommend_banner)
    Banner banner;

    @BindView(R.id.recommend_recycler)
    RecyclerView recycler;

    @BindView(R.id.recommend_loading)
    LoadingView loadingView;

    private Instance instance = new Instance();
    private RecommendPagerAdapter pagerAdapter;
    private BaseRecyclerAdapter recyclerAdapter;

    public static Recommend newInstance() {
        Bundle args = new Bundle();

        Recommend fragment = new Recommend();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(View root) {
        loadingView.start();

        pagerAdapter = new RecommendPagerAdapter();
        banner.setPagerAdapter(pagerAdapter);

        recyclerAdapter = new BaseRecyclerAdapter<>(R.layout.item_recommend, new RecommendRecyclerDispose());
        recycler.setAdapter(recyclerAdapter);
        recycler.addItemDecoration(new RecyclerTitleItemDecoration());
    }

    @Override
    protected void fetch() {
        instance.Recommend().getRecommend()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    pagerAdapter.setSliders(bean.getData().getSlider());
                    banner.notifyDataSetChanged();
                });
        instance.Recommend().getDiscList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discList -> {
                    loadingView.cancel();
                    recycler.setVisibility(View.VISIBLE);
                    recyclerAdapter.setData(discList.getData().getList());
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (banner != null) {
            banner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.resume();
        }
    }
}
