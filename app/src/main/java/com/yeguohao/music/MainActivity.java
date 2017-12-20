package com.yeguohao.music;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yeguohao.music.components.player.Player;
import com.yeguohao.music.components.player.PlayerActivity;
import com.yeguohao.music.view.FixedTextTabLayout;
import com.yeguohao.music.view.MiniPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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
}
