package com.gtzhou.blog.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.gtzhou.blog.domain.User;
import com.gtzhou.blog.request.PageReq;
import com.gtzhou.blog.response.BaseResp;
import com.gtzhou.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sys/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResp list(PageReq req){
       return BaseResp.success(userService.getUserByPage(req));
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public BaseResp addUser(User user){
        userService.saveOrUpdate(user);
        return BaseResp.success(null);
    }

    @RequestMapping(value = "/getUserByName" , method = RequestMethod.POST)
    public Object getUserByName(String userName){
        return userService.loadUserByUsername(userName);
    }

    @RequestMapping(value = "/deleteByUserId", method = RequestMethod.POST)
    public BaseResp deleteUserById(Integer userId){
        userService.deleteUserById(userId);
        return BaseResp.success(null);
    }


}
