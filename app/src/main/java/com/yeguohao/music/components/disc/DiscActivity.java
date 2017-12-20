package com.yeguohao.music.components.disc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yeguohao.music.R;

public class DiscActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc);

        if (savedInstanceState == null) {
            Disc disc = Disc.newInstance(getIntent().getStringExtra("disstid"));
            getFragmentManager().beginTransaction().replace(R.id.disc_replace, disc).commit();
        }
    }
}
