package org.example.clazz.create;

import org.example.clazz.ClassFactoryBean;
import org.example.clazz.IClassCreator;
import org.example.clazz.create.creator.*;
import org.example.clazz.create.updateconfig.UpdatePom;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultClassFactoryBean extends ClassFactoryBean {
    private ExecutorService service = Executors.newFixedThreadPool(5);
    private IClassCreator startCreator;

    private IClassCreator firstCreateCreator;
    @Override
    protected Object createObject() {

        ControllerClassCreator controllerClassCreator = new ControllerClassCreator();
        controllerClassCreator.setDefinition(super.getDefinitions());
        controllerClassCreator.setExecutorService(service);
        this.startCreator = controllerClassCreator;

        ServiceClassCreator serviceClassCreator = new ServiceClassCreator();
        serviceClassCreator.setDefinition(super.getDefinitions());
        serviceClassCreator.setExecutorService(service);
        controllerClassCreator.setIClassCreator(serviceClassCreator);

        ServiceImplClassCreator serviceImplClassCreator = new ServiceImplClassCreator();
        serviceImplClassCreator.setDefinition(getDefinitions());
        serviceImplClassCreator.setExecutorService(service);
        serviceClassCreator.setIClassCreator(serviceImplClassCreator);

        DaoClassCreator daoClassCreator = new DaoClassCreator();
        daoClassCreator.setDefinition(super.getDefinitions());
        daoClassCreator.setExecutorService(service);
        serviceImplClassCreator.setIClassCreator(daoClassCreator);

        MyBatisMapperClassCreator myBatisMapperClassCreator = new MyBatisMapperClassCreator();
        myBatisMapperClassCreator.setDefinition(super.getDefinitions());
        myBatisMapperClassCreator.setExecutorService(service);
        daoClassCreator.setIClassCreator(myBatisMapperClassCreator);
        return invork();

    }

    @Override
    public Object createFirst(){
        MyBatisConfingClassCreator myBatisConfingClassCreator = new MyBatisConfingClassCreator();
        myBatisConfingClassCreator.setDefinition(super.getDefinitions());
        myBatisConfingClassCreator.setExecutorService(service);
        this.firstCreateCreator = myBatisConfingClassCreator;

        InsertAndUpdateInterceptorClassCreator insertAndUpdateInterceptorClassCreator = new InsertAndUpdateInterceptorClassCreator();
        insertAndUpdateInterceptorClassCreator.setDefinition(super.getDefinitions());
        insertAndUpdateInterceptorClassCreator.setExecutorService(service);
        myBatisConfingClassCreator.setIClassCreator(insertAndUpdateInterceptorClassCreator);

        YmlConfigClassCreator ymlConfigClassCreator = new YmlConfigClassCreator();
        ymlConfigClassCreator.setDefinition(getDefinitions());
        ymlConfigClassCreator.setExecutorService(service);
        insertAndUpdateInterceptorClassCreator.setIClassCreator(ymlConfigClassCreator);

        SpringPropsClassCreator springPropsClassCreator = new SpringPropsClassCreator();
        springPropsClassCreator.setDefinition(getDefinitions());
        springPropsClassCreator.setExecutorService(service);
        ymlConfigClassCreator.setIClassCreator(springPropsClassCreator);

        GeneratorIDUtilClassCreator generatorIDUtilClassCreator = new GeneratorIDUtilClassCreator();
        generatorIDUtilClassCreator.setDefinition(getDefinitions());
        generatorIDUtilClassCreator.setExecutorService(service);
        springPropsClassCreator.setIClassCreator(generatorIDUtilClassCreator);

        FileUtilClassCreator fileUtilClassCreator = new FileUtilClassCreator();
        fileUtilClassCreator.setDefinition(getDefinitions());
        fileUtilClassCreator.setExecutorService(service);
        generatorIDUtilClassCreator.setIClassCreator(fileUtilClassCreator);
        return invorkFirst();
    }
    private Object invork() {
        startCreator.create();
        return null;
    }
    private Object invorkFirst(){
        firstCreateCreator.create();
        UpdatePom.update(new File(this.getBasePath().replaceAll("/+","/")
                .replaceAll("target.*","")+"pom.xml"));
        return null;
    }
}
