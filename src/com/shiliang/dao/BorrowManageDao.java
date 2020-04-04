package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BorrowinfoDo;
import com.shiliang.domain.vo.BorrowInfoVO;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-26 13:50
 * @description 图书借阅数据处理
 */
public class BorrowManageDao {

    JdbcTemplate jt=new JdbcTemplate(JDBCUtil.getDataSource());

    /**
     * 分页查询图书借阅信息
     * @param currentPage 当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    public PageBean<BorrowInfoVO> pageSearch(int currentPage, int pageSize) {
        String sql_data = "select bi.id borrowId,b.isbn ISBN,b.name bookName,r.paperNo,r.name readerName,bi.borrowDate,bi.endDate from borrowinfo bi,book b,reader r where bi.fk_reader=r.id and bi.fk_book=b.id order by bi.id DESC limit ?,?";
        String sql_count = "select count(*) from borrowinfo bi,book b,reader r where bi.fk_reader=r.id and bi.fk_book=b.id";
        PageBean<BorrowInfoVO> pb=new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int start = (currentPage - 1) * pageSize;
        List<BorrowInfoVO> borrowInfoVOS = jt.query(sql_data, new BeanPropertyRowMapper<>(BorrowInfoVO.class),start,pageSize);
        pb.setList(borrowInfoVOS);

        Integer totalCount = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(totalCount);

        return pb;
    }


    /**
     * 通过id查询图书借阅信息
     *
     * @param borrowinfoDo
     * @return
     */
    public BorrowinfoDo getBorrowInfoById(BorrowinfoDo borrowinfoDo) {
        String sql = "select * from borrowinfo where id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BorrowinfoDo.class), borrowinfoDo.getId());
    }

    /**
     * 查询当前读者的图书未归还量
     */
    public Integer getNoBackNum() {
        String sql = "SELECT count(*) from borrowinfo where state=0 or state=1 or state= 3 or state=4";
        return jt.queryForObject(sql, Integer.class);
    }

    /**
     * 插入新的图书借阅信息   返回插入和的主键id
     */
    public int addBorrowInfo(BorrowinfoDo borrowinfoDo ) {

        String sql_addBorrow = "INSERT INTO `borrowinfo` (`id`, `borrowDate`, `endDate`, `overday`, `state`, `fk_reader`, `fk_book`, `penalty`, `fk_admin`) VALUES (null, ?, ?, ?, ?, ?,?, ?, ?)";
        Connection con = JDBCUtil.getConnection();
        try {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            jt.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(sql_addBorrow, Statement.RETURN_GENERATED_KEYS);
                        ps.setDate(1, (Date) borrowinfoDo.getBorrowDate());
                        ps.setDate(2, (Date) borrowinfoDo.getEndDate());
                        ps.setInt(3, borrowinfoDo.getOverday());
                        ps.setInt(4, borrowinfoDo.getState());
                        ps.setInt(5, borrowinfoDo.getFkReader());
                        ps.setInt(6, borrowinfoDo.getFkBook());
                        ps.setDouble(7, borrowinfoDo.getPenalty());
                        ps.setInt(8, borrowinfoDo.getFkAdmin());
                        return ps;
                    }
                }, keyHolder);


            return keyHolder.getKey().intValue();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 更新图书的借阅状态
     */
    public int updateBorrowState(BorrowinfoDo borrowInfo) {
        String sql = "update borrowinfo set state=? where id=?";
        return jt.update(sql, borrowInfo.getState(), borrowInfo.getId());
    }
    /**
     * 更新图书的借阅状态(借阅状态、逾期天数)
     */
    public int updateBorrowStateAndOverday(BorrowinfoDo borrowInfo) {
        String sql = "update borrowinfo set state=?,overday=? where id=?";
        return jt.update(sql, borrowInfo.getState(),borrowInfo.getOverday(), borrowInfo.getId());
    }

    /**
     * 更新图书借阅信息的截至时间、归还状态、逾期天数
     */
    public int updateBorrowInfos(BorrowinfoDo borrowinfoDo) {
        String sql = "update borrowinfo set endDate=?,state=?,overday=? where id=?";
        return jt.update(sql, borrowinfoDo.getEndDate(),borrowinfoDo.getState(),borrowinfoDo.getOverday(),borrowinfoDo.getId());
    }


    /**
     * 查询所有未归还得图书信息
     */
    public List<BorrowinfoDo> getBorrowInfoByNoBackState() {
        String sql = "select * from borrowinfo where state=0 or state=1 or state=3 or state=4";
        return jt.query(sql, new BeanPropertyRowMapper<>(BorrowinfoDo.class));
    }
}
