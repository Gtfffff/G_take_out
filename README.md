# G_take_out

#### 介绍
基于分布式的外卖平台试作,目前时间以及技术上的原因仅完成登录框架部分

项目对 Spring Security 做了较深的适配，授权服务器采用 RBAC 的方式构造权限模型，修改了 Token 的 Payload 字段以及RefreshToken 的分发规则，拓展了认证手段及相关支持，支持社交登录，资源服务器采用微服务授权 + 方法级授权的方式 + 非对称加密的方式实现 Token 验证，利用统一异常处理机制实现 Token 静默刷新

- 基于 Spring Boot、Spring Cloud 进行开发
  - SSMP 和 Nacos、Gateway、Feign
- 使用 Spring Security 及其 Oauth 相关组件完成授权认证开发
- 使用 J2cache 作为缓存框架，其中 Caffeine 为一级缓存实现，Redis 为二级缓存实现，使用 Mysql 作为数据库实现
- 使用 Spring Session 完成 Session 的 Redis 管理， Logback 作为日志实现，Validator 作为数据校验实现



#### 工程结构
~~~
g_take_out
├── g-app -- 系统外部服务
├    ├── g-admin -- 用户管理模块
├    ├── g-customer -- 消费者模块
├── g-commons -- 通用包
├── g-service -- 系统内部服务
├    ├── g-auth -- 授权服务器
├    ├── g-gateway -- 网关
├── g-tools -- 工具模块
├    ├── g-feign-api -- feign 模块
├    ├── g-log -- 日志模块
├    ├── g-oauth-properties -- oauth 相关模块
├    ├── g-security -- 资源服务器模块
├    ├── g-session -- session 管理模块
~~~

### 待解决问题

- 当授权请求经由网关转发后导致request不一致，requestcache无法取出的问题，由于该问题的存在，目前授权请求需要直接访问授权服务器才能正常进行后续流程

### 账户密码

- 用户：admin，密码：admin
