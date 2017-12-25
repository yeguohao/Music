package com.yeguohao.music.components.singer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.A;
import com.yeguohao.music.common.decoration.RecyclerTitleItemDecoration1;
import com.yeguohao.music.components.singer.adapter.SingerRecycler;
import com.yeguohao.music.javabean.V8;
import com.yeguohao.music.view.LetterIndexView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class Singer extends BaseFragment implements LetterIndexView.LetterSelectedListener {

    private static final String TAG = "Singer";
    @BindView(R.id.singer_recycler)
    RecyclerView recycler;

    @BindView(R.id.singer_letter_index)
    LetterIndexView letterIndex;

    private SingerRecycler adapter;

    private int[] letterPosition = new int[26];

    @Override
    protected int layout() {
        return R.layout.fragment_singer;
    }

    @Override
    protected void initView(View root) {
        adapter = new SingerRecycler();
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(false);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new RecyclerTitleItemDecoration1());
        letterIndex.setLetterSelectedListener(this);
    }

    @Override
    protected void fetch() {
        List<V8.Data.List> top10 = new ArrayList<>(10);
        new Instance().Singer().v8()
                .flatMap(v8 -> Observable.fromIterable(v8.getData().getList()))
                .doOnNext(item -> {
                    if (top10.size() < 10) top10.add(item);
                })
                .toSortedList((t1, t2) -> {
                    String t1Index = t1.getFindex();
                    String t2Index = t2.getFindex();
                    return A.StringToA(t1Index) - A.StringToA(t2Index);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lists -> {
                    letterPosition(lists);
                    lists.addAll(0, top10);
                    adapter.setData(lists);
                }, throwable -> {
                    Log.e(TAG, "fetch: " + throwable);
                });
    }

    private void letterPosition(List<V8.Data.List> lists) {
        for (int i = 0; i < lists.size(); i++) {
            String letter = lists.get(i).getFindex();
            if (letter.equals("热") || letter.equals("9")) continue;

            int index = A.StringToA(letter) % 65;
            if (letterPosition[index] == 0) {
                letterPosition[index] = i + 10;
            }
        }
    }

    public static Singer newInstance() {
        Bundle args = new Bundle();
        Singer fragment = new Singer();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLetterSelected(String letter) {
        LinearLayoutManager llm = (LinearLayoutManager) recycler.getLayoutManager();
        if (letter.equals("热")) {
            llm.scrollToPositionWithOffset(0, 0);
        } else {
            int index = A.StringToA(letter) % 65;
            int position = letterPosition[index];
            if (position != 0) llm.scrollToPositionWithOffset(position, 0);
            else letterIndex.nextPosition();
        }
    }
}
