package com.gtzhou.blog.response;

import com.github.pagehelper.Page;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageRespData<E> extends BaseResp.BaseData {

    private long    total;
    private int     pages;
    private int     pageSize;
    private int     pageNum;
    private List<E> list;

    public static <E, A extends PageRespData> A getPageData(Page<E> page,A a) {
        a.setTotal(page.getTotal());
        a.setPageNum(page.getPageNum());
        a.setPages(page.getPages());
        a.setPageSize(page.getPageSize());
        return a;
    }
}
