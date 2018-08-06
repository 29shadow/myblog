package com.gtzhou.blog.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/6
 */
//@Configuration
//@EnableCaching
public class RedisConfig {

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate){
//        return new RedisCacheManager(redisTemplate);
//    }
}
