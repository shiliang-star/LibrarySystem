package com.shiliang.dao;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-09 15:26
 * @description 操作管理员的权限
 */
public class AuthorizationDao {
    JdbcTemplate jt=new JdbcTemplate(JDBCUtil.getDataSource());

    /**
     * 通过id来获取权限信息
     * @param authorizationDo
     * @return
     */
    public AuthorizationDo getAuthorizationById(AuthorizationDo authorizationDo) {
        String sql = "select * from  authorization where id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(AuthorizationDo.class), authorizationDo.getId());
    }

    /**
     * 获取所有的权限信息
     * @return
     */
    public List<AuthorizationDo> getAuthorization() {
        String sql = "select * from  authorization ";
        return jt.query(sql, new BeanPropertyRowMapper<>(AuthorizationDo.class));
    }

    /**
     * 增加权限信息
     */
    public int addAuthorization(AuthorizationDo authorizationDo) {
        String sql = "INSERT INTO authorization(`id`, `bookSet`, `readerSet`, `borrowSet`, `typeSet`, `backSet`, `forfeitSet`, `sysSet`, `superSet`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        return jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                connection = con;
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, authorizationDo.getId());
                ps.setInt(2, authorizationDo.getBookSet());
                ps.setInt(3, authorizationDo.getReaderSet());
                ps.setInt(4, authorizationDo.getBorrowSet());
                ps.setInt(5, authorizationDo.getTypeSet());
                ps.setInt(6, authorizationDo.getBackSet());
                ps.setInt(7, authorizationDo.getForfeitSet());
                ps.setInt(8, authorizationDo.getSysSet());
                return ps;
            }
        });
    }

    /**
     * 修改管理员权限信息
     * @param authorizationDo
     * @return
     */
    public int updateAuthorization(AuthorizationDo authorizationDo) {
        String sql = "UPDATE `authorization` SET `bookSet`=?, `readerSet`=?, `borrowSet`=?, `typeSet`=?, `backSet`=?, `forfeitSet`=?, `sysSet`=? WHERE id=?";
        Object[] params = {authorizationDo.getBookSet(), authorizationDo.getReaderSet(), authorizationDo.getBorrowSet(), authorizationDo.getTypeSet(), authorizationDo.getBackSet(), authorizationDo.getForfeitSet(), authorizationDo.getSysSet(), authorizationDo.getId()};
        return jt.update(sql, params);
    }
}
