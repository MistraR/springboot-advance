# springboot-advance
### 作者：Mistra丶 
个人积累的代码工具集，设计方案，第三方中间件集成，JUC等使用姿势。
 
 ---
 
 ### 系统基础：
 * com.advance.mistra.common.exception：统一异常拦截AOP，并结合了语言国际化
 * com.advance.mistra.common.web.MistraDispatcherServlet,i18n，InternationalizationUtil：语言国际化
 * com.advance.mistra.common.response：基于注解的统一返回值封装AOP
 * com.advance.mistra.common.log：系统请求唯一日志追踪Id
 * com.advance.mistra.config.api：Swagger+Knife4j接口文档API
 * com.advance.mistra.config.filter：过滤器
 * com.advance.mistra.dao.jpa：JPA多表级联查询
 * com.advance.mistra.utils：常见工具集(EasyExcel，文件，日期，Https，MD5，SpringUtil等等)
 
 ### 中间件集成：
 * com.advance.mistra.plugin.redis.RedisUtil：集成Redis
 * com.advance.mistra.plugin.redis.ReentrantLockUtil：集成Redisson 
 * com.advance.mistra.config.MybatisPlusConfig：集成Mybatis-plus
 * com.advance.mistra.plugin.esfreemakerversion：集成ElasticSearch，基于Freemaker拼接查询条件
 * com.advance.mistra.plugin.esannotationversion：集成ElasticSearch，基于注解实现自动拼接查询条件
 * com.advance.mistra.plugin.kafka：集成Kafka
 

 ### Code
 * com.advance.mistra.solution.producerconsumer：生产者消费者模型
 * com.advance.mistra.test.juc：*JUC* 各种并发工具使用姿势
   * 锁，并发集合，并发队列，线程池，原子类，CAS，AQS
 
