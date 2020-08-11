# pcache
一个轮子 Java 缓存工具(cache)，支持本地缓存与远程调用(RPC)缓存保持共用一套规范，基于Spring AOP、Spring El

# Spring Cache 不爽的几点
1、项目中之前使用的是`Spring Cache` 用`CacheManager`来管理生成`Cache` 有的同学觉得比如在缓存的时候加自定义的过期时间觉得麻烦了，当然由于设计架构的原因必须新建一个`CacheManager`来解决，这种设计本身OK的，而且对于缓存管理来说也是正确的。
  
 2、后来项目引入了`Dubbo` `Spring Cloud` `grpc`远程调用的时候 希望在`consumer`端(接口调用方) 如果发现缓存 原则上来说就无需再发起网络请求远端接口，而是直接通过缓存获取数据。这种不存在远程访问 效率会高很多，当然了 这种情况要和之前的缓存模式规范保持一致 统一管理和维护 `接口AOP在boot1.x有点问题`
 
 3、一个方法要删除或者设置多个缓存的时候 `Spring Cache` 需要用`Caching`注解来包装，用起来有点麻烦 为嘛不能直接写多个`CacheEvict`
索性就放弃 `Spring Cache` 动手写一个简单的缓存工具 `pcache` 就这样存在了。
 4、缓存监控 统计 比如我想看缓存命中情况 等。  
 5、其他
 
 索性就放弃 `Spring Cache` 动手写一个简单的缓存工具 
 
 支持设置自定义过期时间ttl `虽然这可能有点不好，但还是提供了`    

# 模块
pcache-annotation 原注解信息 

pcache-core       实现

pcache-dubbo      dubbo适配 `规划中`

pcache-grpc       pcache-grpc适配 `规划中
 
pcache-springcloud springcloud适配 `规划中

# 监控
 
 待完善 目前规划是 `Micrometer` 

默认使用的是`fastJson`进行序列化 内部还实现了`jackJson` `java` 支持自定义实现。

# 示例

启用缓存 使用`@EnableCache`注解引入功能支持

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

@Cacheable
```
  @Cacheable(prefix = "user",key = "#p0")
  public User getUserById(Integer userId){
    ....
  }
```
@CachePut
```
  @CachePut(prefix = "user",key = "#p0")
  @CachePut(prefix = "userTemp",key = "#p0")//支持多个
  public User updateUserById(Integer userId){
    ....
  }
```
@Cacheable
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