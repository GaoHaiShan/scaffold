package org.example.clazz.reader;

import org.example.clazz.ClassDefinition;
import org.example.clazz.ClassDefinitionAdapter;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class PropertiesReader implements IReader {
    private Properties properties = new Properties();


    @Override
    public List<ClassDefinition> reader() throws Exception {
        List<ClassDefinition> definitions = new LinkedList<>();
        ClassDefinition definition = null;

        Map<String,Integer> ma = new HashMap<>();

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String k = entry.getKey().toString().split("\\.")[1];
            String per = entry.getKey().toString().split("\\.")[0];

            if ("end".equals(k)){
                definition = definitions.get(ma.get(per));
                Class<?> clazz = definition.getClass();
                Field field = clazz.getDeclaredField("pageName");
                field.setAccessible(true);
                field.set(definition,per);
                continue;
            }
            if(ma.containsKey(per)){
                definition = definitions.get(ma.get(per));
            }else {
                ma.put(per,ma.size());
                definitions.add(new ClassDefinition());
                definition = definitions.get(ma.get(per));
            }
            Class<?> clazz = definition.getClass();
            Field field = clazz.getDeclaredField(k);
            field.setAccessible(true);
            ClassDefinitionAdapter.changeType(field,definition,entry.getValue());
        }
        return definitions;
    }

    @Override
    public void setConfig(String conf) {
        try(InputStream is = new FileInputStream(new File(conf));) {
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
