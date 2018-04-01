package com.yeguohao.music.p;

import com.yeguohao.music.disc.adapters.DiscAdapte;

public class DataF {

    public static Object getData(Object o) {
        if (o instanceof DiscAdapte) {
            return new DiscAdapte();
        }
        return null;
    }

}
