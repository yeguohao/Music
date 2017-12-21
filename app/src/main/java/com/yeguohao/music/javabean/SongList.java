package com.yeguohao.music.javabean;

import java.util.List;

public class SongList {

    private String title;
    private String imgUrl;
    private List<Song> songs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public static class Song {
        private String songName;
        private String songMid;
        private String albumName;
        private String singerName;

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSongMid() {
            return songMid;
        }

        public void setSongMid(String songMid) {
            this.songMid = songMid;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }

        public String getSingerName() {
            return singerName;
        }
    }
}
