package com.yeguohao.music.components.disc;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.common.SongInfo;
import com.yeguohao.music.components.disc.dispose.DiscRecyclerDispose;
import com.yeguohao.music.javabean.CdInfo;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class Disc extends BaseFragment {

    private static final String TAG = "Disc";
    @BindView(R.id.disc_backdrop)
    ImageView backdrop;

    @BindView(R.id.disc_recycler)
    RecyclerView recycler;

    @BindView(R.id.disc_toolbar)
    Toolbar toolbar;

    private Instance instance = new Instance();

    private BaseRecyclerAdapter<CdInfo.CdlistBean.SonglistBean> recyclerAdapter;

    @Override
    protected int layout() {
        return R.layout.fragment_disc;
    }

    @Override
    protected void initView(View root) {
        recyclerAdapter = new BaseRecyclerAdapter<>(R.layout.item_disc, new DiscRecyclerDispose());
        recycler.setAdapter(recyclerAdapter);
    }

    @Override
    protected void fetch() {
        String disstid = getArguments().getString("disstid");
        instance.Recommend().getCdInfo(disstid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cdInfo -> {
                    CdInfo.CdlistBean cdlistBean = cdInfo.getCdlist().get(0);

                    toolbar.setTitle(cdlistBean.getDissname());
                    recyclerAdapter.setData(cdlistBean.getSonglist());

                    Glide.with(getActivity()).load(cdlistBean.getLogo()).into(backdrop);
                });
    }

    public static Disc newInstance(String disstid) {

        Bundle args = new Bundle();
        args.putString("disstid", disstid);

        Disc fragment = new Disc();
        fragment.setArguments(args);
        return fragment;
    }
}
