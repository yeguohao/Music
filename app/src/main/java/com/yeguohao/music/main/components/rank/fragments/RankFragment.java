package com.yeguohao.music.main.components.rank.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.decoration.RankSpaceItemDecoration;
import com.yeguohao.music.main.components.rank.adapters.RankAdapter;
import com.yeguohao.music.main.components.rank.apis.RankApi;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RankFragment extends BaseFragment {

    @BindDimen(R.dimen.dp20) int dp20;
    @BindColor(R.color.colorPrimary) int color;
    @BindView(R.id.rank_recycler) RecyclerView recycler;

    private RankApi rankApi = RetrofitInstance.Retrofit().create(RankApi.class);

    private RankAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void initView(View root) {
        recycler.addItemDecoration(new RankSpaceItemDecoration(dp20, color));
        adapter = new RankAdapter(R.layout.item_rank);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void fetch() {
        rankApi.getRank()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rank -> adapter.addData(rank.getData().getTopList()),
                        throwable -> {
                            throwable.printStackTrace();
                        });
    }

    public static RankFragment newInstance() {
        return new RankFragment();
    }

}
