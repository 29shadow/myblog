package com.gtzhou.blog.service.impl;

import com.gtzhou.blog.dao.MenuMapper;
import com.gtzhou.blog.domain.Menu;
import com.gtzhou.blog.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.selectAll();
    }

    @Override
    public List<Menu> getMenusByUserId() {
        return getMenuTree(menuMapper.getMenusByUserId());
    }

    private List<Menu> getMenuTree(List<Menu> menuList) {
        List<Menu> rootMenu = new ArrayList<>();
        for (Menu m:menuList){
            int parentId = m.getParentId();
            if (parentId==0){
                rootMenu.add(m);
                continue;
            }
        }
        for (Menu root :rootMenu){
            root.setChildren(getChildren(root.getMenuId(),menuList));
        }
        return rootMenu;
    }

    private List<Menu> getChildren(Integer rootId,List<Menu> menuList){
        List<Menu> children = new ArrayList<>();
        for (Menu menu:menuList){
            if (menu.getParentId().equals(rootId)){
                children.add(menu);
            }
        }

        for (Menu menu:children){
            menu.setChildren(getChildren(menu.getMenuId(),menuList));
        }

        if (children.size() == 0){
            return null;
        }
        return children;
    }
}
