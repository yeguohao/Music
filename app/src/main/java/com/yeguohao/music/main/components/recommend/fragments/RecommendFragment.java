package com.yeguohao.music.main.components.recommend.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.main.components.recommend.adapters.RecommendAdapter;
import com.yeguohao.music.main.components.recommend.adapters.RecommendPagerAdapter;
import com.yeguohao.music.main.components.recommend.disposes.RecommendRecyclerDispose;
import com.yeguohao.music.main.components.recommend.apis.RecommendApi;
import com.yeguohao.music.views.Banner;
import com.yeguohao.music.views.LoadingView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecommendFragment extends BaseFragment {

    private static final String TAG = "RecommendApi";
    @BindView(R.id.recommend_banner)
    Banner banner;

    @BindView(R.id.recommend_title)
    TextView title;

    @BindView(R.id.recommend_recycler)
    RecyclerView recycler;

    @BindView(R.id.recommend_loading)
    LoadingView loadingView;

    private RecommendApi recommendApi = RetrofitInstance.Retrofit().create(RecommendApi.class);
    private RecommendPagerAdapter pagerAdapter;
    private RecommendAdapter recyclerAdapter;

    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();

        RecommendFragment fragment = new RecommendFragment();
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

        recyclerAdapter = new RecommendAdapter(R.layout.item_recommend);
        ((ViewGroup) root).removeView(banner);
        recyclerAdapter.addHeaderView(banner);
        ((ViewGroup) root).removeView(title);
        recyclerAdapter.addHeaderView(title);
        recycler.setAdapter(recyclerAdapter);
    }

    @Override
    protected void fetch() {
        recommendApi.getRecommend()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    pagerAdapter.setSliders(bean.getData().getSlider());
                    banner.notifyDataSetChanged();
                }, throwable -> {
                    Log.e(TAG, "getRecommend: " + throwable );
                });
        recommendApi.getDiscList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discList -> {
                    loadingView.cancel();
                    recycler.setVisibility(View.VISIBLE);
                    recyclerAdapter.addData(discList.getData().getList());
                }, throwable -> {
                    Log.e(TAG, "getDiscList: " + throwable );
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
