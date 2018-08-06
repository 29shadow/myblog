package com.gtzhou.blog.controller;

import com.gtzhou.blog.domain.Menu;
import com.gtzhou.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
@RestController
@RequestMapping("/config")
public class ConfigController {


    @Autowired
    MenuService menuService;

    @RequestMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByUserId();
    }
}
