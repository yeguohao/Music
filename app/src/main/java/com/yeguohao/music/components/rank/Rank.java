package com.yeguohao.music.components.rank;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.common.decoration.RankSpaceItemDecoration;
import com.yeguohao.music.components.rank.dispose.RankDispose;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class Rank extends BaseFragment {

    @BindDimen(R.dimen.dp20)
    int dp20;

    @BindColor(R.color.colorPrimary)
    int color;

    @BindView(R.id.rank_recycler)
    RecyclerView recycler;

    private BaseRecyclerAdapter recyclerAdapter;

    @Override
    protected int layout() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void initView(View root) {
        recycler.addItemDecoration(new RankSpaceItemDecoration(dp20, color));
        recyclerAdapter = new BaseRecyclerAdapter<>(R.layout.item_rank, new RankDispose());
        recycler.setAdapter(recyclerAdapter);
    }

    @Override
    protected void fetch() {
        new Instance().Rank().getRank()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rank -> {
                            recyclerAdapter.setData(rank.getData().getTopList());
                        },
                        throwable -> {
                            Log.e("tag", "fetch: ");
                            throwable.printStackTrace();
                        });
    }

    public static Rank newInstance() {

        Bundle args = new Bundle();

        Rank fragment = new Rank();
        fragment.setArguments(args);
        return fragment;
    }

}
