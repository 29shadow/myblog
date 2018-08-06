package com.gtzhou.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/20
 */
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void testRedis(){
        Boolean aBoolean = redisTemplate.hasKey("gtzhou-api");
        if (RedisUtils.unbox(aBoolean)){
            System.out.println("key has exist");
        } else {
            System.out.println("key not exit");
        }
    }

    public static boolean unbox(Boolean aBoolean){
        return aBoolean;
    }
}
