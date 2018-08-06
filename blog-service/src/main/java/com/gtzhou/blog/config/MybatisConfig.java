package com.gtzhou.blog.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {

    @Bean
    public PageHelper pageHelper() {
        System.out.println("============pagehelper===");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("helperDialect", "mysql");
        p.setProperty("reasonable", "true");
        p.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
