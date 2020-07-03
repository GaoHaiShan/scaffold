package org.example.clazz;

import org.example.ICreator;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface IClassCreator extends ICreator {
    void setDefinition(ClassDefinition definitions);


    void setIClassCreator(IClassCreator classCreator);
}
