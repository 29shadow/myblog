package com.gtzhou.blog.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 可以通过实现接口中的方法来实现
 * 账户锁定、
 * 密码过期、
 * 账户登录次数、
 * 账户是否禁用
 * 等功能
 */
@Data
public class User implements UserDetails {

    private Integer userId;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer gender;

    private Integer status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private List<Role> roles;

    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }

    /**
     * 用户当前角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            if (!Objects.isNull(r.getRoleName())){
                authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                System.out.println(r.getRoleName());
            }
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
