package com.gtzhou.blog.dao;

import com.gtzhou.blog.domain.Role;

import java.util.List;

public interface RoleMapper{

    List<Role> getRolesByUserId(Integer userId);

}
