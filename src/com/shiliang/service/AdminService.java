package com.shiliang.service;

import com.shiliang.dao.AdminDao;
import com.shiliang.dao.AuthorizationDao;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * @author SL
 * @create 2020-03-08 20:36
 * @description 管理员服务层
 */
public class AdminService {

    private AdminDao adminDao = new AdminDao();
    private AuthorizationService service = new AuthorizationService();


    public AdminDetailsVo findAdmin(AdminDo adminDo) {

        AdminDo admin = adminDao.findAdmin(adminDo);
        if (admin != null) {
            AdminDetailsVo vo = new AdminDetailsVo();
            BeanUtils.copyProperties(admin, vo);

            AuthorizationDo authorizationDo = new AuthorizationDo();
            authorizationDo.setId(admin.getId());

            AuthorizationDo au = service.getAuthorizationById(authorizationDo);
            vo.setAuthorization(au);
            return vo;
        }

        return null;
    }
    /**
     * 修改管理员个人信息
     */
    public Integer updateAdminInfo(AdminDo adminDo) {
        return adminDao.updateAdminInfo(adminDo);
    }

    /**
     * 修改管理员密码
     */
    public Integer updateAdminPassword(AdminDo adminDo) {
        return adminDao.updateAdminPassword(adminDo);
    }
}
