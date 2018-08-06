package com.gtzhou.blog.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class PageReq extends BaseReq {

    @NotNull(message = "分页大小不可为空")
    @Min(value = 1, message = "分页大小最小为1")
    private int pageSize = 10;

    @NotNull(message = "分页页数不可为空")
    @Min(value = 1, message = "分页页数最小为1")
    private int pageNum = 1;
}
