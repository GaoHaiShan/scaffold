package org.example;

public interface FactoryBean {
    Object getObject(Class<?> clazz);

    Object getObject(String basePath);
}
