package com.gtzhou.blog.service;


import com.gtzhou.blog.domain.User;
import com.gtzhou.blog.request.PageReq;
import com.gtzhou.blog.response.PageRespData;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getAll();

    public User getUserById(Integer id);

    void saveOrUpdate(User user);

    void deleteUserById(Integer id);

    List<User> getUserByIdTK(Integer id);

    PageRespData getUserByPage(PageReq req);

//    List<Role> getRolesByUserId(Integer userId);
}
