package com.yeguohao.music.javabean;

public class Lyric {

    /**
     * retcode : 0
     * code : 0
     * subcode : 0
     * lyric : W3RpOua8lOWRmF0KW2FyOuiWm+S5i+iwpl0KW2FsOue7heWjq10KW2J5Ol0KW29mZnNldDowXQpbMDA6MDAuNTZd5ryU5ZGYIC0g6Jab5LmL6LCmClswMDowMi40Ml3or43vvJrolpvkuYvosKYKWzAwOjAzLjk5Xeabsu+8muiWm+S5i+iwpgpbMDA6MDUuMzhd57yW5puy77ya6YOR5LyfL+W8oOWuneWuhwpbMDA6MDcuNDVd5Yi25L2c5Lq677ya6LW16Iux5L+KClswMDowOS4wOV3lkIjlo7DvvJrotbXoi7Hkv4oKWzAwOjEwLjQ4XeW9lemfs+W4iO+8mueOi+aZk+a1twpbMDA6MTEuNzRdClswMDoxMi4zNF3mt7fpn7PluIjvvJrpso3plJAKWzAwOjEzLjg4XeavjeW4puWkhOeQhuW3peeoi+W4iO+8mumyjemUkApbMDA6MTYuMTBdClswMDoyMS4yNV3nroDljZXngrkKWzAwOjIyLjIxXQpbMDA6MjUuMTZd6K+06K+d55qE5pa55byP566A5Y2V54K5ClswMDoyOC4yN10KWzAwOjMwLjIyXemAkui/m+eahOaDhee7quivt+ecgeeVpQpbMDA6MzMuNTdd5L2g5Y+I5LiN5piv5Liq5ryU5ZGYClswMDozNi4yN13liKvorr7orqHpgqPkupvmg4XoioIKWzAwOjM5LjA4XQpbMDA6NDIuMjBd5rKh5oSP6KeBClswMDo0My43NF0KWzAwOjQ2LjE3XeaIkeWPquaDs+eci+eci+S9oOaAjuS5iOWchgpbMDA6NDkuNDddClswMDo1MS43NF3kvaDpmr7ov4fnmoTlpKrooajpnaIKWzAwOjU0LjYwXeWDj+ayoeWkqei1i+eahOa8lOWRmApbMDA6NTcuMjFd6KeC5LyX5LiA55y86IO955yL6KeBClswMDo1OS42OF0KWzAxOjAyLjQ2XeivpemFjeWQiOS9oOa8lOWHuueahOaIkea8lOinhuiAjOS4jeingQpbMDE6MDcuMDJdClswMTowNy41Nl3lnKjpgLzkuIDkuKrmnIDniLHkvaDnmoTkurrljbPlhbTooajmvJQKWzAxOjEyLjE5XQpbMDE6MTIuODZd5LuA5LmI5pe25YCZ5oiR5Lus5byA5aeL5pS26LW35LqG5bqV57q/ClswMToxNy4zOF0KWzAxOjE4LjAzXemhuuW6lOaXtuS7o+eahOaUueWPmOeci+mCo+S6m+aLmeWKo+eahOihqOa8lApbMDE6MjMuNDBd5Y+v5L2g5pu+57uP6YKj5LmI54ix5oiR5bmy5Zib5ryU5Ye657uG6IqCClswMToyNy45Nl0KWzAxOjI4LjYxXeaIkeivpeWPmOaIkOS7gOS5iOagt+WtkOaJjeiDveW7tue8k+WOjOWApgpbMDE6MzMuMzRdClswMTozMy44Nl3ljp/mnaXlvZPniLHmlL7kuIvpmLLlpIflkI7nmoTov5nkupvpgqPkupsKWzAxOjM4LjQ3XQpbMDE6MzkuNDJd5omN5piv6ICD6aqMClswMTo0MS40MV0KWzAxOjQ0Ljk5XeayoeaEj+ingQpbMDE6NDYuMzddClswMTo0OS4wNl3kvaDmg7PmgI7moLfmiJHpg73pmo/kvr8KWzAxOjUyLjEzXQpbMDE6NTUuMDFd5L2g5ryU5oqA5Lmf5pyJ6ZmQClswMTo1Ny4zN13lj4jkuI3nlKjor7TmhJ/oqIAKWzAyOjAwLjA3XeWIhuW8gOWwseW5s+a3oeS6mwpbMDI6MDIuODhdClswMjowNS4zNV3or6XphY3lkIjkvaDmvJTlh7rnmoTmiJHmvJTop4bogIzkuI3op4EKWzAyOjA5LjkyXQpbMDI6MTAuNDhd5Yir6YC85LiA5Liq5pyA54ix5L2g55qE5Lq65Y2z5YW06KGo5ryUClswMjoxNS4xNV0KWzAyOjE1LjgwXeS7gOS5iOaXtuWAmeaIkeS7rOW8gOWni+ayoeacieS6huW6lee6vwpbMDI6MjAuMjZdClswMjoyMC45NF3pobrnnYDliKvkurrnmoTosI7oqIDooqvliqjlsLHkuI3mmL7lvpflj6/mgJwKWzAyOjI2LjMxXeWPr+S9oOabvue7j+mCo+S5iOeIseaIkeW5suWYm+a8lOWHuue7huiKggpbMDI6MzAuNzhdClswMjozMS41Ml3miJHor6Xlj5jmiJDku4DkuYjmoLflrZDmiY3og73phY3lkIjlh7rmvJQKWzAyOjM1Ljk1XQpbMDI6MzYuNzZd5Y6f5p2l5b2T54ix5pS+5LiL6Ziy5aSH5ZCO55qE6L+Z5Lqb6YKj5LqbClswMjo0MS4xN10KWzAyOjQxLjkxXemDveacieS4quacn+mZkApbMDI6NDQuNDhdClswMjo0Ny45Ml3lhbblrp7lj7DkuIvnmoTop4LkvJflsLHmiJHkuIDkuKoKWzAyOjUxLjg3XQpbMDI6NTMuMDRd5YW25a6e5oiR5Lmf55yL5Ye65L2g5pyJ54K55LiN6IiNClswMjo1Ny4yNl0KWzAyOjU4LjQxXeWcuuaZr+S5n+S5oOaDr+aIkeS7rOadpeWbnuaLieaJrwpbMDM6MDIuMjJdClswMzowMi45N13ov5jorqHovoPnnYDku4DkuYgKWzAzOjA2LjkyXQpbMDM6MDguODZd5YW25a6e6K+05YiG5LiN5byA55qE5Lmf5LiN6KeB5b6XClswMzoxMy4yNV0KWzAzOjE0LjE2XeWFtuWunuaEn+aDheacgOaAleeahOWwseaYr+aLluedgApbMDM6MTguMjNdClswMzoxOS4zOV3otormvJTliLDph43lnLrmiI/otorlk63kuI3lh7rkuoYKWzAzOjIzLjMwXQpbMDM6MjQuMThd5piv5ZCm6L+Y5YC85b6XClswMzoyNy40M10KWzAzOjI5LjE1XeivpemFjeWQiOS9oOa8lOWHuueahOaIkeWwveWKm+WcqOihqOa8lApbMDM6MzMuNzddClswMzozNC4zNl3lg4/mg4XmhJ/oioLnm67ph4znmoTlmInlrr7ku7vkurrmjJHpgIkKWzAzOjM5LjE3XQpbMDM6MzkuNzFd5aaC5p6c6L+Y6IO955yL5Ye65oiR5pyJ54ix5L2g55qE6YKj6Z2iClswMzo0NC4wM10KWzAzOjQ0LjkzXeivt+WJquaOiemCo+S6m+aDheiKguiuqeaIkeeci+S4iuWOu+S9k+mdogpbMDM6NTAuMjBd5Y+v5L2g5pu+57uP6YKj5LmI54ix5oiR5bmy5Zib5ryU5Ye657uG6IqCClswMzo1NC44MV0KWzAzOjU1LjQzXeS4jeWcqOaEj+eahOagt+WtkOaYr+aIkeacgOWQjueahOihqOa8lApbMDQ6MDAuMDRdClswNDowMS4zMl3mmK/lm6DkuLrniLHkvaDmiJHmiY3pgInmi6nooajmvJQKWzA0OjA1LjIyXQpbMDQ6MDYuMTdd6L+Z56eN5oiQ5YWo
     * trans :
     */

    private int retcode;
    private int code;
    private int subcode;
    private String lyric;
    private String trans;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
}
