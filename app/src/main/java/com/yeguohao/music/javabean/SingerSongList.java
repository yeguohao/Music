package com.yeguohao.music.javabean;

import com.google.gson.annotations.SerializedName;

public class SingerSongList {

    private int code;
    private Data data;
    private String message;
    private int subcode;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public static class Data {

        private String singer_id;
        private String singer_mid;
        private String singer_name;
        private int total;
        private java.util.List<List> list;

        public String getSinger_id() {
            return singer_id;
        }

        public void setSinger_id(String singer_id) {
            this.singer_id = singer_id;
        }

        public String getSinger_mid() {
            return singer_mid;
        }

        public void setSinger_mid(String singer_mid) {
            this.singer_mid = singer_mid;
        }

        public String getSinger_name() {
            return singer_name;
        }

        public void setSinger_name(String singer_name) {
            this.singer_name = singer_name;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public java.util.List<List> getList() {
            return list;
        }

        public void setList(java.util.List<List> list) {
            this.list = list;
        }

        public static class List {
            /**
             * Flisten_count1 : 191669658
             * Fupload_time : 2017-11-02 17:32:42
             * index : 1
             * isnew : 1
             * listenCount : 0
             * musicData : {"albumdesc":"","albumid":3310789,"albummid":"001L7UIu3GXVtT","albumname":"渡 The Crossing","alertid":0,"belongCD":3,"cdIdx":0,"interval":255,"isonly":0,"label":"0","msgid":23,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":0,"payinfo":0,"payplay":0,"paytrackmouth":0,"paytrackprice":0,"timefree":1},"preview":{"trybegin":0,"tryend":0,"trysize":0},"rate":31,"singer":[{"id":5062,"mid":"002J4UUk29y8BY","name":"薛之谦"}],"size128":4082823,"size320":10206761,"size5_1":0,"sizeape":25080361,"sizeflac":25007375,"sizeogg":5568158,"songid":207323048,"songmid":"001uxKNp3a7Qkv","songname":"像风一样","songorig":"像风一样","songtype":0,"strMediaMid":"001uxKNp3a7Qkv","stream":13,"switch":1,"type":0,"vid":""}
             * playurl :
             * price : 320
             * vid : {"Fmv_id":"1380636","Fstatus":"1","Fvid":"b0025cadjr8"}
             */

            private String Flisten_count1;
            private String Fupload_time;
            private int index;
            private int isnew;
            private int listenCount;
            private MusicData musicData;
            private String playurl;
            private int price;
            private Vid vid;

            public String getFlisten_count1() {
                return Flisten_count1;
            }

            public void setFlisten_count1(String Flisten_count1) {
                this.Flisten_count1 = Flisten_count1;
            }

            public String getFupload_time() {
                return Fupload_time;
            }

            public void setFupload_time(String Fupload_time) {
                this.Fupload_time = Fupload_time;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getIsnew() {
                return isnew;
            }

            public void setIsnew(int isnew) {
                this.isnew = isnew;
            }

            public int getListenCount() {
                return listenCount;
            }

            public void setListenCount(int listenCount) {
                this.listenCount = listenCount;
            }

            public MusicData getMusicData() {
                return musicData;
            }

            public void setMusicData(MusicData musicData) {
                this.musicData = musicData;
            }

            public String getPlayurl() {
                return playurl;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public Vid getVid() {
                return vid;
            }

            public void setVid(Vid vid) {
                this.vid = vid;
            }

            public static class MusicData {
                /**
                 * albumdesc :
                 * albumid : 3310789
                 * albummid : 001L7UIu3GXVtT
                 * albumname : 渡 The Crossing
                 * alertid : 0
                 * belongCD : 3
                 * cdIdx : 0
                 * interval : 255
                 * isonly : 0
                 * label : 0
                 * msgid : 23
                 * pay : {"payalbum":0,"payalbumprice":0,"paydownload":0,"payinfo":0,"payplay":0,"paytrackmouth":0,"paytrackprice":0,"timefree":1}
                 * preview : {"trybegin":0,"tryend":0,"trysize":0}
                 * rate : 31
                 * singer : [{"id":5062,"mid":"002J4UUk29y8BY","name":"薛之谦"}]
                 * size128 : 4082823
                 * size320 : 10206761
                 * size5_1 : 0
                 * sizeape : 25080361
                 * sizeflac : 25007375
                 * sizeogg : 5568158
                 * songid : 207323048
                 * songmid : 001uxKNp3a7Qkv
                 * songname : 像风一样
                 * songorig : 像风一样
                 * songtype : 0
                 * strMediaMid : 001uxKNp3a7Qkv
                 * stream : 13
                 * switch : 1
                 * type : 0
                 * vid :
                 */

                private String albumdesc;
                private int albumid;
                private String albummid;
                private String albumname;
                private int alertid;
                private int belongCD;
                private int cdIdx;
                private int interval;
                private int isonly;
                private String label;
                private int msgid;
                private Pay pay;
                private Preview preview;
                private int rate;
                private int size128;
                private int size320;
                private int size5_1;
                private int sizeape;
                private int sizeflac;
                private int sizeogg;
                private int songid;
                private String songmid;
                private String songname;
                private String songorig;
                private int songtype;
                private String strMediaMid;
                private int stream;
                @SerializedName("switch")
                private int switchX;
                private int type;
                private String vid;
                private java.util.List<Singer> singer;

                public String getAlbumdesc() {
                    return albumdesc;
                }

                public void setAlbumdesc(String albumdesc) {
                    this.albumdesc = albumdesc;
                }

                public int getAlbumid() {
                    return albumid;
                }

                public void setAlbumid(int albumid) {
                    this.albumid = albumid;
                }

                public String getAlbummid() {
                    return albummid;
                }

                public void setAlbummid(String albummid) {
                    this.albummid = albummid;
                }

                public String getAlbumname() {
                    return albumname;
                }

                public void setAlbumname(String albumname) {
                    this.albumname = albumname;
                }

                public int getAlertid() {
                    return alertid;
                }

                public void setAlertid(int alertid) {
                    this.alertid = alertid;
                }

                public int getBelongCD() {
                    return belongCD;
                }

                public void setBelongCD(int belongCD) {
                    this.belongCD = belongCD;
                }

                public int getCdIdx() {
                    return cdIdx;
                }

                public void setCdIdx(int cdIdx) {
                    this.cdIdx = cdIdx;
                }

                public int getInterval() {
                    return interval;
                }

                public void setInterval(int interval) {
                    this.interval = interval;
                }

                public int getIsonly() {
                    return isonly;
                }

                public void setIsonly(int isonly) {
                    this.isonly = isonly;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public int getMsgid() {
                    return msgid;
                }

                public void setMsgid(int msgid) {
                    this.msgid = msgid;
                }

                public Pay getPay() {
                    return pay;
                }

                public void setPay(Pay pay) {
                    this.pay = pay;
                }

                public Preview getPreview() {
                    return preview;
                }

                public void setPreview(Preview preview) {
                    this.preview = preview;
                }

                public int getRate() {
                    return rate;
                }

                public void setRate(int rate) {
                    this.rate = rate;
                }

                public int getSize128() {
                    return size128;
                }

                public void setSize128(int size128) {
                    this.size128 = size128;
                }

                public int getSize320() {
                    return size320;
                }

                public void setSize320(int size320) {
                    this.size320 = size320;
                }

                public int getSize5_1() {
                    return size5_1;
                }

                public void setSize5_1(int size5_1) {
                    this.size5_1 = size5_1;
                }

                public int getSizeape() {
                    return sizeape;
                }

                public void setSizeape(int sizeape) {
                    this.sizeape = sizeape;
                }

                public int getSizeflac() {
                    return sizeflac;
                }

                public void setSizeflac(int sizeflac) {
                    this.sizeflac = sizeflac;
                }

                public int getSizeogg() {
                    return sizeogg;
                }

                public void setSizeogg(int sizeogg) {
                    this.sizeogg = sizeogg;
                }

                public int getSongid() {
                    return songid;
                }

                public void setSongid(int songid) {
                    this.songid = songid;
                }

                public String getSongmid() {
                    return songmid;
                }

                public void setSongmid(String songmid) {
                    this.songmid = songmid;
                }

                public String getSongname() {
                    return songname;
                }

                public void setSongname(String songname) {
                    this.songname = songname;
                }

                public String getSongorig() {
                    return songorig;
                }

                public void setSongorig(String songorig) {
                    this.songorig = songorig;
                }

                public int getSongtype() {
                    return songtype;
                }

                public void setSongtype(int songtype) {
                    this.songtype = songtype;
                }

                public String getStrMediaMid() {
                    return strMediaMid;
                }

                public void setStrMediaMid(String strMediaMid) {
                    this.strMediaMid = strMediaMid;
                }

                public int getStream() {
                    return stream;
                }

                public void setStream(int stream) {
                    this.stream = stream;
                }

                public int getSwitchX() {
                    return switchX;
                }

                public void setSwitchX(int switchX) {
                    this.switchX = switchX;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public java.util.List<Singer> getSinger() {
                    return singer;
                }

                public void setSinger(java.util.List<Singer> singer) {
                    this.singer = singer;
                }

                public static class Pay {
                    /**
                     * payalbum : 0
                     * payalbumprice : 0
                     * paydownload : 0
                     * payinfo : 0
                     * payplay : 0
                     * paytrackmouth : 0
                     * paytrackprice : 0
                     * timefree : 1
                     */

                    private int payalbum;
                    private int payalbumprice;
                    private int paydownload;
                    private int payinfo;
                    private int payplay;
                    private int paytrackmouth;
                    private int paytrackprice;
                    private int timefree;

                    public int getPayalbum() {
                        return payalbum;
                    }

                    public void setPayalbum(int payalbum) {
                        this.payalbum = payalbum;
                    }

                    public int getPayalbumprice() {
                        return payalbumprice;
                    }

                    public void setPayalbumprice(int payalbumprice) {
                        this.payalbumprice = payalbumprice;
                    }

                    public int getPaydownload() {
                        return paydownload;
                    }

                    public void setPaydownload(int paydownload) {
                        this.paydownload = paydownload;
                    }

                    public int getPayinfo() {
                        return payinfo;
                    }

                    public void setPayinfo(int payinfo) {
                        this.payinfo = payinfo;
                    }

                    public int getPayplay() {
                        return payplay;
                    }

                    public void setPayplay(int payplay) {
                        this.payplay = payplay;
                    }

                    public int getPaytrackmouth() {
                        return paytrackmouth;
                    }

                    public void setPaytrackmouth(int paytrackmouth) {
                        this.paytrackmouth = paytrackmouth;
                    }

                    public int getPaytrackprice() {
                        return paytrackprice;
                    }

                    public void setPaytrackprice(int paytrackprice) {
                        this.paytrackprice = paytrackprice;
                    }

                    public int getTimefree() {
                        return timefree;
                    }

                    public void setTimefree(int timefree) {
                        this.timefree = timefree;
                    }
                }

                public static class Preview {
                    /**
                     * trybegin : 0
                     * tryend : 0
                     * trysize : 0
                     */

                    private int trybegin;
                    private int tryend;
                    private int trysize;

                    public int getTrybegin() {
                        return trybegin;
                    }

                    public void setTrybegin(int trybegin) {
                        this.trybegin = trybegin;
                    }

                    public int getTryend() {
                        return tryend;
                    }

                    public void setTryend(int tryend) {
                        this.tryend = tryend;
                    }

                    public int getTrysize() {
                        return trysize;
                    }

                    public void setTrysize(int trysize) {
                        this.trysize = trysize;
                    }
                }

                public static class Singer {
                    /**
                     * id : 5062
                     * mid : 002J4UUk29y8BY
                     * name : 薛之谦
                     */

                    private int id;
                    private String mid;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getMid() {
                        return mid;
                    }

                    public void setMid(String mid) {
                        this.mid = mid;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

            public static class Vid {
                /**
                 * Fmv_id : 1380636
                 * Fstatus : 1
                 * Fvid : b0025cadjr8
                 */

                private String Fmv_id;
                private String Fstatus;
                private String Fvid;

                public String getFmv_id() {
                    return Fmv_id;
                }

                public void setFmv_id(String Fmv_id) {
                    this.Fmv_id = Fmv_id;
                }

                public String getFstatus() {
                    return Fstatus;
                }

                public void setFstatus(String Fstatus) {
                    this.Fstatus = Fstatus;
                }

                public String getFvid() {
                    return Fvid;
                }

                public void setFvid(String Fvid) {
                    this.Fvid = Fvid;
                }
            }
        }
    }
}
