package org.example;

public abstract class AbstartFactoryBean implements FactoryBean {

    private String basePath;

    private boolean first;

    protected String getBasePath() {
        return basePath.replaceAll("/+","/");
    }

    public void isFirst(boolean first){
        this.first = first;
    }

    @Override
    public Object getObject(Class<?> clazz) {
            checkObject(clazz);
        return getObject(first);
    }

    @Override
    public Object getObject(String basePath) {
        checkObject(basePath);
        return getObject(first);
    }

    protected abstract Object getObject(boolean first);

    private void checkObject(Class<?> clazz){
        String basePath = (clazz.getClassLoader().getResource("")
                .getPath().replaceAll("/classes","")
                .replace("/target","")
                +"/src/main/java/"
                +clazz.getPackage().getName()
                .replaceAll("package","")
                .replaceAll(" ","")
                .replaceAll("\\.","/"))
                .replaceAll("/+","/")
                .replaceFirst("/","");
        if (!basePath.equals(this.basePath)) {
            this.basePath = basePath;
        }
    }
    private void checkObject(String basePath){
        if (!basePath.equals(this.basePath)) {
            this.basePath = basePath;
        }
    }
}
