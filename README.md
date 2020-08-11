# pcache
一个开箱急即用的Java 缓存工具(cache)，支持本地缓存与远程调用(RPC)缓存保持共用一套规范，基于Spring AOP、Spring El

# Spring Cache 不爽的几点
1、项目中之前使用的是`Spring Cache` 用`CacheManager`来管理生成`Cache` 有的同学觉得比如在缓存的时候加自定义的过期时间觉得麻烦了，当然由于设计架构的原因必须新建一个`CacheManager`来解决，这种设计本身OK的，而且对于缓存管理来说也是正确的。
  
 2、后来项目引入了`Dubbo` `Spring Cloud` `grpc`远程调用的时候 希望在`consumer`端(接口调用方) 如果发现缓存 原则上来说就无需再发起网络请求远端接口，而是直接通过缓存获取数据。这种不存在远程访问 效率会高很多，当然了 这种情况要和之前的缓存模式规范保持一致 统一管理和维护 `接口AOP在boot1.x有点问题`
 
 3、一个方法要删除或者设置多个缓存的时候 `Spring Cache` 需要用`Caching`注解来包装，用起来有点麻烦 为嘛不能直接写多个`CacheEvict`
索性就放弃 `Spring Cache` 动手写一个简单的缓存工具 `pcache` 就这样存在了。
 4、缓存监控 统计 比如我想看缓存命中情况 等。  
 5、其他
 
 索性就放弃 `Spring Cache` 动手写一个简单的缓存工具
   



#feature
* 支持设置自定义过期时间ttl `虽然这可能有点不好，但还是提供了` 
* 
扩展缓存

内置缓存

示例

问题

支持的RPC框架

* dubbo  未实现
* spring cloud   未实现
* grpc   未实现