package com.gtzhou.blog.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/4
 */
public class JwtUtils {

    public static final String TOKEN_SECRET = "GtzhouJwt";

    public static final int    TOKEN_EXPIRATION_DAYS = 300;

    public static final String TOKEN_CACHE_NAME = "gateway-token" ;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final int TOKEN_CACHE_EXPIRATION_HOUR = 72;

    public static final int TOKEN_CACHE_EXPIRATION_MINI = 30;

    public static final String HEADER_STRING  = "Authorization";

    /**
     * 解析token
     * @param token
     * @return
     */
    public static String parse2User(String token){
        return Jwts.parser()
                .setSigningKey(JwtUtils.TOKEN_SECRET.getBytes())
                .parseClaimsJws(token.replace(JwtUtils.TOKEN_PREFIX,""))
                .getBody()
                .getSubject();
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String generatorToken(String user){
        LocalDateTime expirationDateTime = LocalDate.now().atStartOfDay().plusHours(3).plusDays(TOKEN_EXPIRATION_DAYS);
//        LocalDateTime expirationDateTime = LocalDate.now().atStartOfDay().plusDays(9).plusMinutes(JwtUtils.TOKEN_CACHE_EXPIRATION_MINI);
        return Jwts.builder().setSubject(user).setExpiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, JwtUtils.TOKEN_SECRET.getBytes())
                .compact();
    }

    /**
     * 获取key
     * @param user
     * @return
     */
    public static String redisKey(String user){
        return TOKEN_CACHE_NAME+"-"+user;
    }

    /**
     *
     * @param token
     * @return
     */
    public static String preToken(String token){
        return TOKEN_PREFIX + token;
    }
}
