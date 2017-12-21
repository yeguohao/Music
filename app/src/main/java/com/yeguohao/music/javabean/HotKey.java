package com.yeguohao.music.javabean;

import java.util.List;

public class HotKey {

    /**
     * code : 0
     * data : {"hotkey":[{"k":"无条件 ","n":85228},{"k":"双世宠妃 ","n":81282},{"k":"从前慢 ","n":78055},{"k":"王俊凯 ","n":70432},{"k":"李宇春 ","n":65388},{"k":"林忆莲 ","n":60861},{"k":"悟空传 ","n":59666},{"k":"淘汰 ","n":43617},{"k":"TIME ","n":39694},{"k":"紫 ","n":26260},{"k":"九张机 ","n":25309},{"k":"非酋 薛明媛/朱贺 ","n":18997},{"k":"四年半 ","n":16146},{"k":"远走高飞 ","n":15043},{"k":"我的前半生 ","n":14012},{"k":"风一样的我 ","n":13715},{"k":"WHAT ARE WORDS ","n":13604},{"k":"醉玲珑 ","n":12607},{"k":"法克油 7妹 ","n":12130},{"k":"谢谢侬 陈奕迅 ","n":10858},{"k":"流着泪说分手 ","n":10856},{"k":"雀跃 ","n":10775},{"k":"舍得 ","n":9980},{"k":"一生所爱 卢冠廷 ","n":9187},{"k":"中国好声音 第六季 ","n":8858},{"k":"DJ舞曲(华语)系列5 DJ ","n":8741},{"k":"小青龙 ","n":8719},{"k":"我的小可爱 ","n":8625},{"k":"叶炫清 ","n":8582},{"k":"沧海一声笑 ","n":8541}],"special_key":"梦想的声音第二季","special_url":"https://y.qq.com/m/act/voiceofdreams2/v3/index.html"}
     * subcode : 0
     */

    private int code;
    private DataBean data;
    private int subcode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public static class DataBean {
        /**
         * hotkey : [{"k":"无条件 ","n":85228},{"k":"双世宠妃 ","n":81282},{"k":"从前慢 ","n":78055},{"k":"王俊凯 ","n":70432},{"k":"李宇春 ","n":65388},{"k":"林忆莲 ","n":60861},{"k":"悟空传 ","n":59666},{"k":"淘汰 ","n":43617},{"k":"TIME ","n":39694},{"k":"紫 ","n":26260},{"k":"九张机 ","n":25309},{"k":"非酋 薛明媛/朱贺 ","n":18997},{"k":"四年半 ","n":16146},{"k":"远走高飞 ","n":15043},{"k":"我的前半生 ","n":14012},{"k":"风一样的我 ","n":13715},{"k":"WHAT ARE WORDS ","n":13604},{"k":"醉玲珑 ","n":12607},{"k":"法克油 7妹 ","n":12130},{"k":"谢谢侬 陈奕迅 ","n":10858},{"k":"流着泪说分手 ","n":10856},{"k":"雀跃 ","n":10775},{"k":"舍得 ","n":9980},{"k":"一生所爱 卢冠廷 ","n":9187},{"k":"中国好声音 第六季 ","n":8858},{"k":"DJ舞曲(华语)系列5 DJ ","n":8741},{"k":"小青龙 ","n":8719},{"k":"我的小可爱 ","n":8625},{"k":"叶炫清 ","n":8582},{"k":"沧海一声笑 ","n":8541}]
         * special_key : 梦想的声音第二季
         * special_url : https://y.qq.com/m/act/voiceofdreams2/v3/index.html
         */

        private String special_key;
        private String special_url;
        private List<HotkeyBean> hotkey;

        public String getSpecial_key() {
            return special_key;
        }

        public void setSpecial_key(String special_key) {
            this.special_key = special_key;
        }

        public String getSpecial_url() {
            return special_url;
        }

        public void setSpecial_url(String special_url) {
            this.special_url = special_url;
        }

        public List<HotkeyBean> getHotkey() {
            return hotkey;
        }

        public void setHotkey(List<HotkeyBean> hotkey) {
            this.hotkey = hotkey;
        }

        public static class HotkeyBean {
            /**
             * k : 无条件
             * n : 85228
             */

            private String k;
            private int n;

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }

            public int getN() {
                return n;
            }

            public void setN(int n) {
                this.n = n;
            }
        }
    }
}
