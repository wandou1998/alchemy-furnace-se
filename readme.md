## 本地库首次关联github
```java
git init
git add .
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/wandou1998/alchemy-furnace-se.git
git branch -M main
git push -u origin main
```
## 关于打包
父级项目和dubbo-api都需要打包

## module间bean的引用存在问题
需要在web的启动类中指明扫描路径 ，但是打包后消费者在引用时依旧无法找到bean
在调用接口的module中要实现service包引用
```
<dependency>
<groupId>com.lee</groupId>
<artifactId>af-service</artifactId>
<version>0.0.1-SNAPSHOT</version>
</dependency>
```

## java: 错误: 不支持发行版本 5
解决方式：https://www.cnblogs.com/Javaer1995/p/18233085

## dubbo api的定义和使用
在服务端，接口的实现类上要使用@DubboService注册接口
在消费端，要使用@DubboReference 引用接口，而不能直接使用spring的注解