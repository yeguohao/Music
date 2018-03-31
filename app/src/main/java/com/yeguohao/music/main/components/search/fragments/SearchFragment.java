package com.yeguohao.music.main.components.search.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.javabean.HotKey;
import com.yeguohao.music.javabean.SearchInfo;
import com.yeguohao.music.main.components.search.adapters.SearchAdapter;
import com.yeguohao.music.main.components.search.apis.SearchApi;
import com.yeguohao.music.views.FlowLayout;
import com.yeguohao.music.views.SearchHistory;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "SearchApi";
    @BindView(R.id.search_hot_group)
    FlowLayout hotGroup;

    @BindView(R.id.search_recycler)
    RecyclerView recycler;

    @BindView(R.id.search_edit)
    EditText editText;

    @BindView(R.id.search_close)
    ImageView close;

    @BindView(R.id.search_history)
    SearchHistory history;

    private SearchApi searchApi = RetrofitInstance.Retrofit().create(SearchApi.class);
    private SearchAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View root) {
        close.setOnClickListener(view -> editText.setText(""));
        adapter = new SearchAdapter(R.layout.item_search);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(false);

        RxTextView.textChanges(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(key -> {
                    if (TextUtils.isEmpty(key)) {
                        searchIconGone();
                        searchRecyclerInvisible();
                    } else {
                        SearchHistory.SearchRecord.getSearchRecord().push(key.toString());
                        String encodeKey = URLEncoder.encode(key.toString(), "utf-8");
                        searchIconVisible();
                        searchRecyclerVisible();
                        startSearch(encodeKey);
                    }
                });

        history.setListener(searchKey -> editText.setText(searchKey));
    }

    @Override
    protected void fetch() {
        searchApi.getHotKey()
                .filter(hotKey -> hotKey.getCode() == 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::hotKeyResult);
    }

    private void searchResult(SearchInfo searchInfo) {
        adapter.addData(searchInfo.getData().getSong().getList());
    }

    private void hotKeyResult(HotKey hotKey) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<HotKey.DataBean.HotkeyBean> hotkeyBeans = hotKey.getData().getHotkey();
        for (HotKey.DataBean.HotkeyBean bean : hotkeyBeans) {
            TextView child = (TextView) inflater.inflate(R.layout.item_hot_search, hotGroup, false);
            child.setText(bean.getK());
            child.setOnClickListener(this);

            hotGroup.addView(child);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        history.pause();
    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        String key = textView.getText().toString();
        editText.setText(key);
    }

    private void startSearch(String encodeKey) {
        searchApi.search(encodeKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchResult, throwable -> Log.e(TAG, "searchOpen: " + throwable));
    }

    private void searchRecyclerVisible() {
        recycler.setVisibility(View.VISIBLE);
    }


    private void searchRecyclerInvisible() {
        recycler.setVisibility(View.INVISIBLE);
    }

    private void searchIconGone() {
        close.setVisibility(View.GONE);
    }

    private void searchIconVisible() {
        close.setVisibility(View.VISIBLE);
    }
}
