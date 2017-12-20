package com.yeguohao.music.javabean;

public class V8 {

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

        private int per_page;
        private int total;
        private int total_page;
        private java.util.List<List> list;

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public java.util.List<List> getList() {
            return list;
        }

        public void setList(java.util.List<List> list) {
            this.list = list;
        }

        public static class List {
            /**
             * Farea : 1
             * Fattribute_3 : 3
             * Fattribute_4 : 0
             * Fgenre : 0
             * Findex : X
             * Fother_name : Joker
             * Fsinger_id : 5062
             * Fsinger_mid : 002J4UUk29y8BY
             * Fsinger_name : 薛之谦
             * Fsinger_tag : 541,555
             * Fsort : 1
             * Ftrend : 0
             * Ftype : 0
             * voc : 0
             */

            private String Farea;
            private String Fattribute_3;
            private String Fattribute_4;
            private String Fgenre;
            private String Findex;
            private String Fother_name;
            private String Fsinger_id;
            private String Fsinger_mid;
            private String Fsinger_name;
            private String Fsinger_tag;
            private String Fsort;
            private String Ftrend;
            private String Ftype;
            private String voc;

            public String getFarea() {
                return Farea;
            }

            public void setFarea(String Farea) {
                this.Farea = Farea;
            }

            public String getFattribute_3() {
                return Fattribute_3;
            }

            public void setFattribute_3(String Fattribute_3) {
                this.Fattribute_3 = Fattribute_3;
            }

            public String getFattribute_4() {
                return Fattribute_4;
            }

            public void setFattribute_4(String Fattribute_4) {
                this.Fattribute_4 = Fattribute_4;
            }

            public String getFgenre() {
                return Fgenre;
            }

            public void setFgenre(String Fgenre) {
                this.Fgenre = Fgenre;
            }

            public String getFindex() {
                return Findex;
            }

            public void setFindex(String Findex) {
                this.Findex = Findex;
            }

            public String getFother_name() {
                return Fother_name;
            }

            public void setFother_name(String Fother_name) {
                this.Fother_name = Fother_name;
            }

            public String getFsinger_id() {
                return Fsinger_id;
            }

            public void setFsinger_id(String Fsinger_id) {
                this.Fsinger_id = Fsinger_id;
            }

            public String getFsinger_mid() {
                return Fsinger_mid;
            }

            public void setFsinger_mid(String Fsinger_mid) {
                this.Fsinger_mid = Fsinger_mid;
            }

            public String getFsinger_name() {
                return Fsinger_name;
            }

            public void setFsinger_name(String Fsinger_name) {
                this.Fsinger_name = Fsinger_name;
            }

            public String getFsinger_tag() {
                return Fsinger_tag;
            }

            public void setFsinger_tag(String Fsinger_tag) {
                this.Fsinger_tag = Fsinger_tag;
            }

            public String getFsort() {
                return Fsort;
            }

            public void setFsort(String Fsort) {
                this.Fsort = Fsort;
            }

            public String getFtrend() {
                return Ftrend;
            }

            public void setFtrend(String Ftrend) {
                this.Ftrend = Ftrend;
            }

            public String getFtype() {
                return Ftype;
            }

            public void setFtype(String Ftype) {
                this.Ftype = Ftype;
            }

            public String getVoc() {
                return voc;
            }

            public void setVoc(String voc) {
                this.voc = voc;
            }
        }
    }
}
