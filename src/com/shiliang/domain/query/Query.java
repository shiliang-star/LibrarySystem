package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-26 19:46
 * @description 父类 封装当前页码的信息
 */
public class Query {
    private String currentPage;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
