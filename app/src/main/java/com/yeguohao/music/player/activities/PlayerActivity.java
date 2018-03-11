package com.yeguohao.music.player.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yeguohao.music.R;
import com.yeguohao.music.player.fragments.PlayerFragments;

public class PlayerActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.player_replace, PlayerFragments.newInstance()).commit();
        }
    }
}
