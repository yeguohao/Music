package com.yeguohao.music.player.fragments;

import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.PlayerImpl;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.player.adapters.PlayerPagerAdapter;
import com.yeguohao.music.views.BlurImageView;
import com.yeguohao.music.views.MyProcess;
import com.yeguohao.music.views.PositionTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yeguohao.music.player.PlayerConstance.LOOP;
import static com.yeguohao.music.player.PlayerConstance.RANDOM;
import static com.yeguohao.music.player.PlayerConstance.SEQUENCE;

public class PlayerFragment extends BaseFragment {

    @BindView(R.id.player_background) BlurImageView background;
    @BindView(R.id.player_song) TextView songText;
    @BindView(R.id.player_singer) TextView singer;
    @BindView(R.id.player_viewpager) ViewPager viewPager;
    @BindView(R.id.player_mode) ImageView modeImageView;
    @BindView(R.id.player_prev) ImageView prev;
    @BindView(R.id.player_play) ImageView play;
    @BindView(R.id.player_next) ImageView next;
    @BindView(R.id.player_favorite) ImageView favoriteImageView;
    @BindView(R.id.player_current_time) PositionTextView currentTime;
    @BindView(R.id.player_total_time) PositionTextView totalTime;
    @BindView(R.id.player_progress) MyProcess progress;

    private MusicController musicController = PlayerInstance.getMusicController();
    private SongStore songStore = PlayerInstance.getSongStore();
    private PlayerImpl.PositionUpdater updater = PlayerInstance.getUpdater();

    private boolean seekUser;

    private SparseIntArray modeMap;
    private PlayerPagerAdapter pagerAdapter;

    private void fillData() {
        modeMap = new SparseIntArray(3);
        modeMap.put(SEQUENCE, R.drawable.sequence);
        modeMap.put(LOOP, R.drawable.loop);
        modeMap.put(RANDOM, R.drawable.random);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsetListener();
    }

    @Override
    protected int layout() {
        return R.layout.fragment_player;
    }

    @Override
    protected void initView(View root) {
        fillData();
        showUIByItem();
        pagerAdapter = new PlayerPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        updater.setPositionCallback(position -> {
            if (seekUser) {
                return;
            }
            getActivity().runOnUiThread(() -> update(position));
        });
        updater.setCompletedCallback(this::completed);
        updater.setBufferedPositionCallback(progress::setBufferedPosition);
        progress.setChangeListener(new MyProcess.OnChangeListener() {

            private long seekPosition;

            @Override
            public void onProgressChanged(MyProcess process, long progress, boolean fromUser) {
                if (fromUser) {
                    seekPosition = progress;
                    currentTime.setPosition(seekPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(MyProcess process) {
                seekUser = true;
            }

            @Override
            public void onStopTrackingTouch(MyProcess process) {
                seekUser = false;
                musicController.seek(seekPosition);
            }

        });
    }

    private void unsetListener() {
        updater.setPositionCallback(null);
        updater.setCompletedCallback(null);
        updater.setBufferedPositionCallback(null);
    }

    // 更新进度
    private void update(long position) {
        // 如果 totalTime 还未设置，则设置它
        if (totalTime.getPosition() == 0) {
            long d = songStore.song(songStore.currentIndex()).getDuration();
            totalTime.setPosition(d);
            progress.setMax(d);
        }
        currentTime.setPosition(position);
        progress.setProgress(position);

        LyricFragment lyricFragment = pagerAdapter.getLyricFragment();
        lyricFragment.progress(position);
    }

    // 播放结束，延时一秒后切换歌曲
    private void completed() {
        getActivity().runOnUiThread(this::switchSong);
    }

    @OnClick({R.id.player_mode, R.id.player_prev, R.id.player_play, R.id.player_next, R.id.player_favorite})
    void controlClicks(View view) {
        MusicItem item = songStore.song(songStore.currentIndex());

        switch (view.getId()) {
            case R.id.player_mode: {
                musicController.switchMode();
                int mode = musicController.getMode();
                modeImageView.setImageResource(modeMap.get(mode));
                break;
            }
            case R.id.player_prev: {
                musicController.prev();
                switchSong();
                break;
            }
            case R.id.player_play: {
                if (item.isPlaying()) {
                    musicController.pause();
                } else {
                    musicController.play();
                }
                play.setSelected(item.isPlaying());
                AlbumCoverFragment coverFragment = pagerAdapter.getAlbumCoverFragment();
                coverFragment.playBack(item.isPlaying());
                break;
            }
            case R.id.player_next: {
                musicController.next();
                switchSong();
                break;
            }
            case R.id.player_favorite: {
                musicController.favorite();
                favoriteImageView.setSelected(item.isFavorite());
                break;
            }
        }
    }

    private void switchSong() {
        showUIByItem();
        AlbumCoverFragment albumCoverFragment = pagerAdapter.getAlbumCoverFragment();
        LyricFragment lyricFragment = pagerAdapter.getLyricFragment();
        albumCoverFragment.switchSong();
        lyricFragment.switchSong();
    }

    private void setBackgroundImage(String url) {
        RequestOptions options = RequestOptions.placeholderOf(R.color.colorPrimary)
                .error(R.color.textColorDark).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).asBitmap().load(url).apply(options).into(background);
    }

    private void showUIByItem() {
        MusicItem item = songStore.song(songStore.currentIndex());
        MusicItem.Description description = item.getDescription();

        // 显示歌曲信息
        songText.setText(description.getSongName());
        singer.setText(description.getSingerName());

        long duration = Math.max(item.getDuration(), 0);
        // 显示歌曲长度和进度
        totalTime.setPosition(duration);
        currentTime.setPosition(item.getCurrentPosition());

        // 设置进度条属性
        progress.setMax(duration);
        progress.setProgress(item.getCurrentPosition());
        progress.setBufferedPosition(item.getBufferedPosition());

        // 是否收藏
        favoriteImageView.setSelected(item.isFavorite());

        // 显示模式
        int mode = musicController.getMode();
        modeImageView.setImageResource(modeMap.get(mode));

        play.setSelected(item.isPlaying());

        // 背景
        setBackgroundImage(description.getIconUri());
    }

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

}
