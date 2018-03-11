package com.yeguohao.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AA {

    public static void fetch(Object o) {
        String canonicalName = o.getClass().getCanonicalName();
        try {
            Class<?> aClass = Class.forName(canonicalName + "");
            try {
                Object o2 = aClass.getConstructor().newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            Method provider = aClass.getMethod("provider", CallBack.class);
            Object o1 = MM.getInstance().get(canonicalName + "");
            provider.invoke(o1, new CallBack() {
                @Override
                public void notifyData() {

                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
