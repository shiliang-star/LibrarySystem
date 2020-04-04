package com.shiliang.domain.vo;

import com.shiliang.domain.data.AuthorizationDo;

/**
 * @author SL
 * @create 2020-03-22 12:02
 * @description 管理员前端数据的封装
 */
public class AdminManageListVO {
    //管理员Id
    private Integer Id;
    //管理员用户名
    private String userName;
    //管理员姓名
    private String name;
    //管理员号码
    private String phone;
    //管理员的权限
    private AuthorizationDo authorizationDo;

    public AdminManageListVO() {
    }

    public AdminManageListVO(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AuthorizationDo getAuthorizationDo() {
        return authorizationDo;
    }

    public void setAuthorizationDo(AuthorizationDo authorizationDo) {
        this.authorizationDo = authorizationDo;
    }
}
