package com.gtzhou.blog.service;

import com.gtzhou.blog.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRolesByUserId(Integer userId);

}
