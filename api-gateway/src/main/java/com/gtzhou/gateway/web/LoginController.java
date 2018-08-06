package com.gtzhou.gateway.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/4
 */
@Slf4j
@Controller
public class LoginController {

    @RequestMapping("/login")
    public void  login(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        response.setStatus(200);
        request.getRequestDispatcher("/blog/login").forward(request,response);
        System.out.println("--");
    }
}
