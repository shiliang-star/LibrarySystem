package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-22 11:33
 * @description 管理员查询信息封装
 */
public class AdminQuery {
    //管理员ID
    private Integer id;
    //管理员用户名
    private String userName;
    //管理员名称
    private String name;
    //当前页面
    private Integer currentPage;

    public AdminQuery() {
    }


    public AdminQuery(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
