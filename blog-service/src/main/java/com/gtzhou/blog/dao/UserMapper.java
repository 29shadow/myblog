package com.gtzhou.blog.dao;

import com.gtzhou.blog.domain.Role;
import com.gtzhou.blog.domain.User;


import java.util.List;

//@Repository
public interface UserMapper {

    //    @Select("select * from user where id=#{id}")
    User getUserById(Integer userId);

    //    @Insert("insert into user (id,user_name,password,email,gender,phone) values (#{id},#{userName},#{password},#{email},#{gender},#{phone})")
    void save(User user);

    void updateUserId(User user);

    void deleteUserById(Integer userId);

    List<User> getAll();

    User loadUserByUsername(String userName);


    List<User> getUserByPage();
//    List<Role> getRolesByUserId(Integer userId);

    List<Role> getRolesByUserId(Integer userId);


}
