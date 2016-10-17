## JFinal事物
MySQL数据库事务隔离级别：http://www.cnblogs.com/zemliu/archive/2012/06/17/2552301.html

配置见JFinal文档：http://www.jfinal.com/man

*如果对事物的要求高，需要将mysql的事物级别提升。事物级别提升之后性能会降低，建议采用主从实现读写分离。
```
arp.setTransactionLevel(8);
```

## mysql主从配置
mysql 的主从复制配置：http://my.oschina.net/u/255939/blog/505598

单服务器MySQL主从复制实践：http://my.oschina.net/moson/blog/396244

*建议:使用虚拟机多搭建几个服务

Druid配置： https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_DruidDataSource%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE

## JFinal中主从配置和使用
```
// 主配置Druid数据库连接池插件
DruidPlugin masterDruidPlugin = new DruidPlugin(masterJdbcUrl, masterUser, masterPassword);
masterDruidPlugin.addFilter(new StatFilter()).addFilter(new Slf4jLogFilter());
WallFilter masterWall = new WallFilter();
masterWall.setDbType("mysql");
masterDruidPlugin.addFilter(masterWall);
me.add(masterDruidPlugin);
// 主配置ActiveRecord插件
ActiveRecordPlugin masterArp = new ActiveRecordPlugin("master", masterDruidPlugin);
masterArp.addMapping("t_user", "id", TUser.class);
masterArp.addMapping("blog", Blog.class);
masterArp.setShowSql(isdev);
me.add(masterArp);

// 从数据源
DruidPlugin slaveDruidPlugin = new DruidPlugin(slaveJdbcUrl, slaveUser, slavePassword);
slaveDruidPlugin.addFilter(new StatFilter()).addFilter(new Slf4jLogFilter());
WallFilter slaveWall = new WallFilter();
slaveWall.setDbType("mysql");
slaveDruidPlugin.addFilter(slaveWall);
me.add(slaveDruidPlugin);
// 从ActiveRecord插件
ActiveRecordPlugin slaveArp = new ActiveRecordPlugin("slave", slaveDruidPlugin);
slaveArp.setShowSql(isdev);
me.add(slaveDruidPlugin);
```

读写分离：主(master)执行save、update、delete，从(slave)执行find、query

## 说明：http://www.oschina.net/question/936368_221458
1. 有多少个数据源就要new多少个C3p0Plugin 或者 DruidPlugin
2. 每个 ActiveRecordPlugin 对应一个数据源，所以多个数据源之下，也要 new 出多个 ActiveRecordPlugin 对象
3. 每个 Model 唯一对应一个 ActiveRecordPlugin 对象，且只能被某一个 ActiveRecordPlugin 对象使用 addMapping(...) 进行映射，所以同一个 Model 不能添加到不同的 ActiveRecrodPlugin
4. Model 唯一对应一个 ActiveRecordPlugin ，ActiveRecordPlugin 唯一对应一个 C3p0Plugin，推导出 Model 唯一对应一个数据源，所以 Model 与数据源的对应关系是自动化解决的，因为在构建 ActiveRecordPlugin 对象的时候就已经确立了对应关系
5. 而 Db + Record 模式没有像 Model 一样建立与数据源的一一对应的关系，所以在多数据源之下需要使用 Db.use(configName) 来指定数据源

*建议：从库(slave)查询建议都采用Db去处理
