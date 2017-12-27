package com.yeguohao.music.components.player;

public class Song {
        private String songName;
        private String singerName;
        private String songMid;
        private String albumMid;

        public void setAlbumMid(String albumMid) {
            this.albumMid = albumMid;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }

        public void setSongMid(String songMid) {
            this.songMid = songMid;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSongName() {
            return songName;
        }

        public String getAlbumMid() {
            return albumMid;
        }

        public String getSingerName() {
            return singerName;
        }

        public String getSongMid() {
            return songMid;
        }
    }