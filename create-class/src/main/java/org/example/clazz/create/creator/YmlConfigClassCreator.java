package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class YmlConfigClassCreator extends AbstartClassCreator {
    private String path;
    @Override
    protected File createJavaFile(String basePath, String className) {
        this.path = basePath;
        basePath = basePath.replaceAll("java.*","");
        basePath += "resources/";
        File file = null;
        try {
            file = createJavaFile(basePath,"","application",".yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return "";
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("server:\n" +
                "  port: 8080\n" +
                "\n" +
                "mybatis:\n" +
                "  mapper-locations: classpath:/mapper/*.xml\n" +
                "  config-location: classpath:mybatis.xml\n" +
                "spring:\n" +
                "  application:\n" +
                "    name: "+getThisClassDefinition().getApplicationName()+"\n" +
                "  sleuth:\n" +
                "    rxjava:\n" +
                "      schedulers:\n" +
                "        hook:\n" +
                "          enabled: true\n" +
                "  redis:\n" +
                "    host: "+getThisClassDefinition().getRedisHost()+"\n" +
                "    port: "+getThisClassDefinition().getRedisPort()+"\n" +
                "#    password: pAssw0r_d\n" +
                "\n" +
                "  jpa:\n" +
                "    show-sql: true\n" +
                "    properties:\n" +
                "      hibernate:\n" +
                "        format_sql: true\n" +
                "  datasource:\n" +
                "    driver-class-name: com.mysql.cj.jdbc.Driver\n" +
                "    url: jdbc:mysql://127.0.0.1:3306/"+getThisClassDefinition().getDataBase()+"?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false\n" +
                "    username: "+getThisClassDefinition().getDataBaseUserName()+"\n" +
                "    password: "+getThisClassDefinition().getDataBasePassWord()+"\n" +
                "    druid:\n" +
                "      stat-view-servlet:\n" +
                "        enabled: false\n" +
                "        allow: 0.0.0.0/0\n" +
                "      filter.stat.enabled: true\n" +
                "  jackson:\n" +
                "    date-format: yyyy-MM-dd HH:mm:ss\n" +
                "    time-zone: GMT+8\n" +
                "  cloud:\n" +
                "    zookeeper:\n" +
                "      enabled: false\n" +
                "\n" +
                "#coframe-sdk\\u914D\\u7F6E\n" +
                "coframe:\n" +
                "  server: # \\u9700\\u8981\\u4F7F\\u7528\\u63A5\\u53E3\\u4EE3\\u7406\\u65F6\\u5FC5\\u987B\\u914D\\u7F6E\n" +
                "    name: FPSO-COFRAME  # coframe\\u670D\\u52A1\\u540D\\u79F0\n" +
                "    context-path: /   # coframe\\u670D\\u52A1\\u7684context path\n" +
                "  cache:    # \\u9700\\u8981\\u6821\\u9A8Ctoken\\u548C\\u529F\\u80FD\\u65F6\\u5FC5\\u987B\\u914D\\u7F6E, \\u76EE\\u524D\\u53EA\\u652F\\u6301redis. \\u5FC5\\u987B\\u4E0E\\u9700\\u8981\\u96C6\\u6210\\u7684coframe\\u914D\\u7F6E\\u76F8\\u540C\n" +
                "    enabled: true    # \\u542F\\u7528\\u7F13\\u5B58\n" +
                "    type: redis   # \\u7C7B\\u578Bredis\n" +
                "    key-namespace: FPSO-COFRAME  # \\u7F13\\u5B58\\u547D\\u540D\\u7A7A\\u95F4CNOOCFIVE-COFRAME\n" +
                "  filter:\n" +
                "    token:  # \\u9700\\u8981\\u7528\\u5230token\\u6821\\u9A8C\\u65F6, \\u5FC5\\u987B\\u914D\\u7F6E\n" +
                "      enabled: true   # \\u662F\\u5426\\u542F\\u7528token\\u6821\\u9A8C\\u8FC7\\u6EE4\\u5668, \\u9ED8\\u8BA4\\u4E3A true\n" +
                "      validate-token: true   # \\u662F\\u5426\\u6821\\u9A8C token, \\u9ED8\\u8BA4\\u4E3A false\n" +
                "      uri-patterns: /file/*  # \\u62E6\\u622A\\u54EA\\u4E9B\\u8BF7\\u6C42, \\u652F\\u6301\\u4F7F\\u7528*\\u53F7\\u901A\\u914D, \\u652F\\u6301\\u914D\\u7F6E\\u591A\\u6761\\u4EF6, \\u4EE5\\u9017\\u53F7\\u5206\\u5F00\n" +
                "      exclude-uri-patterns: /api/demo/paas   # \\u4E0D\\u62E6\\u622A\\u54EA\\u4E9B\\u8BF7\\u6C42, \\u540C\\u6837\\u652F\\u6301\\u4F7F\\u7528*\\u53F7\\u901A\\u914D, \\u652F\\u6301\\u914D\\u7F6E\\u591A\\u6761\\u4EF6, \\u4EE5\\u9017\\u53F7\\u5206\\u5F00\n" +
                "  tools:\n" +
                "    enabled: false   # \\u662F\\u5426\\u5411\\u5916\\u63D0\\u4F9B\\u5DE5\\u5177\\u7C7B\\u578B\\u7684\\u63A5\\u53E3. \\u76EE\\u524D\\u63D0\\u4F9B\\u7684\\u5DE5\\u5177\\u63A5\\u53E3\\u4E2D, \\u66B4\\u9732\\u4E86\\u4E00\\u4E2A\\u529F\\u80FD\\u626B\\u63CF\\u7684\\u63A5\\u53E3, \\u8C03\\u7528\\u8005\\u53EF\\u4EE5\\u8C03\\u7528\\u6B64\\u63A5\\u53E3, \\u83B7\\u53D6\\u4EE3\\u7801\\u4E2D\\u5B9A\\u4E49\\u7684\\u529F\\u80FD\\u5B9A\\u4E49. \\u6B64\\u914D\\u7F6E\\u5EFA\\u8BAE\\u5F00\\u53D1\\u65F6\\u8BBE\\u7F6E\\u4E3Atrue, \\u800C\\u751F\\u4EA7\\u73AF\\u5883\\u4E2D\\u8BBE\\u7F6E\\u4E3Afalse\n" +
                "\n" +
                "\n" +
                "#SDK\\u914D\\u7F6E\n" +
                "eos:\n" +
                "  application:\n" +
                "    sys-code: FPSO-MANAGE\n" +
                "    sys-key: f6fc72a3fe1a4ab8bd3bdbaebab2af0f     #Governor #\\u521B\\u5EFA\\u7CFB\\u7EDF\\u540E\\u586B\\u5199\n" +
                "  springfox:\n" +
                "    enabled: true\n" +
                "  logging:\n" +
                "    outbound:\n" +
                "      enabled: true\n" +
                "      hh-enabled: true\n" +
                "    inbound:\n" +
                "      enabled: true\n" +
                "      hh-enabled: true\n" +
                "\n" +
                "management:\n" +
                "  endpoints:\n" +
                "    web:\n" +
                "      exposure:\n" +
                "        include: hystrix.stream,health,info,loggers,eos,mappings\n" +
                "\n" +
                "\n" +
                "eureka:\n" +
                "  client:\n" +
                "    enabled: true\n" +
                "    service-url:\n" +
                "      defaultZone: http://127.0.0.1:8761/eureka/\n" +
                "  instance:\n" +
                "    prefer-ip-address: true\n" +
                "    lease-renewal-interval-in-seconds: 10\n" +
                "    lease-expiration-duration-in-seconds: 30\n" +
                "\n" +
                "hystrix:\n" +
                "  command:\n" +
                "    default:\n" +
                "      execution:\n" +
                "        isolation:\n" +
                "          thread:\n" +
                "            timeoutInMilliseconds: 1000000\n" +
                "  config:\n" +
                "    stream:\n" +
                "      maxConcurrentConnections: 20\n" +
                "  metrics:\n" +
                "    enabled: false\n" +
                "\n" +
                "feign:\n" +
                "  hystrix:\n" +
                "    enabled: true\n" +
                "ribbon:\n" +
                "  ReadTimeout: 15000\n" +
                "  ConnectTimeout: 15000\n" +
                "  MaxAutoRetries: 1 #\\u540C\\u4E00\\u53F0\\u5B9E\\u4F8B\\u6700\\u5927\\u91CD\\u8BD5\\u6B21\\u6570,\\u4E0D\\u5305\\u62EC\\u9996\\u6B21\\u8C03\\u7528\n" +
                "  MaxAutoRetriesNextServer: 1 #\\u91CD\\u8BD5\\u8D1F\\u8F7D\\u5747\\u8861\\u5176\\u4ED6\\u7684\\u5B9E\\u4F8B\\u6700\\u5927\\u91CD\\u8BD5\\u6B21\\u6570,\\u4E0D\\u5305\\u62EC\\u9996\\u6B21\\u8C03\\u7528\n" +
                "  OkToRetryOnAllOperations: false  #\\u662F\\u5426\\u6240\\u6709\\u64CD\\u4F5C\\u90FD\\u91CD\\u8BD5\n" +
                "\n" +
                "\n" +
                "apollo:\n" +
                "  bootstrap:\n" +
                "    enabled: false\n" +
                "  meta: http://10.79.35.18:18081/\n" +
                "\n" +
                "upload:\n" +
                "  file:\n" +
                "    path-file: /usr/cnoocfive-filedir/file\n" +
                "    path-month: /usr/cnoocfive-filedir/month\n" +
                "    path-image: /usr/cnoocfive-filedir/image\n" +
                "\n" +
                "#jasypt\\u52A0\\u89E3\\u5BC6\\u914D\\u7F6E\n" +
                "jasypt:\n" +
                "  encryptor:\n" +
                "    publicKeyString: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKpUxXb2rfUEaBTHvWXbbuVVXpHHT56cAyIYNUobMQXMiwv2iZYi9U7bi5DQ5b6ABiYtxZbvI0tD+hf9CXmnwXcNx0KTkFYW34Wt5crANoBIaqGscSA0Ukxo/jNCSaha8TwkSRdCIl2u/xq7bP7IvovqDwtVVmmRMxJ9rznkrqGQIDAQAB\n" +
                "    publicKeyFormat: DER\n" +
                "    privateKeyString: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMqlTFdvat9QRoFMe9Zdtu5VVekcdPnpwDIhg1ShsxBcyLC/aJliL1TtuLkNDlvoAGJi3Flu8jS0P6F/0JeafBdw3HQpOQVhbfha3lysA2gEhqoaxxIDRSTGj+M0JJqFrxPCRJF0IiXa7/Grts/si+i+oPC1VWaZEzEn2vOeSuoZAgMBAAECgYBst7H1VrknhZHN3JKztyNlSjMFFVdMnLOYMZKb3QSMSrsA2C/t2lc6YS8xWGfTtuw93HwLHxKiY/GfW0s1ipP3qb0B40YP/PCPHqf94XQK6ChzrVIQ6k4oNLWYCJwDCwXys06ANP308h0PTsRgidIhbJv9Z5WkY5HFvstSv7954QJBAP0TiVa2hiR3ehsjbIp5mJA9mu6t4h2nDcgCLZjweauKr4EpGGVeJLAXdPbzEIwOYIgTszLru96QCfd45U5GkG8CQQDM/J13hteFL7itau4yxAbRSHLouMyrM5gb/BzziVGN8lCdmK42cibpXDvZJI+RhAhAWzDJC6AtRjdRz0J6/OH3AkA6DhNBWxmmn+nY8+VgVsiSvi8edbEbUEkvCqDfJrsiiOv92ymHh3MvGhJw3A19s4adcLd5BO7R/YTrykYAENvtAkAEB1Lw7m196JowjlFwHfokUtAvUrJzCuiKZEINZz17FLAQ6NdDqqqpG27xrY5ExduDqEclLF1Rhne/29rqn92xAkEA1HnwG1eEJqg9fbCqtd5ZdxhMKs48fpFem/WxzmVT1eLp62bypfXjO90ZRZzA1O0NDPkHpM5KGuDet0bE7Ok/Fg==\n" +
                "    privateKeyFormat: DER\n");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }
}
