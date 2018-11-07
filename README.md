# 机灵报表（SmartReport）- MySQL
机灵报表（SmartReport）是基于Birt 4.6开发的一款报表应用管理平台，机灵报表具有如下特点：
- 项目管理，机灵报表中的所有报表都是以项目模式进行管理的，只有项目组中的成员可以互相查看对方创建的报表。
- 数据源管理，通用的数据源管理方式，可以让用户快速在测试环境与生产环境之间进行切换。
- 定时任务，根据报表模板定时生成各种格式的日报、周报、月报等。
- 支持EXCEL/PDF/WORD等多种格式下载、打印和导出。
- 支持对报表数据集进行修改，可随意篡改报表数据并保留痕迹。

## 开发环境说明

由于Birt 4.6的很多依赖库MAVEN仓库中没有，所以有些JAR包必须手动下载。同时由于各种库不同版本之间可能有冲突，建议大家采用如下的库版本进行开发。

- Birt 4.6
- Jdk 1.8.0
- Tomcat 9.0
- Spring 4.3.16
- Hibernate 5.2.11
- Connetion Pool C3p0 0.9
- quartz 2.3
- MySQL 8

## 使用说明

### MySQL8.X版本

- 更换数据源，
打开 src/main/resources/db.properties 文件，切换成自己的数据库连接环境。
- 创建表结构
启动项目，Hibernate会自动创建所有的表结构。
将v_user_role_resources表删除，使用 src/main/resources/mysql.data.sql中的视图脚本创建视图
将 src/main/resources/mysql.data.sql中的初始化语句导入数据库中
重新启动项目


