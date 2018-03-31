package com.yeguohao.music.disc.activities;

import android.os.Bundle;

import com.yeguohao.music.R;
import com.yeguohao.music.base.NotStatusBarActivity;
import com.yeguohao.music.disc.fragments.DiscFragment;

public class DiscActivity extends NotStatusBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc);

        if (savedInstanceState == null) {
            DiscFragment disc = DiscFragment.newInstance(getIntent().getStringExtra("disstid"));
            getFragmentManager().beginTransaction().replace(R.id.disc_replace, disc).commit();
        }
    }

}
