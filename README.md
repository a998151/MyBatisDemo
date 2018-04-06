环境：
* IntelliJ IDEA
* JDK 1.8
* Maven


这是算是一个简单的一个简单Hello World 的 MyBatis 项目。帮助朋友们快速配置和入门。

程序里面有着注释，配合着[官方文档](http://www.mybatis.org/mybatis-3/zh/index.html)，可以很明了。

### 两个重要的配置文件
1. 全局配置文件，包含了数据库的连接，事务管理等。例如本例子中的 mybatis-config.xml 文件。这个文件也可以通过Java代码的方式实现，具体可以查看官方文档。
2. SQL的映射文件，保存了每一条 SQL 语句的映射信息。

这两个配置在开头都引入了格式约束
* http://mybatis.org/dtd/mybatis-3-mapper.dtd
* http://mybatis.org/dtd/mybatis-3-config.dtd

全局配置文件的标签类型与作用，可通过查询官方文档进行查看。

注意：配置文件 configuration 中的元素，不但有类型限制，也有顺序限制。


SQL建表语句
```sql
create table tbl_employee
(
	id int not null auto_increment
		primary key,
	gender char null,
	email varchar(255) null,
	last_name varchar(255) null
)
```


