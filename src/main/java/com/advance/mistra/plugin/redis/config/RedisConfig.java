package com.advance.mistra.plugin.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author mistra@future.com
 * @date 2022/2/28
 */
@Configuration
public class RedisConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;

    public RedisConfig() {
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(this.minIdle);
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        log.info("----------JedisPool init success----------");
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, this.host, this.port, this.timeout, this.password, this.database, (String) null);
        return jedisPool;
    }

    @Bean
    @ConditionalOnProperty(
            name = {"spring.redis.connection-factory.enabled"}
    )
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        log.info("----------JedisConnectionFactory init success----------");
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(this.port);
        configuration.setDatabase(this.database);
        if (StringUtils.isNotBlank(this.password)) {
            configuration.setPassword(this.password);
        }

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = ((SingleServerConfig) config.useSingleServer().setAddress("redis://" + this.host + ":" + this.port).setConnectionMinimumIdleSize(this.minIdle).setConnectionPoolSize(this.maxIdle).setTimeout(this.timeout)).setDatabase(this.database).setDnsMonitoringInterval(-1L);
        config.setCodec(new StringCodec());
        if (StringUtils.isNotBlank(this.password)) {
            serverConfig.setPassword(this.password);
        }

        return Redisson.create(config);
    }
}
