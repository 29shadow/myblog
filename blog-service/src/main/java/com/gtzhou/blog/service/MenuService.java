package com.gtzhou.blog.service;

import com.gtzhou.blog.domain.Menu;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
public interface MenuService {
    List<Menu> getAllMenus();

    List<Menu> getMenusByUserId();
}
