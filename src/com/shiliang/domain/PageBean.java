package com.shiliang.domain;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-01 17:48
 * @description JavaBean
 * 封装每一页所需的信息：
 * 1. 当前页内容
 * 2. 当前页码
 * 3. 每页显示条数（固定）
 * 4. 总条数
 * 5. 总页数
 */
public class PageBean<T> {
    //当前的页码
    private Integer currentPage;
    //设置每页显示条数
    private Integer pageSize = 10;
    //总条数
    private Integer TotalCount;
    //总页数
    private Integer TotalPage;
    //当前页面内容
    private List<T> list;

    public PageBean() {
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public Integer getTotalPage() {
        int i = TotalCount / pageSize;
        return TotalCount%pageSize==0?i:i+1;
    }
//
//    public void setTotalPage(Integer totalPage) {
//        TotalPage = totalPage;
//    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", TotalCount=" + TotalCount +
                ", TotalPage=" + TotalPage +
                '}';
    }
}
