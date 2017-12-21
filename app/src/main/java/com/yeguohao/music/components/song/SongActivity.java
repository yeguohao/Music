package com.yeguohao.music.components.song;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yeguohao.music.R;

public class SongActivity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String TOP_ID = "top_id";
    public static final String SINGER_MID = "singer_mid";

    public static final String TYPE_SINGER = "type_singer";
    public static final String TYPE_RANK = "type_rank";

    public static void start(Activity activity, String type, String topId, String singerMid) {
        Intent intent = new Intent(activity, SongActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(TOP_ID, topId);
        intent.putExtra(SINGER_MID, singerMid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String type = intent.getStringExtra(TYPE);
            String topId = intent.getStringExtra(TOP_ID);
            String singerMid = intent.getStringExtra(SINGER_MID);
            getFragmentManager().beginTransaction()
                    .replace(R.id.song_replace, Song.newInstance(type, topId, singerMid)).commit();
        }
    }
}
