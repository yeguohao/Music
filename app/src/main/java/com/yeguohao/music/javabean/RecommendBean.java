package com.yeguohao.music.javabean;

import java.util.List;

public class RecommendBean {


    /**
     * code : 0
     * data : {"slider":[{"linkUrl":"https://y.qq.com/m/act/mixnine/v3/index.html?ADTAG=jiaodiantu","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000000FSJoZ2c4VE9.jpg","id":13404},{"linkUrl":"https://y.qq.com/m/digitalbum/gold/index.html?_video=true&id=3766873&g_f=shoujijiaodian","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000000B8wHB1Si6Iz.jpg","id":13398},{"linkUrl":"https://y.qq.com/m/act/voiceofdreams2/v3/index.html?ADTAG=jiaodiantu","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000004LxX0l27acSB.jpg","id":13385},{"linkUrl":"https://y.qq.com/m/act/BattleofTeana2/v3/index.html?ADTAG=jiaodiantu","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000002n6Zxs2rVEHO.jpg","id":13408},{"linkUrl":"http://y.qq.com/w/album.html?albummid=002hxRxn2M4EQX","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000004FPDdI03PEjL.jpg","id":13442}],"radioList":[{"picUrl":"http://y.gtimg.cn/music/photo/radio/track_radio_199_13_1.jpg","Ftitle":"热歌","radioid":199}],"songList":[{"songListDesc":"催泪大杀器！盘点演唱会经典万人大合唱","picUrl":"http://p.qpic.cn/music_cover/1Fr9IFMhWDPeUzWKVEjn3QTL2eX2QziaJmaL0ZAmsvtW71ic9IDUoYzg/300?n=1","id":"2646688496","accessnum":7443009,"songListAuthor":"Harry","pic_mid":"00333So02drvak","album_pic_mid":"004aOQhn3PPOpK"},{"songListDesc":"纳尼？这些华语歌手竟然会唱日语歌！","picUrl":"http://p.qpic.cn/music_cover/z8wAFqicQ1qhImeiajkrgiaR4hYM3pzsUULFnicXshFXdw9uGkm261Ex9g/300?n=1","id":"1144416825","accessnum":609049,"songListAuthor":"风吹草地","pic_mid":"0013j8zs1jRnLQ","album_pic_mid":"001iJq1y1Uq3zV"},{"songListDesc":"精选内地十大民谣歌手代表作","picUrl":"http://p.qpic.cn/music_cover/hVUsfUFG2DV466URqw7PT7X66OknPIhic2mKDgicawN4qThIR7yhYY1w/300?n=1","id":"2043041547","accessnum":830709,"songListAuthor":"１'s   ヽ","pic_mid":"004bFmjW2PXSqF","album_pic_mid":"0032YJyg2yF6Dd"},{"songListDesc":"2016Billboard嘻哈榜精选","picUrl":"http://p.qpic.cn/music_cover/tkduvk4dwqBxwzZhsNe0nwkwyiaLHVkxtla7REsX0yNkhibOH3Bdb2og/300?n=1","id":"2040362185","accessnum":1154812,"songListAuthor":"　綠茶椰菜花","pic_mid":"0032Ubid2ses0e","album_pic_mid":"001iJq1y1Uq3zV"},{"songListDesc":"浮光掠影：ACG纯音乐赏析合辑","picUrl":"http://p.qpic.cn/music_cover/XMPAjfs5uwGZdWII3osvAvCRyNWx8Pqy5Yice41OCZlBhLtk0p0icNvg/300?n=1","id":"1723063372","accessnum":885237,"songListAuthor":"肥喵","pic_mid":"000xFtbN1l8ye8","album_pic_mid":"002egQPg3DWcCS"},{"songListDesc":"trip-hop单曲大推荐","picUrl":"http://y.gtimg.cn/music/photo_new/T005R600x600M000002CJKAY1LKpcz.jpg?n=1","id":"3482605622","accessnum":356737,"songListAuthor":"哑忍","pic_mid":"000xFtbN1l8ye8","album_pic_mid":"004aOQhn3PPOpK"}]}
     */

    private int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<Slider> slider;
        private List<RadioList> radioList;
        private List<SongList> songList;

        public List<Slider> getSlider() {
            return slider;
        }

        public void setSlider(List<Slider> slider) {
            this.slider = slider;
        }

        public List<RadioList> getRadioList() {
            return radioList;
        }

        public void setRadioList(List<RadioList> radioList) {
            this.radioList = radioList;
        }

        public List<SongList> getSongList() {
            return songList;
        }

        public void setSongList(List<SongList> songList) {
            this.songList = songList;
        }

        public static class Slider {
            /**
             * linkUrl : https://y.qq.com/m/act/mixnine/v3/index.html?ADTAG=jiaodiantu
             * picUrl : http://y.gtimg.cn/music/photo_new/T003R720x288M000000FSJoZ2c4VE9.jpg
             * id : 13404
             */

            private String linkUrl;
            private String picUrl;
            private int id;

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class RadioList {
            /**
             * picUrl : http://y.gtimg.cn/music/photo/radio/track_radio_199_13_1.jpg
             * Ftitle : 热歌
             * radioid : 199
             */

            private String picUrl;
            private String Ftitle;
            private int radioid;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getFtitle() {
                return Ftitle;
            }

            public void setFtitle(String Ftitle) {
                this.Ftitle = Ftitle;
            }

            public int getRadioid() {
                return radioid;
            }

            public void setRadioid(int radioid) {
                this.radioid = radioid;
            }
        }

        public static class SongList {
            /**
             * songListDesc : 催泪大杀器！盘点演唱会经典万人大合唱
             * picUrl : http://p.qpic.cn/music_cover/1Fr9IFMhWDPeUzWKVEjn3QTL2eX2QziaJmaL0ZAmsvtW71ic9IDUoYzg/300?n=1
             * id : 2646688496
             * accessnum : 7443009
             * songListAuthor : Harry
             * pic_mid : 00333So02drvak
             * album_pic_mid : 004aOQhn3PPOpK
             */

            private String songListDesc;
            private String picUrl;
            private String id;
            private int accessnum;
            private String songListAuthor;
            private String pic_mid;
            private String album_pic_mid;

            public String getSongListDesc() {
                return songListDesc;
            }

            public void setSongListDesc(String songListDesc) {
                this.songListDesc = songListDesc;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getAccessnum() {
                return accessnum;
            }

            public void setAccessnum(int accessnum) {
                this.accessnum = accessnum;
            }

            public String getSongListAuthor() {
                return songListAuthor;
            }

            public void setSongListAuthor(String songListAuthor) {
                this.songListAuthor = songListAuthor;
            }

            public String getPic_mid() {
                return pic_mid;
            }

            public void setPic_mid(String pic_mid) {
                this.pic_mid = pic_mid;
            }

            public String getAlbum_pic_mid() {
                return album_pic_mid;
            }

            public void setAlbum_pic_mid(String album_pic_mid) {
                this.album_pic_mid = album_pic_mid;
            }
        }
    }
}
