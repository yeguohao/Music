package com.yeguohao.music.song.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeguohao.music.R;
import com.yeguohao.music.javabean.SongList;

import static com.yeguohao.music.song.activities.SongActivity.TYPE_RANK;

public class SongAdapter extends BaseQuickAdapter<SongList.Song, BaseViewHolder> {

    private int[] res = {R.drawable.first, R.drawable.second, R.drawable.third};
    private String type;

    public SongAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SongList.Song item) {
        helper.setText(R.id.song_name, item.getSongName())
                .setText(R.id.album_name, item.getSingerName() + "-" + item.getAlbumName());

        if (type.equals(TYPE_RANK)) {
            ImageView rankImg = helper.getView(R.id.song_rank_img);
            TextView rankText = helper.getView(R.id.song_rank_txt);
            rankImg.setVisibility(View.VISIBLE);
            rankText.setVisibility(View.VISIBLE);

            int position = helper.getLayoutPosition();
            if (position < 3) {
                Glide.with(helper.itemView).load(res[position]).into(rankImg);
                rankText.setVisibility(View.INVISIBLE);
            } else {
                rankImg.setVisibility(View.INVISIBLE);
                rankText.setText(position + 1 + "");
            }
        }
    }

}
