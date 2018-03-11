package com.yeguohao.music.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yeguohao.music.flavour.activities.FlavourActivity;
import com.yeguohao.music.main.adapters.MainPagerAdapter;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.player.activities.PlayerActivity;
import com.yeguohao.music.views.FixedTextTabLayout;
import com.yeguohao.music.views.MiniPlayer;

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

        MediaPlayerUtil.getPlayerUtil().restoreState();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_flavour, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_flavour) {
            startActivity(new Intent(this, FlavourActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerUtil.getPlayerUtil().storeState();
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
    protected void onResume() {
        super.onResume();
        miniPlayer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        miniPlayer.pause();
    }

}
