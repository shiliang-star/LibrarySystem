package com.shiliang.service;

import com.shiliang.dao.AuthorizationDao;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.AuthorizationDo;

/**
 * @author SL
 * @create 2020-03-09 15:33
 * @description 管理员权限的业务逻辑
 */
public class AuthorizationService {
      AuthorizationDao dao=new AuthorizationDao();

    /**
     * 通过id获取权限的信息
     * @param authorizationDo
     * @return
     */
    public AuthorizationDo getAuthorizationById(AuthorizationDo authorizationDo) {
        try {
            return dao.getAuthorizationById(authorizationDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 修改管理员权限信息
     */
    public int updateAuthorization(AuthorizationDo authorizationDo) {
        try {
            return dao.updateAuthorization(authorizationDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
