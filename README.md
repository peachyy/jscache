# jscache

[![Build Status](https://travis-ci.org/peachyy/jscache.svg?branch=master)](https://travis-ci.org/peachyy/jscache)



jscache is a java simple cache framework.

一个轮子 Java 简单的缓存工具(cache)，支持本地缓存与远程调用(RPC)缓存保持共用一套规范，基于Spring AOP、Spring El

# Spring Cache 不爽的几点
1、项目中之前使用的是`Spring Cache` 用`CacheManager`来管理生成`Cache` 有的同学觉得比如在缓存的时候加自定义的过期时间觉得麻烦了，当然由于设计架构的原因必须新建一个`CacheManager`来解决，这种设计本身OK的，而且对于缓存管理来说也是正确的。
  
 2、后来项目引入了`Dubbo` `Spring Cloud` `grpc`远程调用的时候 希望在`consumer`端(接口调用方) 如果发现缓存 原则上来说就无需再发起网络请求远端接口，而是直接通过缓存获取数据。这种不存在远程访问 效率会高很多，当然了 这种情况要和之前的缓存模式规范保持一致 统一管理和维护 `接口AOP在boot1.x有点问题`
 
 3、一个方法要删除或者设置多个缓存的时候 `Spring Cache` 需要用`Caching`注解来包装，用起来有点麻烦 为嘛不能直接写多个`CacheEvict`
索性就放弃 `Spring Cache` 动手写一个简单的缓存工具 `jscache` 就这样存在了。

 4、缓存监控 统计 比如我想看缓存命中情况 等。  
 
 5、其他
 
 索性就放弃 `Spring Cache` 动手写一个简单的缓存工具 
 
 支持设置自定义过期时间ttl `虽然这可能有点不好，但还是提供了`    

# 模块
jscache-annotation 原注解信息 

jscache-core       实现

jscache-dubbo      dubbo适配 `如果dubbo bean 是被spring管理的 那么不需要此模块也能被AOP到，没有被管理的情况下 加入此模块 缓存注解写到接口上 就支持适配` 

jscache-grpc       grpc适配 `规划中`
 
jscache-springcloud springcloud适配 `规划中`

# 监控
 
 待完善 目前规划是 `Micrometer` 



# API

默认使用的是`fastJson`进行序列化 内部还实现了`jackJson` `java` 支持自定义实现。

启用缓存 使用`@EnableCache`注解引入功能支持

`@Cacheable`

| 属性                | 描述               |test   |
| ------------------ | ------------------ |-------|
| prefix             | 前缀               |前缀   |
| key                | key spring el标识  |     |
| ttl                | 缓存存活时间 默认-1 不过期            |           |
| argCondition       | 参数条件 el 为true会执行此注解 false 则过滤            |          |
| returnCondition    | 返回值条件el  为true则执行 false 过滤  变量#result表示返回值   |             |
| allowNullValue    |  允许空值写入缓存 默认为false  可防止缓存穿透     |          |

```
@SpringBootApplication
@EnableCache
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
 *注解中的key是`el`表达式 建议使用 p0 p1 p2的方式，这样就支持标注在接口方法上。*

@Cacheable 有缓存则获取缓存 没有则调用服务后再缓存
```
  @Cacheable(prefix = "user",key = "#p0")
  //@Cacheable(prefix = "user2",key = "#p0",ttl=3600)
  public User getUserById(Integer userId){
    ....
  }
```

`@CachePut` 总是添加缓存

| 属性                | 描述               |test   |
| ------------------ | ------------------ |-------|
| prefix             | 前缀               |前缀   |
| key                | key spring el标识  |     |
| ttl                | 缓存存活时间 默认-1 不过期            |           |
| argCondition       | 参数条件 el 为true会执行此注解 false 则过滤            |          |
| returnCondition    | 返回值条件el  为true则执行 false 过滤  变量#result表示返回值   |             |
| allowNullValue    |  允许空值写入缓存 默认为false  可防止缓存穿透     |          |


```
  @CachePut(prefix = "user",key = "#p0")
  @CachePut(prefix = "userTemp",key = "#p0")//支持多个
  public User updateUserById(Integer userId){
    ....
  }
```


`@CacheEvict`  移除缓存

| 属性                | 描述               |test   |
| ------------------ | ------------------ |-------|
| prefix             | 前缀               |前缀   |
| key                | key spring el标识  |     |
| argCondition       | 参数条件 el 为true会执行此注解 false 则过滤            |          |


```
  @CacheEvict(prefix = "user",key = "#p0")
  public void delete(Integer userId){
    ....
  }
```


支持的RPC框架

* dubbo  未实现
* spring cloud   未实现
* grpc   未实现