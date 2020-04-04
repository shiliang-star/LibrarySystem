package com.shiliang.dao;

import com.shiliang.domain.data.ReaderDo;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author SL
 * @create 2020-03-09 23:29
 * @description 读者数据层
 */
public class ReaderDao {

    JdbcTemplate jt=new JdbcTemplate(JDBCUtil.getDataSource());

    public ReaderDo findReaderByPaperNo(ReaderDo readerDo) {
        try {
            String sql = "select * from reader where paperNo=? ";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(ReaderDo.class), readerDo.getPaperNo());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新读者信息
     *
     * @param readerDo
     * @return
     */
    public int updateReaderInfo(ReaderDo readerDo) {
        String sql = "update reader set phone=?,email=? where id=?";
        return jt.update(sql, readerDo.getPhone(), readerDo.getEmail(), readerDo.getId());

    }

    /**
     * 修改读者密码
     * @param readerDo
     * @return
     */
    public int updateReaderPassword(ReaderDo readerDo) {
        String sql = "update reader set password=? where id =?";
        return jt.update(sql, readerDo.getPassword(), readerDo.getId());
    }
}
