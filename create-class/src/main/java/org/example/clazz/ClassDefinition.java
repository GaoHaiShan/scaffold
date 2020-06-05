package org.example.clazz;

public class ClassDefinition {
    private String pageName;
    private String className;
    private String basePath;
    private String url;
    //是否使用 rest 风格
    private boolean rest = false;
    //是否需要mq
    private boolean isHasRabbitMQ = false;
    //是否需要redis
    private boolean isHasRedis = false;
    //是否需要dubbo
    private boolean isHashDubbo = false;
    //是否开启事务
    private boolean OpenTransaction=true;
    private String dataBase;
    private String dataBaseUserName;
    private String dataBasePassWord;

    private String redisHost="127.0.0.1";
    private String redisPort="6379";
    //表名对应
    private String tableName;
    //主键
    private String primaryKey = "";

    private String applicationName;


    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRest() {
        return rest;
    }

    public void setRest(boolean rest) {
        this.rest = rest;
    }

    public boolean isHasRabbitMQ() {
        return isHasRabbitMQ;
    }

    public void setHasRabbitMQ(boolean hasRabbitMQ) {
        isHasRabbitMQ = hasRabbitMQ;
    }

    public boolean isHasRedis() {
        return isHasRedis;
    }

    public void setHasRedis(boolean hasRedis) {
        isHasRedis = hasRedis;
    }

    public boolean isHashDubbo() {
        return isHashDubbo;
    }

    public void setHashDubbo(boolean hashDubbo) {
        isHashDubbo = hashDubbo;
    }

    public boolean isOpenTransaction() {
        return OpenTransaction;
    }

    public void setOpenTransaction(boolean openTransaction) {
        OpenTransaction = openTransaction;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDataBaseUserName() {
        return dataBaseUserName;
    }

    public void setDataBaseUserName(String dataBaseUserName) {
        this.dataBaseUserName = dataBaseUserName;
    }

    public String getDataBasePassWord() {
        return dataBasePassWord;
    }

    public void setDataBasePassWord(String dataBasePassWord) {
        this.dataBasePassWord = dataBasePassWord;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
