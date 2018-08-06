package com.gtzhou.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
@Controller
public class RegLoginController {

    @RequestMapping("/login_p")
    public String login(){
        return "index";
    }
}
