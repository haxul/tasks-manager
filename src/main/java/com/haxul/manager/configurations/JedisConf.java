package com.haxul.manager.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConf {

    @Bean
    public Jedis jedisClient() {
        Jedis jedis = new Jedis("redis://localhost:6379");
        jedis.auth("redis_password");
        return jedis;
    }
}
