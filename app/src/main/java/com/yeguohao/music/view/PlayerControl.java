package com.yeguohao.music.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yeguohao.music.R;
import com.yeguohao.music.common.SongInfo;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerControl extends ConstraintLayout {

    @BindBitmap(R.drawable.player_play)
    Bitmap playBitmap;

    @BindBitmap(R.drawable.player_pause)
    Bitmap pauseBitmap;

    @BindBitmap(R.drawable.player_favorite)
    Bitmap favoriteBitamp;

    @BindBitmap(R.drawable.player_favorite_border)
    Bitmap favoriteBorderBitamp;

    @BindView(R.id.player_sequence)
    ImageView sequence;

    @BindView(R.id.player_prev)
    ImageView prev;

    @BindView(R.id.player_play)
    ImageView play;

    @BindView(R.id.player_next)
    ImageView next;

    @BindView(R.id.player_favorite)
    ImageView favorite;

    private SongInfo songInfo = SongInfo.newInstance();

    public PlayerControl(Context context) {
        this(context, null);
    }

    public PlayerControl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ui();
        play.setOnClickListener(view -> songInfo.setPause(!songInfo.isPause()));
        songInfo.setPauseListener(isPause -> ui());
    }

    private void ui() {
        if (songInfo.isPause()) {
            play.setImageBitmap(playBitmap);
        } else {
            play.setImageBitmap(pauseBitmap);
        }

    }

    private void initLayout(Context context) {
        inflate(context, R.layout.player_control, this);
    }
}
