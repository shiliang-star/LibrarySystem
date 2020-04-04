package com.shiliang.service;

import com.shiliang.dao.AdminManageDao;
import com.shiliang.dao.AuthorizationDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.domain.query.AdminQuery;
import com.shiliang.domain.vo.AdminManageListVO;
import com.shiliang.utils.JDBCUtil;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author SL
 * @create 2020-03-22 12:05
 * @description 提供管理员管理的服务
 */
public class AdminManageService {
    AdminManageDao dao=new AdminManageDao();
    AuthorizationDao authorizationDao=new AuthorizationDao();


    /**
     * 条件查询
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<AdminManageListVO> pageSearch(AdminQuery query, int pageSize) {
        try {
            return dao.pageSearch(query, pageSize);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 添加管理员信息和管理员权限
     * @param adminDo
     * @return
     */
    public int addAdmin(AdminDo adminDo) {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            //开启事务
            connection.setAutoCommit(false);
            //获取添加的主键值
            int id = dao.addAdmin(adminDo);
            if (id > 0) {
                //添加权限

//                int i = 3/ 0;

                AuthorizationDo authorizationDo = new AuthorizationDo(id,0,0,0,0,0,0,0);
                int count = authorizationDao.addAuthorization(authorizationDo);
//                提交事务
               connection.commit();
                if (count > 0) {
                    return count;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return 0;
    }


    /**
     * 通过用户名来获取管理员信息
     * @param adminDo
     * @return
     */
    public AdminDo getAdminByUserName(AdminDo adminDo) {
        try {
            return dao.getAdminByUserName(adminDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdminDo getAdminInfoById(AdminDo adminDo) {
        try {
            return dao.getAdminInfoById(adminDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateAdmin(AdminDo adminDo) {
        try {
            return dao.updateAdmin(adminDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAdmin(AdminDo adminDo) {
        try {
            return dao.deleteAdmin(adminDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
