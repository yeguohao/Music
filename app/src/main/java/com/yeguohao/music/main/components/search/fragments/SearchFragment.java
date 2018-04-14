package com.yeguohao.music.main.components.search.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.javabean.HotKey;
import com.yeguohao.music.main.components.search.apis.SearchApi;
import com.yeguohao.music.player.activities.PlayerActivity;
import com.yeguohao.music.views.FlowLayout;
import com.yeguohao.music.views.SearchHistory;
import com.yeguohao.music.views.SearchView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.search_hot_group) FlowLayout hotGroup;
    @BindView(R.id.search_history) SearchHistory history;
    @BindView(R.id.search) SearchView searchView;

    private SearchApi searchApi = RetrofitInstance.Retrofit().create(SearchApi.class);

    @Override
    protected int layout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View root) {
        searchView.setListener(item -> {
            SongStore songStore = PlayerInstance.getSongStore();
            MusicController musicController = PlayerInstance.getMusicController();
            musicController.add(item);

            int index = songStore.songs().indexOf(item);
            musicController.switchSong(index);
            PlayerActivity.startActivity(getActivity());
        });
        history.setListener(searchKey -> searchView.setText(searchKey));
    }

    @Override
    protected void fetch() {
        searchApi.getHotKey()
                .filter(this::isOk)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::hotKeyResult, throwable -> {});
    }

    private boolean isOk(HotKey hotKey) {
        return hotKey.getCode() == 0;
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

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        String key = textView.getText().toString();
        searchView.setText(key);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

}
