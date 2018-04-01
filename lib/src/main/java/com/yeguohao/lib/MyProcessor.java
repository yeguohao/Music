package com.yeguohao.lib;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yeguohao.lib.Annotations.Field;
import com.yeguohao.lib.Annotations.Notice;
import com.yeguohao.lib.Annotations.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MyProcessor extends AbstractProcessor {

    private Elements elements;
    private Filer filer;
    private Messager messager;
    private String noticeMethodName;
    private TypeSpec helloWorld;
    private String className;
    private MethodSpec.Builder constructorMethod;

    private List<String> fieldNames;
    private List<String> keys;
    private Map<String, String> providers;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();

        fieldNames = new ArrayList<>();
        keys = new ArrayList<>();
        providers = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Field.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        parseNotice(roundEnvironment);
        parseField(roundEnvironment);
        parseProvider(roundEnvironment);

        ClassName string = ClassName.get(String.class);
        ClassName arrayList = ClassName.get(ArrayList.class);
        TypeName listOfString = ParameterizedTypeName.get(arrayList, string);
        helloWorld = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(listOfString, "fieldNames", Modifier.PUBLIC)
                .addField(listOfString, "keys", Modifier.PUBLIC)
                .addMethod(constructorMethod.build())
                .build();

        return false;
    }

    private void parseNotice(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Notice.class);
        if (elements.size() > 1) {
            messager.printMessage(Diagnostic.Kind.ERROR, "只能有一个注解");
            return;
        }
        for (Element element : elements) {
            if (element.getKind() != ElementKind.METHOD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only method can be annotated with @Notice.");
                return;
            }
            noticeMethodName = element.getSimpleName().toString();
        }
    }

    private void parseField(RoundEnvironment roundEnvironment) {
        constructorMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this.$N = new $N<>()", "fieldNames", "ArrayList")
                .addStatement("this.$N = new $N<>()", "keys", "ArrayList");

        Element parent = null;
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Field.class)) {
            if (element.getKind() != ElementKind.FIELD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only field can be annotated with @Field.");
                return;
            }
            Field annotation = element.getAnnotation(Field.class);
            String value = annotation.value();

            constructorMethod.addStatement("this.$N.add($N)", "fieldNames", element.getSimpleName())
                    .addStatement("this.$N.add($N)", "keys", value);

            fieldNames.add(element.getSimpleName().toString());
            keys.add(value);

            if (parent == null) {
                parent = element.getEnclosingElement();
            }
        }

        assert parent != null;
        className = parent.getSimpleName().toString();
    }

    private void parseProvider(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Provider.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only Class can be annotated with @Field.");
                return;
            }
            Provider annotation = element.getAnnotation(Provider.class);
            Class value = annotation.value();
            for (java.lang.reflect.Field field : value.getDeclaredFields()) {
                try {
                    Object o = field.get(null);
                    if (!(o instanceof String)) {
                        continue;
                    }
                    providers.put(((String) o), value.getCanonicalName());
                } catch (NullPointerException | IllegalAccessException e) {

                }
            }

        }
    }

}
