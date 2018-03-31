package com.yeguohao.music.common.player.impl;

import static com.yeguohao.music.player.PlayerConstance.ALBUM_IMG_URL;
import static com.yeguohao.music.player.PlayerConstance.MUSIC_DOWN_BASE_URL;

public class MusicItem {

    private long currentPosition;
    private long duration;
    private long bufferedPosition;
    private boolean favorite;
    private boolean playing;
    private Description description;

    public MusicItem() {
        description = new Description();
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getBufferedPosition() {
        return bufferedPosition;
    }

    public void setBufferedPosition(long bufferedPosition) {
        this.bufferedPosition = bufferedPosition;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public class Description {

        private String iconUri;
        private String streamUri;
        private String songMid;
        private String albumMid;
        private String songName;
        private String singerName;

        public String getIconUri() {
            return iconUri;
        }

        public void setIconUri(String iconUri) {
            this.iconUri = iconUri;
        }

        public String getStreamUri() {
            return streamUri;
        }

        public void setStreamUri(String streamUri) {
            this.streamUri = streamUri;
        }

        public String getSongMid() {
            return songMid;
        }

        public void setSongMid(String songMid) {
            this.songMid = songMid;
            String url = String.format(MUSIC_DOWN_BASE_URL, songMid);
            setStreamUri(url);
        }

        public String getAlbumMid() {
            return albumMid;
        }

        public void setAlbumMid(String albumMid) {
            this.albumMid = albumMid;
            String url = String.format(ALBUM_IMG_URL, albumMid);
            setIconUri(url);
        }

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSingerName() {
            return singerName;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }
    }

}
