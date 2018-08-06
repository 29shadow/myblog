package com.gtzhou.blog.service.impl;

import com.gtzhou.blog.dao.RoleMapper;
import com.gtzhou.blog.domain.Role;
import com.gtzhou.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
