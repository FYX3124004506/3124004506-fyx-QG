# QGWeek1小组学习笔记

## JDBC

#### JDBC概念:

·JDBC 就是使用Java语言操作关系型数据库的一套API	同一套Java代码，操作不同的关系型数据库	
·全称:(Java DataBase Connectivity) Java数据库连接

#### JDBC本质:	Java代码	

官方(sun公司)定义的一套操作所有关系型数据库的规则，即接口·各个数据库厂商去实现这套接口，提供数据库驱动jar包
我们可以使用这套接口(JDBC)编程，真正执行的代码是驱动jar包	JDBC	一套标准接口	
中的实现类

#### JDBC 好处:	

各数据库厂商使用相同的接口，Java代码不	驱动	MySQL	驱动	Oracle	驱动	DB2	实现类	
需要针对不同数据库分别开发
可随时替换底层数据库，访问数据库的代码基本不变



### JDBC 就是使用Java语言操作关系型数据库的一套API

//1.注册驱动
Class.forName("com.mysql.jdbc.Driver");

//2.获取连接对象
String url = "jdbc:mysql://127.0.0.1:3306/db1?useSSL=false"; string username = "root"; string password = "1234";
Connection conn = DriverManager.getConnection(url,username, password);

//3.定义SQL
String sql = "update account set money = 2000 where id = 1"

;//4.获取执行sql的对象
statement stmt = conn.createStatement();

//5.执行sql
int count = stmt.executeUpdate(sql);

//6.处理结果
//system.out.println(count);

//7.释放资源 stmt.close(); conn.close():



基本形式：

创建工程，导入驱动jar包
mysql-connector-java-5.1.48.jar
1.注册驱动
Class.forName("com.mysql.jdbc.Driver");	Java代码	

2. 获取连接
    Connection conn = DriverManager.getConnection(url, username, password);
3. 定义SQL语句
    String sql = “update...”;
4. 获取执行SQL对象	SQL	返回结果	
    Statement stmt = conn.createStatement();
5. 执行SQL
    stmt.executeUpdate(sql);
6. 处理返回结果	MySQL:	
7. 释放资源

### DriverManager

DriverManager(驱动管理类)作用:
1. 注册驱动
2.获取数据库连接

##### 使用方法：

###### 1.注册驱动

Class.forName("com.mysql.jdbc.Driver");

查看 Driver类源码
static {
try {
DriverManager.registerDriver(new Driver());} catch (SQLException var1) {
throw new RuntimeException("Can't register driver!");
}
}



提示:
MySQL5之后的驱动包，可以省略注册驱动的步骤
自动加载jar包中META-INF/services/java.sql.Driver文件中的驱动类

###### 2.获取连接

static Connection	getConnection (String url, string user, string password)	
参数

1. url: 连接路径
语法: jdbc:mysql://ip地址(域名):端口号/数据库名称?参数键值对1&参数键值对2...示例:jdbc:mysql://127.0.0.1:3306/db1细节:
·如果连接的是本机mysql服务器，并且mysql服务默认端口是3306，则url可以简写为:jdbc:mysql:///数据库名称?参数键值对· 配置 useSSL=false参数，禁用安全连接方式，解决警告提示

2.user: 用户名

3.password: 密码

### Connection

Connection(数据库连接对象)作用:

1. 获取执行 SQL 的对象
    2.管理事务

使用方法

1. 获取执行SQL 的对象
普通执行SQL对象
Statement	createStatement()	
预编译SQL的执行SQL对象:防止SQL注入
PreparedStatement	prepareStatement (sql)	
执行存储过程的对象

CallableStatementprepareCall (sql)



2.然后，调用Connection的prepareCall()方法创建一个CallableStatement对象，并将需要执行的存储过程的SQL语句作为参数传入，例如:

CallableStatement cstmt = conn.prepareCall("{call getEmployees(?， ?)}");

上述代码中，"{call getEmployees(?,?)}"是一个存储过程的调用语句，其中"?"表示占位符，后续需要通过setXXX()方法将具体值设置进去。



3.在CallableStatement对象中设置输入参数和输出参数，例如:
cstmt.setString(1,"John Doe");//设置存储过程的第一个输入参数
cstmt.registerOutParameter(2，Types.INTEGER); //注册存储过程的第二个输出参数

上述代码中，setString()方法用于向存储过程的第一个输入参数设置具体的值，registerOutParameter()方法用于注册存储过程的第二个输出参数的类型，本例中为整数类型。



4.调用CallableStatement对象的execute()或executeQuery()方法执行存储过程，并获取返回结果，例如:

ResultSet rs = cstmt.executeQuery(); //执行存储过程并获取返回的结果集 int rowCount = cstmt.getInt(2); //获取存储过程的第二个输出参数的值



#### 事务管理

·MySQL 事务管理
开启事务: BEGIN; / START TRANSACTION;提交事务:COMMIT;回滚事务:ROLLBACK;



MySQL默认自动提交事务
·JDBC事务管理:Connection接口中定义了3个对应的方法
开启事务:setAutoCommit(boolean autoCommit):true为自动提交事务;false为手动提交事务，即为开启事务提交事务: commit()回滚事务:rollback()

使用方法：try {
//开启事务
conn.setAutoCommit(false);

//5.执行sql
int count1 = stmt.executeUpdate(sql1);//受影响的行数

//6.处理结果
System.out.println(count1);
//5.执行sql
int count2 = stmt.executeUpdate(sql2);//受影响的行数

//6.处理结果
System.out.println(count2);
//提交事务
conn.commit();
} catch (Exception throwables) {
//回滚事务
conn.rollback();
throwables.printStackTrace();}



### Statement

·Statement作用:

1. 执行SQL语句·执行SQL语句
int executeUpdate(sql): 执行DML、DDL语句
返回值:(1) DML语句影响的行数(2) DDL语句执行后，执行成功也可能返回0

ResultSet executeQuery(sql): 执行DQL 语句
返回值:ResultSet结果集对象



### ResultSet

·ResultSet(结果集对象)作用:
1.封装了DQL查询语句的结果
ResultSet stmt.executeQuery(sql): 执行DQL 语句，返回ResultSet 对象



·获取查询结果
boolean next(): (1)将光标从当前位置向前移动一行(2)判断当前行是否为有效行	


返回值:
true: 有效行，当前行有数据		
false: 无效行，当前行没有数据

xxx getXxx(参数):获取数据
> xxx: 数据类型;如:int getlnt(参数); String getString(参数)参数:
> ·int: 列的编号，从1开始· String: 列的名称



##### 使用步骤:

1.游标向下移动一行，并判断该行否有数据:next()
2.获取数据:getXxx(参数)

//循环判断游标是否是最后一行末尾 while(rs.next()){//获取数据
rs.getXxx(参数);

使用方法：

//1.注册驱动
//Class.forName("com.mysql.jdbc.Driver");

//2.获取连接:如果连接的是本机mysql并且端口是默认的3306可以简化书写 String url = "jdbc:mysql:///db1?useSSL=false"; String username= "root"; string password = "1234";
Connection conn = DriverManager.getConnection(url, username, password);

//3.定义sql
String sql = "select * from account";

//4.获取statement对象
Statement stmt = conn.createStatement();

//5.执行sql
ResultSet rs = stmt.executeQuery(sql);

//6.处理结果，遍历rs中的所有数据
// 6.1光标向下移动一行，并且判断当前行是否有数据 while (rs.next()){
//6.2 获取数据getXxx()
int id = rs.getInt(columnlndex:1);

string name = rs.getstring(columnlndex:2);

double money = rs.getDouble(columnIndex:3);
System.out.println(id);

System.out.println(name); System.out.println(money);

System.out.println("--------------");
}

//7.释放资源 rs.clÃse(); stmt.close(); conn.close();|



### PreparedStatement

·PreparedStatement作用:
1.预编译SQL语句并执行:预防SQL注入问题
·SQL注入
· SQL注入是通过操作输入来修改事先定义好的SQL语句，用以达到执行代码对服务器进行攻击的方法。

PreparedStatement
·PreparedStatement作用:
1. 预编译SQL并执行SQL语句
1、获取 PreparedStatement 对象
// SQL语句中的参数值，使用?占位符替代
String sql = "select * from user where username = ? and password = ?";
// 通过Connection对象获取，并传入对应的sql语句
PreparedStatement pstmt =conn.prepareStatement(sql);
1. 设置参数值
PreparedStatement对象:setXxx(参数1，参数2):给 ?赋值
Xxx: 数据类型;如setint(参数1，参数2)参数:
参数1: ? 的位置编号，从1开始	
参数2:?的值
1. 执行SQL
executeUpdate(); / executeQuery();:不需要再传递sql

使用方法：

//2.获取连接:如果连接的是本机mysql并且端口是默认的3306可以简化书写 String url = "jdbc:mysql:///db1?useSSL=false"; String username = "root"; String password = "1234";
Connection conn = DriverManager.getConnection(url, username, password);

//接收用户输入用户名和密码 String name = "zhangsan"; String pwd = "fhsjkfhdsk";

// 定义sql
String sql = "select * from tb_user where username = ? and password = ?";

// 获取pstmt对象
PreparedStatement pstmt = conn.prepareStatement(sql);

设置?的值
pstmt.setString( parameterlndex:1, name); pstmt.setString( parameterlndex:2,pwd);

// 执行sql
ResultSet	rs	=	pstmt.executeQuery();

## 数据库连接池实现

标准接口:DataSource
官方(SUN) 提供的数据库连接池标准接口，由第三方组织实现此接口。功能:获取连接
Connection getConnection()
常见的数据库连接池:
DBCP·C3PO

Druid
·Druid(德鲁伊)
· Druid连接池是阿里巴巴开源的数据库连接池项目
·功能强大，性能优秀，是Java语言最好的数据库连接池之一



##### Driud使用步骤

1.导入jar包 druid-1.1.12.jar
2.定义配置文件

3. 加载配置文件
4.获取数据库连接池对象
5.获取连接



driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql:///db1?useSSL=false&useServerPrepStmts=true username=root
ssword=1234

初始化连接数量 initialSize=5--

#最大连接数 maxActive=10--

#最大等待时间 maxWait=3000

使用方式：

//1.导入jar包
//2.定义配置文件
//3.加载配置文件
Properties prop = new Properties();
prop.load(new FileInputStream( name:"jdbc-demo/src/druid.properties"));

//4.获取连接池对象
DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

//5.获取数据库连接Connection
Connection connection = dataSource.getConnection();
System.out.println(connection);

