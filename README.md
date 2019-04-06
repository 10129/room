# xy99520
# room
#初始化
mvn process-resources -D skipLiquibaseRun=false -D db.driver=com.mysql.jdbc.Driver -D db.url="jdbc:mysql://localhost:3306/xy99?useUnicode=true&characterEncoding=UTF-8" -D db.user=root -D db.password=root

#tomcat conf/context.xml数据源
<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver" url="jdbc:mysql://39.108.98.36:3306/xy99?useUnicode=true&amp;characterEncoding=UTF-8" name="jdbc/xydb" type="javax.sql.DataSource" username="root" password="root"/>
