package org.example.clazz.reader;

import org.example.clazz.ClassDefinition;

import java.util.List;

public interface IReader {
    ClassDefinition reader() throws Exception;

    void setConfig(String conf);
}
