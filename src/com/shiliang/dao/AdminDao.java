package com.shiliang.dao;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.BookDo;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-08 16:03
 * @description 管理员数据层
 */
public class AdminDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());

    public List<AdminDo> findAllAdmin() {
        String sql = "select * from admin";
        List<AdminDo> adminDoList = jt.query(sql, new BeanPropertyRowMapper<>(AdminDo.class));
        return adminDoList;
    }


    /**
     * 根据用户名来查找用户
     */
    public AdminDo findAdmin(AdminDo adminDo) {
        try {
            String sql="select * from admin where username=? and state=1 limit 1";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(AdminDo.class), adminDo.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据id来获取管理员
     */
    public AdminDo getAdminByID(AdminDo adminDo) {
        String sql="select * from admin where id=? and state=1 limit 1";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(AdminDo.class), adminDo.getId());
    }

    /**
     * 修改管理员信息
     */
    public Integer updateAdminInfo(AdminDo adminDo) {
        String sql = "update admin set name =?,phone=? where id=?";
         return jt.update(sql, adminDo.getName(), adminDo.getPhone(), adminDo.getId());
    }

    /**
     * 修改管理员密码
     */
    public Integer updateAdminPassword(AdminDo adminDo) {
        String sql = "update admin set password=? where id=?";
        return jt.update(sql, adminDo.getPassword(), adminDo.getId());
    }



}
