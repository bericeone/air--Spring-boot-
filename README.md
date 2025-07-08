# air--Spring-boot-
主要用于一下功能测试，整体很简陋但是也方便升级；
1. 后端技术
Spring Boot
用于快速搭建Java Web应用，简化配置，集成各种Spring生态组件。
Spring MVC
实现Web层的请求分发、参数绑定、表单校验等。
MyBatis
作为ORM框架，实现Java对象与数据库表的映射，SQL语句灵活可控。
MyBatis-Plus（如有）
MyBatis的增强工具，简化CRUD操作（如未用可忽略）。
AOP（面向切面编程）
用于日志记录（LogAspect），统一拦截service层方法，记录操作日志。
Spring事务管理
通过@EnableTransactionManagement和@Transactional保证数据一致性。
2. 数据库相关
MySQL
作为主要的关系型数据库，存储航班、管理员等数据。
JDBC/HikariCP
Spring Boot默认集成HikariCP作为数据库连接池。
3. 前端技术
Thymeleaf
作为模板引擎，负责渲染HTML页面，实现前后端数据绑定。
HTML5 + CSS + JavaScript
页面结构、样式和交互，表单提交、图片上传等。
4. 文件上传与静态资源
Spring MultipartFile
实现图片上传功能，图片保存到static/uploads目录。
静态资源映射
通过Spring Boot自动映射/static目录下的资源。
5. 安全与登录控制
Session管理
登录成功后将用户信息存入Session，实现会话控制。
自定义拦截器（LoginInterceptor）
拦截未登录用户，强制跳转到登录页。
6. 配置与管理
application.yml
统一管理数据库、端口、日志、文件上传等配置。
7. 其他
Maven
作为项目构建和依赖管理工具。
SLF4J + Logback
日志记录，便于排查问题。
总结一句话：
本项目是基于Spring Boot + MyBatis + Thymeleaf的分层Java Web系统，涵盖了数据库操作、AOP日志、图片上传、登录安全、前后端数据绑定等完整功能。
