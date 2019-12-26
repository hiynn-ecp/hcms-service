### hcms
![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.0.6-yellow.svg?style=flat-square](https://img.shields.io/badge/springboot-2.0.6-yellow.svg?style=flat-square)
![https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square)

hcsm是一款简单高效的后台管理系统，使用Spring Boot和Vue构建。相信无论作为企业级应用，私活开发脚手架或者后台管理系统构建学习，hscm都会是一个不错的选择。


账号和密码：

账号 | 密码| 权限
---|---|---
admin | 1111 | 超级管理员

### 系统模块
系统功能模块组成如下所示：
```
├─平台产品
├─工作平台
├─系统工具
│  ├─代码生成器
│  └─静态资源管理
├─文章资料
│  ├─开发规范
│  ├─开发软件
│  ├─技术分享
│  ├─共享空间
│  └─系统信息
├─枚举管理
├─部门成员
├─日志管理
├─用户管理
├─角色管理
└─菜单管理

```
### 系统特点

1. 前后端请求参数校验

2. 支持多数据源，代码生成

3. 支持静态资源一键备份和还原

4. 支持文章资料管理

5. 用户权限动态刷新

6. 浏览器兼容性好

7. 代码简单，结构清晰

### 技术选型

#### 后端
- [Spring Boot 2.0.6](http://spring.io/projects/spring-boot/)
- [Mybatis 1.3.2](https://mybatis.org/mybatis-3/)
- [MySQL 5.7.x](https://dev.mysql.com/downloads/mysql/5.7.html#downloads),[Hikari](https://brettwooldridge.github.io/HikariCP/),[Redis](https://redis.io/)
- [Shiro 1.4.0](http://shiro.apache.org/)


### 浏览器兼容
|[<img src="https://raw.github.com/alrra/browser-logos/master/src/archive/internet-explorer_9-11/internet-explorer_9-11_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |[<img src="https://raw.github.com/alrra/browser-logos/master/src/opera/opera_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Opera
| --------- | --------- | --------- | --------- | --------- |--------- |
|IE 10+| Edge| last 15 versions| last 15 versions| last 10 versions| last 15 versions

