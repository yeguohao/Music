package com.yeguohao.music.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.javabean.SearchInfo;
import com.yeguohao.music.main.components.search.adapters.SearchAdapter;
import com.yeguohao.music.main.components.search.apis.SearchApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchView extends FrameLayout {

    @BindView(R.id.search_recycler) RecyclerView recycler;
    @BindView(R.id.search_edit_wrap) SearchEditText searchEditText;

    private SearchApi searchApi = RetrofitInstance.Retrofit().create(SearchApi.class);
    private SearchAdapter adapter;

    public SearchView(@NonNull Context context) {
        this(context, null);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.search, this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        adapter = new SearchAdapter(R.layout.item_search);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        searchEditText.setListener(new SearchEditText.OnSearchListener() {
            @Override
            public void onStart(String key) {
                startSearch(key);
            }

            @Override
            public void onCancel() {
                recycler.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void setListener(SearchAdapter.OnSearchItemSelectedListener listener) {
        adapter.setListener(listener);
    }

    public void setText(String searchKey) {
        searchEditText.setText(searchKey);
    }

    private void startSearch(String encodeKey) {
        searchApi.search(encodeKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchResult, throwable -> {});
    }

    private void searchResult(SearchInfo searchInfo) {
        recycler.setVisibility(View.VISIBLE);
        List<SearchInfo.DataBean.SongBean.ListBean> list = searchInfo.getData().getSong().getList();
        if (list.isEmpty()) {
            adapter.setEmptyView(R.layout.search_empty);
        } else {
            adapter.replaceData(list);
        }
    }

}
