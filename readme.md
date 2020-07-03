本项目致力于简化项目开发配置，主要的根据作者的自身所学去创建对应配置 以及模板，最终想去实现一个只去书写一个个的逻辑处理，以及前台页面，    
而去忽略使用某种框架的配置，使用户可更加简便高效的完成对应功能，减少用户对于整合某种框架的配置，以及简化 api 的使用，降低用户开发某个   
网站 app 小程序的门槛。    
我们应将更多的时间放在性能上面，而不是crud中。
目前只做了后台
jar使用教程
1、在springboot启动类修改为
```java
  public class Application {

      public static void main(String[] args) {
          QuickStartFactory quickStartFactory = new QuickStartFactory();
          //第一次创建需要加载配置文件 这个方法变为 true
          quickStartFactory.isFirst(true);
          quickStartFactory.getObject(Application.class);
  //       SpringApplication.run(Application.class, args);
      }

  }
```
2、运行main方法会生成 两个类（CreateClassStart.java,CreatePageStart.java）以及一个配置文件(createclass.properties)
3、运行CreatePageStart.java main方法生成对应项目目录
4、配置createclass.properties文件
5、运行CreateClassStart.java main方法生成对应文件即可
6、若想继续生成类重复第四步和第五步即可
# 注意
第一次生成完类以后需将 CreateClassStart中 isFrist置为false
```java
public class CreateClassStart {
    public static void main(String[] args) {

        //创建类
        AbstartFactoryBean factoryBean = new DefaultClassFactoryBean();
        factoryBean.isFirst(true);
        factoryBean.getObject("com.primeton.eos.Application",CreateClassStart.class.getClassLoader().getResource("").getPath()
                .replaceAll("java.*","resources")+"/createclass.properties");
    }
}
```
