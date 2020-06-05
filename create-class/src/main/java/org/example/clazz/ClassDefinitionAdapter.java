package org.example.clazz;

import java.lang.reflect.Field;

public class ClassDefinitionAdapter {

    public static void changeType(Field field, Object pojo,Object value) throws Exception {
        if (field.getType()==String.class){
            field.set(pojo,value.toString());
            return;
        }
        if(field.getType()==boolean.class){
            field.set(pojo,Boolean.valueOf(value.toString()));
            return;
        }
        throw new Exception("TYPE IS NOT DEFINITION");
    }
}
