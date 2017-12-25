package com.yeguohao.music;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yeguohao.music.components.player.PlayerActivity;
import com.yeguohao.music.view.FixedTextTabLayout;
import com.yeguohao.music.view.MiniPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_tab)
    FixedTextTabLayout tab;

    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    @BindView(R.id.mini_player)
    MiniPlayer miniPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MainPagerAdapter(getFragmentManager()));
        tab.setupWithViewPager(viewPager);

        miniPlayer.setMiniPlayerListener(new MiniPlayer.MiniPlayerListener() {
            @Override
            public void onPlay() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onOpenSongList() {

            }

            @Override
            public void onOpenPlayer() {
                PlayerActivity.startActivity(MainActivity.this);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
