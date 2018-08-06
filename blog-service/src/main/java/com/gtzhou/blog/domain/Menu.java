package com.gtzhou.blog.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/6/30
 */
@Data
public class Menu {

    private Integer menuId;

    private String url;

    private String path;

    private String menuName;

    private String iconCls;

    private Integer parentId;

    private Integer sort;

    private Integer status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private List<Role> roles;

    private List<Menu> children;

    public void addChild(Menu menu){
        if (children==null){
            children = new ArrayList<>();
        }
        children.add(menu);
    }
}
