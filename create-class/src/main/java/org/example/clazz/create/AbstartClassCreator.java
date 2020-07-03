package org.example.clazz.create;

import org.example.ICreator;
import org.example.clazz.ClassDefinition;
import org.example.clazz.IClassCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public abstract class AbstartClassCreator implements IClassCreator {


    private IClassCreator classCreator;

    private ClassDefinition thisClassDefinition;

    @Override
    public void create(String... className) {
        run();
        if (classCreator != null) {
            classCreator.create();
        }

    }


    public void run() {
        loadDefinitions();
    }


    @Override
    public void setDefinition(ClassDefinition definitions) {
        this.thisClassDefinition = definitions;
    }


    @Override
    public void setIClassCreator(IClassCreator classCreator) {
        this.classCreator = classCreator;
    }


    protected File createJavaFile(String basePath, String className, String per, String fix) throws IOException {
        File file = new File(basePath + per + className + fix);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            try {
                throw new Exception("File creation failed : " + className + fix + " is early create.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    protected void loadDefinitions() {
        File file = createJavaFile(thisClassDefinition.getBasePath(), thisClassDefinition.getClassName());
        editCode(file, thisClassDefinition);
    }

    private void editCode(File file, ClassDefinition definition) {
        StringBuffer sb = new StringBuffer();
        String page = getPackage(definition.getBasePath());
        sb.append(page);
        if (definition.isRest()) {
            restEditCode(file, definition.getUrl(), sb, definition.getClassName(), page);
        } else {
            deflauteEditCode(file, definition.getUrl(), sb, definition.getClassName(), page);
        }
        try (
                FileOutputStream fo = new FileOutputStream(file);
        ) {
            fo.write(sb.toString().getBytes());
            fo.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getPackage(String basePath, String pageName) {
        String formatPath = basePath.replaceAll("/+", "/");
        if (formatPath.charAt(0) == '/')
            formatPath = formatPath.substring(1);
        String[] arr = formatPath.split("/");
        boolean f = false;
        StringBuffer sb = new StringBuffer();
        sb.append("package ");
        for (String s : arr) {
            if (s.equals("java"))
                f = true;
            if (f) {
                sb.append(s);
                if (!s.equals(arr.length - 1)) {
                    sb.append(".");
                }
            }
        }

        sb.append(pageName);
        sb.append(";");
        return sb.toString().replaceAll("java\\.", "");
    }

    public ClassDefinition getThisClassDefinition() {
        return thisClassDefinition;
    }

    protected abstract File createJavaFile(String basePath, String className);

    protected abstract String getPackage(String basePath);

    protected abstract void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page);

    protected abstract void restEditCode(File file, String url, StringBuffer sb, String className, String page);

}
