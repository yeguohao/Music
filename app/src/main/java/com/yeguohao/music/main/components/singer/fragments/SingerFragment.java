package com.yeguohao.music.main.components.singer.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.A;
import com.yeguohao.music.common.decoration.RecyclerTitleItemDecoration1;
import com.yeguohao.music.javabean.V8;
import com.yeguohao.music.main.components.singer.adapters.SingerAdapter;
import com.yeguohao.music.main.components.singer.apis.SingerApi;
import com.yeguohao.music.views.LetterIndexView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SingerFragment extends BaseFragment implements LetterIndexView.LetterSelectedListener,
        RecyclerTitleItemDecoration1.OnFixedTextChangedListener {

    private static final String TAG = "SingerApi";
    @BindView(R.id.singer_recycler) RecyclerView recycler;
    @BindView(R.id.singer_letter_index) LetterIndexView letterIndex;

    private SingerApi singerApi = RetrofitInstance.Retrofit().create(SingerApi.class);

    private SingerAdapter adapter;
    private int[] letterPosition = new int[26];

    private RecyclerTitleItemDecoration1 itemDecoration1;

    @Override
    protected int layout() {
        return R.layout.fragment_singer;
    }

    @Override
    protected void initView(View root) {
        adapter = new SingerAdapter(R.layout.item_singer);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(false);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);

        itemDecoration1 = new RecyclerTitleItemDecoration1();
        itemDecoration1.setListener(this);
        recycler.addItemDecoration(itemDecoration1);
        letterIndex.setLetterSelectedListener(this);
    }

    @Override
    protected void fetch() {
        List<V8.Data.List> top10 = new ArrayList<>(10);
        singerApi.v8()
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
                    adapter.addData(lists);
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

    public static SingerFragment newInstance() {
        Bundle args = new Bundle();
        SingerFragment fragment = new SingerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLetterSelected(String letter) {
        LinearLayoutManager llm = (LinearLayoutManager) recycler.getLayoutManager();
        int position = letterPosition[getIndexByLetter(letter)];
        Log.d(TAG, "onLetterSelected: " + position);
        if (position != 0) llm.scrollToPositionWithOffset(position, -itemDecoration1.getMarginTop());
        else letterIndex.nextPosition();
    }

    private int getIndexByLetter(String letter) {
        if (letter.contains("热") || letter.contains("9")) {
            return 0;
        } else {
            return A.StringToA(letter) % 65;
        }
    }

    @Override
    public void onChanged(String letter) {
        int index;
        if (letter.contains("热") || letter.contains("9")) {
            index = 0;
        } else {
            index = (A.StringToA(letter) % 65) + 1;
        }
        letterIndex.setSelectedIndex(index);
    }
}
