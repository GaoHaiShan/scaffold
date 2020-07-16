package org.example.clazz.create;

import org.example.clazz.ClassFactoryBean;
import org.example.clazz.IClassCreator;
import org.example.clazz.create.creator.controller.ControllerClassCreator;
import org.example.clazz.create.creator.config.MyBatisConfingClassCreator;
import org.example.clazz.create.creator.config.YmlConfigClassCreator;
import org.example.clazz.create.creator.dao.BaseDaoClassCreator;
import org.example.clazz.create.creator.dao.DaoClassCreator;
import org.example.clazz.create.creator.interceptor.InsertAndUpdateInterceptorClassCreator;
import org.example.clazz.create.creator.interceptor.TableCacheClassCreator;
import org.example.clazz.create.creator.mapper.MyBatisMapperClassCreator;
import org.example.clazz.create.creator.service.ServiceClassCreator;
import org.example.clazz.create.creator.service.ServiceImplClassCreator;
import org.example.clazz.create.creator.task.TaskClassCreator;
import org.example.clazz.create.creator.until.*;
import org.example.clazz.create.updateconfig.UpdateApplication;
import org.example.clazz.create.updateconfig.UpdatePom;

import java.io.File;

public class DefaultClassFactoryBean extends ClassFactoryBean {
    private IClassCreator startCreator;

    private IClassCreator firstCreateCreator;

    @Override
    protected Object createObject() {

        ControllerClassCreator controllerClassCreator = new ControllerClassCreator();
        controllerClassCreator.setDefinition(super.getDefinitions());

        this.startCreator = controllerClassCreator;

        ServiceClassCreator serviceClassCreator = new ServiceClassCreator();
        serviceClassCreator.setDefinition(super.getDefinitions());
        controllerClassCreator.setIClassCreator(serviceClassCreator);

        ServiceImplClassCreator serviceImplClassCreator = new ServiceImplClassCreator();
        serviceImplClassCreator.setDefinition(getDefinitions());
        serviceClassCreator.setIClassCreator(serviceImplClassCreator);



        MyBatisMapperClassCreator myBatisMapperClassCreator = new MyBatisMapperClassCreator();
        myBatisMapperClassCreator.setDefinition(super.getDefinitions());

        serviceImplClassCreator.setIClassCreator(myBatisMapperClassCreator);

        DaoClassCreator daoClassCreator = new DaoClassCreator();
        daoClassCreator.setDefinition(super.getDefinitions());

        myBatisMapperClassCreator.setIClassCreator(daoClassCreator);

        return invork();

    }

    @Override
    public Object createFirst() {
        MyBatisConfingClassCreator myBatisConfingClassCreator = new MyBatisConfingClassCreator();
        myBatisConfingClassCreator.setDefinition(super.getDefinitions());

        this.firstCreateCreator = myBatisConfingClassCreator;

        BaseDaoClassCreator daoClassCreator = new BaseDaoClassCreator();
        daoClassCreator.setDefinition(super.getDefinitions());
        myBatisConfingClassCreator.setIClassCreator(daoClassCreator);

        InsertAndUpdateInterceptorClassCreator insertAndUpdateInterceptorClassCreator = new InsertAndUpdateInterceptorClassCreator();
        insertAndUpdateInterceptorClassCreator.setDefinition(super.getDefinitions());

        daoClassCreator.setIClassCreator(insertAndUpdateInterceptorClassCreator);

        YmlConfigClassCreator ymlConfigClassCreator = new YmlConfigClassCreator();
        ymlConfigClassCreator.setDefinition(getDefinitions());
        insertAndUpdateInterceptorClassCreator.setIClassCreator(ymlConfigClassCreator);

        SpringPropsClassCreator springPropsClassCreator = new SpringPropsClassCreator();
        springPropsClassCreator.setDefinition(getDefinitions());
        ymlConfigClassCreator.setIClassCreator(springPropsClassCreator);

        GeneratorIDUtilClassCreator generatorIDUtilClassCreator = new GeneratorIDUtilClassCreator();
        generatorIDUtilClassCreator.setDefinition(getDefinitions());
        springPropsClassCreator.setIClassCreator(generatorIDUtilClassCreator);

        FileUtilClassCreator fileUtilClassCreator = new FileUtilClassCreator();
        fileUtilClassCreator.setDefinition(getDefinitions());
        generatorIDUtilClassCreator.setIClassCreator(fileUtilClassCreator);

        JsonUtilClassCreator jsonUtilClassCreator= new JsonUtilClassCreator();
        jsonUtilClassCreator.setDefinition(getDefinitions());
        fileUtilClassCreator.setIClassCreator(jsonUtilClassCreator);

        FilePropsClassCreator filePropsClassCreator = new FilePropsClassCreator();
        filePropsClassCreator.setDefinition(getDefinitions());
        jsonUtilClassCreator.setIClassCreator(filePropsClassCreator);

        TaskClassCreator taskClassCreator = new TaskClassCreator();
        taskClassCreator.setDefinition(getDefinitions());
        filePropsClassCreator.setIClassCreator(taskClassCreator);

        TableCacheClassCreator tableCacheClassCreator = new TableCacheClassCreator();
        tableCacheClassCreator.setDefinition(getDefinitions());
        taskClassCreator.setIClassCreator(tableCacheClassCreator);

        return invorkFirst();
    }

    private Object invork() {
        startCreator.create();
        return null;
    }

    private Object invorkFirst() {
        firstCreateCreator.create();
        UpdatePom.update(new File(this.getBasePath().replaceAll("/+", "/")
                .replaceAll("target.*", "") + "pom.xml"));

        UpdateApplication.update(new File((this.getBasePath().replaceAll("/+", "/")
                .replaceAll("target.*", "/src/main/java/")+this.getFilePath()
                .replaceAll("\\.","/")).replaceAll("/+","/")+".java"));
        return null;
    }
}
