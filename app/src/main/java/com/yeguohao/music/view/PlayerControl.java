package com.yeguohao.music.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yeguohao.music.R;
import com.yeguohao.music.common.MediaPlayerListener;
import com.yeguohao.music.common.MediaPlayerUtil;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yeguohao.music.common.MediaPlayerUtil.AT_ONCE;
import static com.yeguohao.music.common.MediaPlayerUtil.FAVORITE;
import static com.yeguohao.music.common.MediaPlayerUtil.MODE;
import static com.yeguohao.music.common.MediaPlayerUtil.NEXT;
import static com.yeguohao.music.common.MediaPlayerUtil.PAUSE;
import static com.yeguohao.music.common.MediaPlayerUtil.PREV;
import static com.yeguohao.music.common.MediaPlayerUtil.START;

public class PlayerControl extends ConstraintLayout implements MediaPlayerListener {

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

    private boolean isPause;

    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

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
        RxView.clicks(play).subscribe(view -> {
            if (isPause) {
                playerUtil.sent(START);
            } else {
                playerUtil.sent(PAUSE);
            }
        });
        RxView.clicks(next).subscribe(view-> playerUtil.sent(NEXT));
        RxView.clicks(prev).subscribe(view-> playerUtil.sent(PREV));
    }

    private void initLayout(Context context) {
        inflate(context, R.layout.player_control, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playerUtil.on(START | PAUSE | FAVORITE | MODE | AT_ONCE, this);
    }

    @Override
    public void start() {
        isPause = false;
        play.setImageBitmap(pauseBitmap);
    }

    @Override
    public void pause() {
        isPause = true;
        play.setImageBitmap(playBitmap);
    }

    @Override
    public void favorite(boolean isFavorite) {

    }

    @Override
    public void modeChanged(int mode) {

    }
}
