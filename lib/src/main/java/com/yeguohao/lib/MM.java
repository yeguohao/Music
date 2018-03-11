package com.yeguohao.lib;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.yeguohao.lib.Annotations.Field;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public class MM {

    private static MM mm;

    private Map<String, Object> instances;

    private MM() {
        instances = new HashMap<>();
    }

    public Object get(String className) {
        Object o = instances.get(className);
        if (o == null) {
            o = i(className);
        }
        return o;
    }

    private Object i(String className) {
        Object o = null;
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?> constructor = aClass.getConstructor();
            o = constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static MM getInstance() {
        if (mm == null) {
            mm = new MM();
        }
        return mm;
    }
}
