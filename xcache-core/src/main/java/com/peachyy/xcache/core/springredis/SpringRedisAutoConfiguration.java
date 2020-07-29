//package com.peachyy.xcache.core.springredis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author Xs.Tao
// */
//@Configuration
//@Slf4j
//public class SpringRedisAutoConfiguration {
//
//    private final static int MAX_ATTEMPTS = 3;
//
//    private final static int CONNECT_TIMEOUT = 5000;
//
//    @Primary
//    @Bean("j2CahceRedisConnectionFactory")
//    @ConditionalOnMissingBean(name = "j2CahceRedisConnectionFactory")
//    @ConditionalOnProperty(name = "j2cache.redis-client", havingValue = "lettuce")
//    public LettuceConnectionFactory lettuceConnectionFactory(net.oschina.j2cache.J2CacheConfig j2CacheConfig) {
//        Properties l2CacheProperties = j2CacheConfig.getL2CacheProperties();
//        String hosts = l2CacheProperties.getProperty("hosts");
//        String mode = l2CacheProperties.getProperty("mode") == null ? "null" : l2CacheProperties.getProperty("mode");
//        String clusterName = l2CacheProperties.getProperty("cluster_name");
//        String password = l2CacheProperties.getProperty("password");
//        int database = l2CacheProperties.getProperty("database") == null ? 0
//                : Integer.parseInt(l2CacheProperties.getProperty("database"));
//        LettuceConnectionFactory connectionFactory = null;
//        LettucePoolingClientConfigurationBuilder config = LettucePoolingClientConfiguration.builder();
//        config.commandTimeout(Duration.ofMillis(CONNECT_TIMEOUT));
//        config.poolConfig(getGenericRedisPool(l2CacheProperties, null));
//        List<RedisNode> nodes = new ArrayList<>();
//        if (hosts != null && !"".equals(hosts)) {
//            for (String node : hosts.split(",")) {
//                String[] s = node.split(":");
//                String host = s[0];
//                int port = (s.length > 1) ? Integer.parseInt(s[1]) : 6379;
//                RedisNode n = new RedisNode(host, port);
//                nodes.add(n);
//            }
//        } else {
//            log.error("j2cache中的redis配置缺少hosts！！");
//            throw new IllegalArgumentException();
//        }
//        RedisPassword paw = RedisPassword.none();
//        if (!StringUtils.isEmpty(password)) {
//            paw = RedisPassword.of(password);
//        }
//        switch (mode) {
//            case "sentinel":
//                RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration();
//                sentinel.setDatabase(database);
//                sentinel.setPassword(paw);
//                sentinel.setMaster(clusterName);
//                sentinel.setSentinels(nodes);
//                connectionFactory = new LettuceConnectionFactory(sentinel, config.build());
//                break;
//            case "cluster":
//                RedisClusterConfiguration cluster = new RedisClusterConfiguration();
//                cluster.setClusterNodes(nodes);
//                cluster.setMaxRedirects(MAX_ATTEMPTS);
//                cluster.setPassword(paw);
//                connectionFactory = new LettuceConnectionFactory(cluster, config.build());
//                break;
//            case "sharded":
//                throw new IllegalArgumentException("Lettuce not support use mode [sharded]!!");
//            default:
//                for (RedisNode node : nodes) {
//                    String host = node.getHost();
//                    int port = node.getPort();
//                    RedisStandaloneConfiguration single = new RedisStandaloneConfiguration(host, port);
//                    single.setDatabase(database);
//                    single.setPassword(paw);
//                    connectionFactory = new LettuceConnectionFactory(single, config.build());
//                    break;
//                }
//                if (!"single".equalsIgnoreCase(mode))
//                    log.warn("Redis mode [" + mode + "] not defined. Using 'single'.");
//                break;
//        }
//        return connectionFactory;
//    }
//
//    @Bean("j2CacheRedisTemplate")
//    public RedisTemplate<String, Serializable> j2CacheRedisTemplate(
//            @Qualifier("j2CahceRedisConnectionFactory") RedisConnectionFactory j2CahceRedisConnectionFactory,
//            @Qualifier("j2CacheValueSerializer") RedisSerializer<Object> j2CacheSerializer) {
//        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setDefaultSerializer(j2CacheSerializer);
//        template.setConnectionFactory(j2CahceRedisConnectionFactory);
//        return template;
//    }
//
//    @Bean("j2CacheValueSerializer")
//    @ConditionalOnMissingBean(name = "j2CacheValueSerializer")
//    public RedisSerializer<Object> j2CacheValueSerializer() {
//        return new J2CacheSerializer();
//    }
//
//    @Bean("j2CacheRedisMessageListenerContainer")
//    RedisMessageListenerContainer container(
//            @Qualifier("j2CahceRedisConnectionFactory") RedisConnectionFactory j2CahceRedisConnectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(j2CahceRedisConnectionFactory);
//        return container;
//    }
//
//    private GenericObjectPoolConfig getGenericRedisPool(Properties props, String prefix) {
//        GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
//        cfg.setMaxTotal(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxTotal"), "-1")));
//        cfg.setMaxIdle(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxIdle"), "100")));
//        cfg.setMaxWaitMillis(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxWaitMillis"), "100")));
//        cfg.setMinEvictableIdleTimeMillis(
//                Integer.valueOf((String) props.getOrDefault(key(prefix, "minEvictableIdleTimeMillis"), "864000000")));
//        cfg.setMinIdle(Integer.valueOf((String) props.getOrDefault(key(prefix, "minIdle"), "10")));
//        cfg.setNumTestsPerEvictionRun(
//                Integer.valueOf((String) props.getOrDefault(key(prefix, "numTestsPerEvictionRun"), "10")));
//        cfg.setLifo(Boolean.valueOf(props.getProperty(key(prefix, "lifo"), "false")));
//        cfg.setSoftMinEvictableIdleTimeMillis(
//                Integer.valueOf((String) props.getOrDefault(key(prefix, "softMinEvictableIdleTimeMillis"), "10")));
//        cfg.setTestOnBorrow(Boolean.valueOf(props.getProperty(key(prefix, "testOnBorrow"), "true")));
//        cfg.setTestOnReturn(Boolean.valueOf(props.getProperty(key(prefix, "testOnReturn"), "false")));
//        cfg.setTestWhileIdle(Boolean.valueOf(props.getProperty(key(prefix, "testWhileIdle"), "true")));
//        cfg.setTimeBetweenEvictionRunsMillis(
//                Integer.valueOf((String) props.getOrDefault(key(prefix, "timeBetweenEvictionRunsMillis"), "300000")));
//        cfg.setBlockWhenExhausted(Boolean.valueOf(props.getProperty(key(prefix, "blockWhenExhausted"), "false")));
//        return cfg;
//    }
//
//    private String key(String prefix, String key) {
//        return (prefix == null) ? key : prefix + "." + key;
//    }
//}
