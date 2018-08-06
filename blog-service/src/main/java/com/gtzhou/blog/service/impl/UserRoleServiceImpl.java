package com.gtzhou.blog.service.impl;

import com.gtzhou.blog.dao.UserRoleMapper;
import com.gtzhou.blog.domain.UserRole;
import com.gtzhou.blog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/15
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void deleteByUserId(Integer userId) {
        userRoleMapper.deleteByPrimaryKey(new UserRole(){{
            setId(userId);
        }});
    }
}
