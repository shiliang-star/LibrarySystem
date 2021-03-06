package com.shiliang.domain.vo;

import com.shiliang.domain.data.AuthorizationDo;

/**
 * @author SL
 * @create 2020-03-09 15:24
 * @description 此类是用于对管理员权限的封装
 */
public class AdminDetailsVo {
    private Integer id;
    private String username;
    private String name;
    private String phone;
    private Integer state;
    private String password;
    private AuthorizationDo authorization;


    public AdminDetailsVo() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorizationDo getAuthorization() {
        return authorization;
    }

    public void setAuthorization(AuthorizationDo authorization) {
        this.authorization = authorization;
    }
}
