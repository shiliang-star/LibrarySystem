package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BackinfoDo;
import com.shiliang.domain.query.BackListQuery;
import com.shiliang.domain.vo.BackDetailsVO;
import com.shiliang.domain.vo.BackListVO;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-27 16:09
 * @description 图书归还信息数据层
 */
public class BackInfoDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());

    /**
     * 插入一天新的图书归还信息
     *
     * @param backinfoDo
     * @param
     * @return
     */
    public int addBackInfo(BackinfoDo backinfoDo) {
        Connection connection = null;
        try {
            String sql = "INSERT INTO `backinfo` (`id`, `backDate`, `fk_admin`) VALUES (?, null,null )";
            connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, backinfoDo.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 分页和条件查询图书借阅信息
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<BackListVO> pageSearch(BackListQuery query,int pageSize) {
        String sql_data = "SELECT" +
                "	bo.id borrowId," +
                "	b.isbn ISBN," +
                "	b.NAME bookName," +
                "	r.paperNo paperNO," +
                "	r. NAME readerName," +
                "	ba.backDate," +
                "	bo.endDate," +
                "  a.name adminName  " +
                "FROM" +
                "	borrowinfo bo " +
                "LEFT JOIN book b ON bo.fk_book = b.id " +
                "LEFT JOIN reader r ON bo.fk_reader = r.id " +
                "LEFT JOIN backinfo ba ON bo.id = ba.id " +
                "LEFT JOIN admin a on    bo.fk_admin=a.id where 1=1  ";
        String sql_count = "SELECT count(*) FROM " +
                "	borrowinfo bo " +
                "LEFT JOIN book b ON bo.fk_book = b.id " +
                "LEFT JOIN reader r ON bo.fk_reader = r.id " +
                "LEFT JOIN backinfo ba ON bo.id = ba.id " +
                "LEFT JOIN admin a on bo.fk_admin=a.id where 1=1  ";
        StringBuilder sb = new StringBuilder();
        if ( DataUtils.isValid(query.getBorrowId())) {
            sb.append("and bo.id like '%" + query.getBorrowId() + "%' ");
        }
        if (DataUtils.isValid(query.getISBN())) {
            sb.append("and b.isbn like '%" + query.getISBN() + "%' ");
        }
        if (DataUtils.isValid(query.getPaperNO())) {
            sb.append("and r.paperNO like '%" + query.getPaperNO() + "%' ");
        }

        sql_count += sb.toString();
        sql_data += sb.toString();
        sql_data += " ORDER  BY bo.id DESC  limit ?,?";

        PageBean<BackListVO> pb=new PageBean<>();
        pb.setCurrentPage(Integer.parseInt(query.getCurrentPage()));
        pb.setPageSize(pageSize);

        Integer totalCount = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(totalCount);

        int start = (Integer.parseInt(query.getCurrentPage()) - 1) * pageSize;
        List<BackListVO> backListVOS = jt.query(sql_data, new BeanPropertyRowMapper<>(BackListVO.class), start, pageSize);
        pb.setList(backListVOS);

        return pb;
    }


    /**
     * 更新图书的归还信息 更新归还的时间和操作的管理员
     * @param backinfoDo
     * @return
     */
    public int updateBackInfo(BackinfoDo backinfoDo) {
        String sql = "update backinfo set backDate=?,fk_admin=? where id=?";
        return jt.update(sql, backinfoDo.getBackDate(), backinfoDo.getFkAdmin(), backinfoDo.getId());
    }

    /**
     * 通过图书id获取图书归还详细信息
     */
    public BackDetailsVO getBackInfoById(BackinfoDo backinfoDo) {
        String sql = "SELECT \n" +
                "bo.id borrowId,\n" +
                "b.isbn ISBN,\n" +
                "b.name bookName,\n" +
                "bt.name bookType,\n" +
                "r.paperNO paperNO,\n" +
                "r.name readerName,\n" +
                "rt.name readerType,\n" +
                "bo.overday,\n" +
                "a.name admin,\n" +
                "bo.state\n" +
                "from \n" +
                "backinfo ba\n" +
                "LEFT JOIN borrowinfo bo on bo.id=ba.id \n" +
                "LEFT JOIN book b on bo.fk_book=b.id \n" +
                "LEFT JOIN booktype bt on b.fk_booktype=bt.id\n" +
                "LEFT JOIN reader r on bo.fk_reader=r.id \n" +
                "LEFT JOIN reader rt on r.fk_readertype=rt.id\n" +
                "LEFT JOIN admin a on ba.fk_admin=a.id where ba.id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BackDetailsVO.class), backinfoDo.getId());
    }
}
