package com.gtzhou.blog.user;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gtzhou.blog.dao.UserRoleMapper;
import com.gtzhou.blog.domain.User;
import com.gtzhou.blog.domain.UserRole;
import com.gtzhou.blog.response.BaseResp;
import com.gtzhou.blog.response.PageRespData;
import com.gtzhou.blog.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Test
    public void testUserAdd() {
        User user = new User();
        String password = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passEncode = encoder.encode(password);
        user.setUsername("jocker4");
        user.setPassword(passEncode);
        user.setPhone("1763228861");
        user.setEmail("zhangsan@163.com");
        user.setGender(1);
        user.setStatus(1);
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            return;
        }
        userService.saveOrUpdate(user);
    }

    @Test
    public void testUserGetById() {
//        System.out.println("==========="+userService.getUserById(1L));
        System.out.println("=============" + userService.getUserByIdTK(1).get(0));
    }

    @Test
    public void testSelectAll() {
        System.out.println(userService.getAll());
    }

    @Test
    public void testPage(){
        List<User> list = userService.getAll();
        PageRespData<User> pageData = PageRespData.getPageData((Page<User>) list,new PageRespData());
        pageData.setList(list);
        BaseResp.success(pageData);
        Assert.assertEquals(6,pageData.getTotal());
    }

    @Test
    public void testBCrypt() {
        String pass = "123456";
        BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
        String encode = bcp.encode(pass);
        System.out.println(encode);
    }

    @Test
    public void testUpdate(){
        UserRole ur = new UserRole();
        ur.setUserId(4);
        ur.setUpdatedBy("gtzhou");
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", 4);
        userRoleMapper.updateByExampleSelective(ur,example);
    }


}
