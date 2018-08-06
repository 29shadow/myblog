package com.gtzhou.blog.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ZGW
 * Date: 2018/7/15
 */
@Data
public class UserRole {

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;
}
