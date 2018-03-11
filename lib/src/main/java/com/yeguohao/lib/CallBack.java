package com.yeguohao.lib;

import java.util.HashMap;
import java.util.Map;

public class CallBack {

    private Map<String, String> map = new HashMap<>();

    public void set(String key, String value) {
        map.put(key, value);
    }

    public void notifyData() {

    }

}
