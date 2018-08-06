package com.gtzhou.gateway.zuul;

import com.gtzhou.gateway.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/4
 */
@Slf4j
@Component
public class ZuulAuthFilter  extends ZuulFilter {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run(){


        RequestContext context = RequestContext.getCurrentContext();
        try{

            //获取token
            HttpServletRequest request = context.getRequest();
            String url = request.getRequestURL().toString();
            String token = request.getHeader(JwtUtils.HEADER_STRING);
            if (token==null && !isLogin(url)){
                context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                context.setSendZuulResponse(false);
                return null;
            }

            //解析token
            if (!isLogin(url)){
                //解析token的用户
                String user = JwtUtils.parse2User(token);
                if (Objects.isNull(user)){
                    context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                    context.setSendZuulResponse(false);
                    return null;
                }
                context.addZuulRequestHeader("token",token);
                log.info("解析token成功："+user);

                //从缓存中获取token
                String redisKey = JwtUtils.redisKey(user);
                String cacheToken = redisTemplate.opsForValue().get(redisKey);

                if (Objects.isNull(cacheToken)){
                    context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                    context.setSendZuulResponse(false);
                    return null;
                }

//                //比较token
                if (!Objects.equals(token,cacheToken)){
                    context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                    context.setSendZuulResponse(false);
                    return null;
                }
//
//                //延长缓存时间
                redisTemplate.expire(redisKey,JwtUtils.TOKEN_CACHE_EXPIRATION_HOUR,TimeUnit.HOURS);
                //添加请求头
                context.addZuulRequestHeader("userId",user);
            }
            log.info("转发请求：request path is "+ request.getPathInfo() + "或" + request.getServletPath());
        }catch (Exception e){
            log.error(" auth error ", e);
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            return null;
        }
        return null;
    }

    private boolean isLogin(String url){
        if (url.endsWith("login")){
            return true;
        }
        return false;
    }
}
