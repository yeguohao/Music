package com.yeguohao.music.main.components.playqueue.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.main.components.playqueue.adapters.PlaySongQueueAdapter;
import com.yeguohao.music.views.ScrollDownView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yeguohao.music.common.Util.screenHeight;
import static com.yeguohao.music.common.Util.setDialogWindowAnimations;
import static com.yeguohao.music.player.PlayerConstance.LOOP;
import static com.yeguohao.music.player.PlayerConstance.RANDOM;
import static com.yeguohao.music.player.PlayerConstance.SEQUENCE;

public class PlaySongQueue extends DialogFragment {

    @BindView(R.id.queue_recycler) RecyclerView recycler;
    @BindView(R.id.play_mode) ImageView playMode;
    @BindView(R.id.play_mode_text) TextView playModeText;

    private SongStore songStore = PlayerInstance.getSongStore();
    private MusicController musicController = PlayerInstance.getMusicController();

    private SparseIntArray modeMapResId = new SparseIntArray();
    private SparseArray<String> modeMapName = new SparseArray<>();
    private PlaySongQueueAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        setDialogWindowAnimations(dialog, R.style.PlaySongQueueDialog);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_play_queue, container, false);
        ButterKnife.bind(this, root);
        ((ScrollDownView)root).setDialog(getDialog());
        fillMap();
        initView();
        setup();
        return root;
    }

    private void fillMap() {
        modeMapResId.append(SEQUENCE, R.drawable.sequence);
        modeMapResId.append(LOOP, R.drawable.loop);
        modeMapResId.append(RANDOM, R.drawable.random);

        modeMapName.append(SEQUENCE, "顺序播放");
        modeMapName.append(LOOP, "循环播放");
        modeMapName.append(RANDOM, "随机播放");
    }

    private void initView() {
        adapter = new PlaySongQueueAdapter(R.layout.item_play_song_queue);
        recycler.setAdapter(adapter);
//        RxRecyclerView.scrollEvents(recycler).subscribe(this::checkDy);

        List<MusicItem> source = songStore.songs();
        List<MusicItem> dest = new ArrayList<>(source.size());
        dest.addAll(source);
        adapter.replaceData(dest);

        int mode = musicController.getMode();
        playMode.setImageResource(modeMapResId.get(mode));
        playModeText.setText(modeMapName.get(mode));
    }

    private void checkDy(RecyclerViewScrollEvent event) {
        boolean down = event.dy() < 0;
        LinearLayoutManager manager = (LinearLayoutManager) recycler.getLayoutManager();

        View child = manager.getChildAt(0);
        int position = manager.getPosition(child);
        boolean first = position == 0;

        if (first && down) {
            dismiss();
        }
    }

    private void setup() {
        int h = screenHeight(getActivity());
        Dialog dialog = getDialog();
        dialog.setOnShowListener(inflater -> {
            Window window = dialog.getWindow();
            assert window != null;
            window.setGravity(Gravity.BOTTOM);

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = h / 6 * 4;   // dialog 占屏幕六分之四
            window.setAttributes(params);

            window.setBackgroundDrawable(null);
            window.getDecorView().setPadding(0, 0, 0, 0);
        });
    }

    @OnClick(R.id.clear_queue)
    void clearQueue() {
        adapter.getData().clear();
        dismiss();
    }

    public void completed() {
        adapter.notifyDataSetChanged();
    }

    public static PlaySongQueue newInstance() {
        return new PlaySongQueue();
    }

}
