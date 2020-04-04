package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.domain.query.AdminQuery;
import com.shiliang.domain.vo.AdminManageListVO;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SL
 * @create 2020-03-22 12:05
 * @description 对数据库中管理员信息的操作
 */
public class AdminManageDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());


    /**
     * 分页查询和条件查询管理员数据信息
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<AdminManageListVO> pageSearch(AdminQuery query, int pageSize) throws InvocationTargetException, IllegalAccessException {

        PageBean<AdminManageListVO> pb=new PageBean<>();
        pb.setCurrentPage(query.getCurrentPage());
        pb.setPageSize(pageSize);

        StringBuilder sb=new StringBuilder();

        String sql_data = "select a.userName,a.name,a.phone,au.* from admin a,authorization au where  a.state=1 AND a.id=au.id ";
        String sql_count = "select count(*) from admin a,authorization au where a.state=1 AND a.id=au.id   ";

        //条件查询管理员用户名
        if (DataUtils.isValid(query.getUserName())) {
            sb.append("and a.username like '%" + query.getUserName() + "%' ");
        }
        //条件查询管理员名称
        if (DataUtils.isValid(query.getName())) {
            sb.append("and a.name like '%" + query.getName() + "%'  ");
        }

        sql_data += sb.toString();
        sql_count += sb.toString();
        sql_data += " limit ?,?  ";


        Integer totalCount = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(totalCount);

        int start = (query.getCurrentPage() - 1) * pageSize;
        List<Map<String, Object>> mapList = jt.queryForList(sql_data, start, pageSize);

        List<AdminManageListVO> vos=new ArrayList<>();

        for (Map<String, Object> map : mapList) {
            AdminManageListVO vo=new AdminManageListVO();
            BeanUtils.populate(vo,map);
            AuthorizationDo authorizationDo=new AuthorizationDo();
            BeanUtils.populate(authorizationDo, map);
            vo.setAuthorizationDo(authorizationDo);
            vos.add(vo);
        }

        pb.setList(vos);

        return pb;
    }


    /**
     * 添加管理员
     * @param adminDo
     * @return返回添加的主键值
     */
    public int addAdmin(AdminDo adminDo) {
        //主键的持有者
        KeyHolder keyHolder=new GeneratedKeyHolder();
        String sql = "insert into admin values(null,?,?,?,?,?)";
//        Object[] params = {adminDo.getUsername(), adminDo.getName(), adminDo.getPhone(), adminDo.getState(), adminDo.getPassword()};
       jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                connection = con;
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, adminDo.getUsername());
                ps.setString(2, adminDo.getName());
                ps.setString(3, adminDo.getPhone());
                ps.setInt(4,adminDo.getState());
                ps.setString(5,adminDo.getPassword());
                return ps;
            }
        },keyHolder);
       //获取添加后的主键值
        return keyHolder.getKey().intValue();
    }

    /**
     * 通过用户名来获取管理员信息
     * @param adminDo
     * @return
     */
    public AdminDo getAdminByUserName(AdminDo adminDo) {
        String sql = "select * from admin where state=1 and username=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(AdminDo.class),adminDo.getUsername());
    }

    /**
     * 通过id来获取管理员的信息
     * @param adminDo
     * @return 管理员信息
     */
    public AdminDo getAdminInfoById(AdminDo adminDo) {
        String sql = "select * from admin where state=1 and id=?";
       return jt.queryForObject(sql, new BeanPropertyRowMapper<>(AdminDo.class), adminDo.getId());
    }

    /**
     * 更新管理员信息
     */
    public int updateAdmin(AdminDo adminDo) {
        String sql = "update admin set username=?,name=?,phone=? where id=?";
        int update = jt.update(sql, adminDo.getUsername(), adminDo.getName(), adminDo.getPhone(), adminDo.getId());
        return update;
    }

    /**
     * 逻辑删除管理员信息
     *   将其字段state设置为0
     * @param adminDo
     * @return
     */
    public int deleteAdmin(AdminDo adminDo) {
        String sql = "update admin set state=0 where id=?";
        return jt.update(sql, adminDo.getId());
    }
}
