package com.gtzhou.blog.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "请求成功");

    private String code;
    private String msg;
}
