package org.example.clazz;

import org.example.AbstartFactoryBean;
import org.example.clazz.reader.IReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public abstract class ClassFactoryBean extends AbstartFactoryBean {

    private String config;
    private List<ClassDefinition> definitions;
    @Override
    protected Object getObject(boolean first) {
        this.config = super.getBasePath();
        try {
            readConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (first){
            createFirst();
        }
        return createObject();
    }

    public void readConfig(){
        String[] filePath = config.split("\\.");
        String fileName = filePath[filePath.length-1];
        String className = getReaderClassName(fileName);
        try {
            Class<?> clazz = Class.forName(className);
           Constructor constructor = clazz.getConstructor();
           IReader reader = (IReader) constructor.newInstance();
           reader.setConfig(config);
           this.definitions = reader.reader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getReaderClassName(String fileName) {
        char one = fileName.charAt(0);
        if (one >= 'a'){
            one -= 32;
        }
        String c = ClassFactoryBean.class.getPackage().toString().replaceAll("package ","");
        return c+".reader"+"."+one +fileName.substring(1)+"Reader";
    }
    protected abstract Object createObject();
        protected abstract Object createFirst();

    public List<ClassDefinition> getDefinitions() {
        return definitions;
    }
}

