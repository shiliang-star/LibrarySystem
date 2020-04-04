package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-17 19:53
 * @description 对读者类型进行操作
 */
public class ReaderTypeManageDao {

    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());


    /**
     * 实现分页查询功能
     */
    public PageBean<ReadertypeDo> pageSearch(int currentPage, int pageSize) {

        PageBean<ReadertypeDo> pb=new PageBean<>();

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        String sql_data = "select * from readertype limit ?,?";
        String sql_count = "select count(*) from readertype";

        Integer count = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(count);

        int begin = (currentPage - 1) * pageSize;
        List<ReadertypeDo> query = jt.query(sql_data, new BeanPropertyRowMapper<>(ReadertypeDo.class), begin, pageSize);
        pb.setList(query);

        return pb;

    }


    /**
     * 通过读者类型Id来更新读者类型所有信息
     * @param readertypeDo
     * @return
     */
    public Integer updateReaderTypeById(ReadertypeDo readertypeDo) {
        String sql = "update readertype set name=?,maxNum=?,bday=?,penalty=?,renewDays=? where id=?";
        return  jt.update(sql, readertypeDo.getName(), readertypeDo.getMaxNum(), readertypeDo.getBday(), readertypeDo.getPenalty(), readertypeDo.getRenewDays(), readertypeDo.getId());
    }

    /**
     * 通过读者类型Id来查询读者类型所有信息
     * @param readertypeDo
     * @return
     */
    public ReadertypeDo getReaderTypeByID(ReadertypeDo readertypeDo) {
        String sql = "select * from readertype where id=?";
         return jt.queryForObject(sql, new BeanPropertyRowMapper<>(ReadertypeDo.class), readertypeDo.getId());
    }

    /**
     * 添加读者类型信息
     * @param readertypeDo
     * @return
     */
    public int addReaderType(ReadertypeDo readertypeDo) {
        String sql = "insert into readertype values(null,?,?,?,?,?) ";
        return jt.update(sql, readertypeDo.getName(), readertypeDo.getMaxNum(), readertypeDo.getBday(), readertypeDo.getPenalty(), readertypeDo.getRenewDays());
    }

    /**
     * 通过读者类型名称来查询读者类型所有信息
     */
    public ReadertypeDo getReaderTypeByName(ReadertypeDo readertypeDo) {
        String sql = "select * from readertype where name=?";
         return jt.queryForObject(sql, new BeanPropertyRowMapper<>(ReadertypeDo.class), readertypeDo.getName());
    }
}
