package com.gtzhou.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gtzhou.blog.dao.UserMapper;
import com.gtzhou.blog.dao.UserRoleMapper;
import com.gtzhou.blog.domain.User;
import com.gtzhou.blog.request.PageReq;
import com.gtzhou.blog.response.PageRespData;
import com.gtzhou.blog.service.UserRoleService;
import com.gtzhou.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {


//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<User> getAll() {
//        return userRepository.findAll();
        PageHelper.startPage(1, 5);
//        return userMapper.selectAll();
        List<User> list = userMapper.getAll();
        return list;
    }

    @Override
    public User getUserById(Integer id) {
        // return userRepository.getOne(id);
        return userMapper.getUserById(id);
    }

    @Override
    public void saveOrUpdate(User user) {
        //添加
        Integer userId = user.getUserId();
        if (Objects.isNull(userId)){
            userMapper.save(user);
        }else{
            //修改
            userMapper.updateUserId(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserById(Integer userId) {
        //删除用户角色关系
        userRoleService.deleteByUserId(userId);
        //删除用户信息
        userMapper.deleteUserById(userId);
    }

    @Override
    public List<User> getUserByIdTK(Integer id) {
//        List<User> userList = userMapper.select(new User(){{
////            setUserId(id);
////        }});
////        return userList;
        return null;
    }

    @Override
    public PageRespData getUserByPage(PageReq req) {
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<User> userList = userMapper.getUserByPage();
//        userList.stream().map(User::getRoles)
        PageRespData managerData = PageRespData.getPageData((Page<User>)userList,new PageRespData());
        managerData.setList(userList);
        return managerData;
    }

//    @Override
//    public List<Role> getRolesByUserId(Integer userId) {
//        return userMapper.getRolesByUserId(userId);
//    }

    //实现方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
