## 本地库首次关联github
```java
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/wandou1998/alchemy-furnace-se.git
git branch -M main
git push -u origin main
```
## 关于打包
父级项目和dubbo-provider都需要打包

## module间bean的引用存在问题
需要在web的启动类中指明扫描路径 ，但是打包后消费者在引用时依旧无法找到bean
解决方案！！！

## java: 错误: 不支持发行版本 5
解决方式：https://www.cnblogs.com/Javaer1995/p/18233085