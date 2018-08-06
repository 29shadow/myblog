package com.gtzhou.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gtzhou.blog.filter.*;
import com.gtzhou.blog.service.UserService;
import com.gtzhou.blog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Autowired
    private UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html","/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //http.addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        })
                .antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("{\"status\":\"error\",\"msg\":\"");
                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                    sb.append("用户名或密码输入错误，登录失败!");
                } else if (e instanceof DisabledException) {
                    sb.append("账户被禁用，登录失败，请联系管理员!");
                } else {
                    sb.append("登录失败!");
                }
                sb.append("\"}");
                out.write(sb.toString());
                out.flush();
                out.close();

            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String userName = "";
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    userName = ((UserDetails) principal).getUsername();
                } else {
                    userName = principal.toString();
                }
                System.out.println("登录成功："+userName);

                String redisKey = JwtUtils.redisKey(userName);
                String token = JwtUtils.generatorToken(userName);
                redisTemplate.opsForValue().set(redisKey,token,JwtUtils.TOKEN_CACHE_EXPIRATION_MINI,TimeUnit.MINUTES);
                System.out.println("缓存token："+redisTemplate.opsForValue().get(redisKey));
//                response.setHeader(JwtUtils.HEADER_STRING,token);
//                response.setStatus(HttpStatus.OK.value());
                System.out.println(token);

                response.setContentType("application/json;charset=utf-8");

                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNodeFactory factory = JsonNodeFactory.instance;
                ObjectNode objectNode = factory.objectNode();
                objectNode.set("status",factory.textNode("success"));
                objectNode.set("msg",factory.textNode(""));
                objectNode.set("token",factory.textNode(token));

//                String s = "{\"status\":\"success\",\"msg\":" + objectMapper.writeValueAsString("") + "}";

                out.write(objectNode.toString());
                out.flush();
                out.close();
            }
        }).permitAll().and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);

    }

    //基于内存方式
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("gtzhou")
//                .password(new BCryptPasswordEncoder().encode("12345"))
//                .roles("USER","ADMIN");
////        auth.inMemoryAuthentication().withUser("gtzhou").password("12345").roles("USER");
//    }

    //基于数据库方式


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 自定义 QQ登录 过滤器
     */
    private QQAuthenticationFilter qqAuthenticationFilter() {
        QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/login/qq");
        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());
        return authenticationFilter;
    }
}
