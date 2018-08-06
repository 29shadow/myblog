package com.gtzhou.blog.dao;

import com.gtzhou.blog.domain.Menu;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
public interface MenuMapper {

    List<Menu> selectAll();

    List<Menu> getMenusByUserId();

}
