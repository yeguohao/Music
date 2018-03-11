package com.yeguohao.music.song.disposes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseRecyclerAdapter;
import com.yeguohao.music.base.RecyclerDispose;
import com.yeguohao.music.javabean.SongList;

import static com.yeguohao.music.song.activities.SongActivity.TYPE_RANK;

public class SongDispose extends RecyclerDispose<SongList.Song> {

    private int[] res = {R.drawable.first, R.drawable.second, R.drawable.third};

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void refreshUI(BaseRecyclerAdapter.InnerViewHolder holder, int position, SongList.Song dataItem) {
        TextView songName = holder.getTextView(R.id.song_name);
        TextView albumName = holder.getTextView(R.id.album_name);

        songName.setText(dataItem.getSongName());
        albumName.setText(dataItem.getSingerName() + "-" + dataItem.getAlbumName());
        if (type.equals(TYPE_RANK)) {
            ImageView rankImg = holder.getImageView(R.id.song_rank_img);
            TextView rankText = holder.getTextView(R.id.song_rank_txt);
            rankImg.setVisibility(View.VISIBLE);
            rankText.setVisibility(View.VISIBLE);

            if (position < 3) {
                Glide.with(holder.itemView).load(res[position]).into(rankImg);
                rankText.setVisibility(View.INVISIBLE);
            } else {
                rankImg.setVisibility(View.INVISIBLE);
                rankText.setText(position + 1 + "");
            }
        }
    }
}
