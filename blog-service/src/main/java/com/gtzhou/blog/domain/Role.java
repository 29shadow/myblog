package com.gtzhou.blog.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role{

    private Integer roleId;

    private String roleName;

    private String description;

    private Integer status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;
}
