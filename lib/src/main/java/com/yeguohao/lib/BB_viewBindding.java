package com.yeguohao.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BB_viewBindding {

    private List<String> fieldNames;
    private List<String> keys;
    private Map<String, String> providers;

    public BB_viewBindding(Object o) {
        providers = new HashMap<>();
        providers.forEach((key, var) -> {
            Object o1 = MM.getInstance().get(key + "");
            Class<?> aClass = o1.getClass();
            try {
                Method provider = aClass.getMethod("provider", CallBack.class);
                provider.invoke(o1, new CallBack() {
                    @Override
                    public void notifyData() {

                    }
                });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
