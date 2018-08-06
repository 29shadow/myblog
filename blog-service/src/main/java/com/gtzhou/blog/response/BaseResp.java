package com.gtzhou.blog.response;

import lombok.Data;

@Data
public class BaseResp {

    private boolean success;

    private String code;

    private String msg;

    private BaseData data;

    public static class BaseData {

    }

    public BaseResp(boolean success, ResponseCode response) {
        this.success = success;
        this.code = response.getCode();
        this.msg = response.getMsg();
    }

    public BaseResp(boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public static BaseResp success(BaseData data) {
        BaseResp resp = new BaseResp(true, ResponseCode.SUCCESS);
        resp.setData(data);
        return resp;
    }

    public static BaseResp fail(ResponseCode response) {
        return new BaseResp(false, response);
    }

    public static BaseResp fail(String code, String msg) {
        return new BaseResp(false, code, msg);
    }
}
