package com.gtzhou.blog.utils;

import com.gtzhou.blog.domain.Role;
import com.gtzhou.blog.domain.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate2;


    @Test
    public void testStringRedisTemplate(){
        
        //判断redis中是否存在key
        Boolean aBoolean = redisTemplate.hasKey("gtzhou-api");
        if (RedisUtils.unbox(aBoolean)){
            ValueOperations<String, String> stringValue = redisTemplate.opsForValue();
            System.out.println(stringValue.get("gtzhou-db"));
            String s = redisTemplate.opsForValue().get("gtzhou-api");
            System.out.println(s);
            redisTemplate.opsForValue().set("gtzhou-redis","test redis");
        } else {
            System.out.println("key not exit");
        }
    }

    @Test
    public void testList(){
        List<Role> list = new ArrayList<>();
        Role role1 = new Role();
        role1.setRoleName("role_name1");
        role1.setDescription("role1");
        Role role2 = new Role();
        role2.setRoleName("role_name2");
        role2.setDescription("role2");
        list.add(role1);
        list.add(role2);
        Role role3 = new Role();
        role3.setRoleName("role_name3");
        role3.setDescription("role3");
        list.add(0,role3);
        System.out.println(list);
    }


    @Test
    public void testRedisTemplate(){
        redisTemplate2.opsForValue().set("gtzhou-api","30s time out",30,TimeUnit.SECONDS);
    }

    public static boolean unbox(Boolean aBoolean){
        return aBoolean;
    }

}
