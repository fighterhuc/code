package com.huc.util;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Data
public class Page<T> {
    //页码
    private Integer pageNum;
    //每页的条数
    private Integer pageSize;
    //总条数
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //返回的结果数据
    private List<T> list;
    public static Page get(HttpServletRequest request){
        Page page = new Page();
        page.setPageNum(request.getParameter("page")!=null?Integer.parseInt(request.getParameter("page")):1);
        page.setPageSize(request.getParameter("pageSize")!=null?Integer.parseInt(request.getParameter("pageSize")):20);
        return page;
    }
}
