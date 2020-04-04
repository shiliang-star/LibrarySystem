package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.OverdueinfoDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.query.ForfeitQuery;
import com.shiliang.domain.vo.OverDueDetailsVO;
import com.shiliang.domain.vo.OverDueManageListVO;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-27 14:41
 * @description 逾期信息数据层
 */
public class OverDueInfoDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());

    /**
     * 通过读者id获取该读者的所有逾期信息
     * @param readerDo
     * @return
     */
    public List<OverdueinfoDo> getOverDueInfoByReaderId(ReaderDo readerDo) {
        String sql = "select * from borrowinfo b,overdueinfo o where o.id=b.id and b.fk_reader=?";
        return jt.query(sql, new BeanPropertyRowMapper<>(OverdueinfoDo.class), readerDo.getId());
    }


    /**
     * 通过图书借阅id清除逾期信息
     */
    public int deleteOverDueInfoById(OverdueinfoDo overdueinfoDo) {
        String sql = "delete from overdueinfo where id=?";
        return jt.update(sql, overdueinfoDo.getId());
    }

    /**
     * 获取逾期列表信息
     */
    public PageBean<OverDueManageListVO> pageSearch(ForfeitQuery query, int pageSize) {
        PageBean<OverDueManageListVO> pb = new PageBean<>();
        pb.setPageSize(pageSize);
        pb.setCurrentPage(Integer.parseInt(query.getCurrentPage()));

        String sql_data = "SELECT \n" +
                "bo.id borrowId,\n" +
                "b.isbn ISBN,\n" +
                "b.name bookName,\n" +
                "r.paperNO paperNO,\n" +
                "r.name readerName,\n" +
                "bo.overday overDay,\n" +
                "ov.forfeit forfeit\n" +
                " from borrowinfo bo,overdueinfo ov,book b,reader r \n" +
                "where bo.id=ov.id and bo.fk_book=b.id and \n" +
                "bo.fk_reader=r.id  ";
        String sql_count = "SELECT \n" +
                "count(*)\n" +
                " from borrowinfo bo,overdueinfo ov,book b,reader r \n" +
                "where bo.id=ov.id and bo.fk_book=b.id and \n" +
                "bo.fk_reader=r.id  ";

        StringBuilder sb = new StringBuilder();

        if (DataUtils.isValid(query.getBorrowId())) {
            sb.append("and bo.id like '%" + query.getBorrowId() + "%' ");
        }
        if (DataUtils.isValid(query.getPaperNO())) {
            sb.append("and r.paperNO like '%" + query.getPaperNO() + "%' ");
        }
        if (DataUtils.isValid(query.getISBN())) {
            sb.append("and b.isbn like '%" + query.getISBN() + "%' ");
        }


        sql_count += sb.toString();
        sql_data += sb.toString()+" limit ?,?";

        Integer totalCount = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(totalCount);

        int start = (Integer.parseInt(query.getCurrentPage()) - 1) * pageSize;
        List<OverDueManageListVO> overDueManageListVOS = jt.query(sql_data, new BeanPropertyRowMapper<>(OverDueManageListVO.class), start, pageSize);
        pb.setList(overDueManageListVOS);

        return pb;
    }


    /**
     * 通过借阅id获取图书详细信息
     *
     * @param overdueinfoDo
     * @return
     */
    public OverDueDetailsVO getgetForfeitInfoById(OverdueinfoDo overdueinfoDo) {
        String sql = "select \n" +
                "bo.id borrowId,\n" +
                "b.isbn ISBN,\n" +
                "b.name bookName,\n" +
                "bt.name bookType,\n" +
                "r.paperNO paperNO,\n" +
                "r.name readerName,\n" +
                "rt.name readerType,\n" +
                "bo.overday,\n" +
                "a.name admin,\n" +
                "ov.isPay\n" +
                "FROM overdueinfo ov\n" +
                "LEFT JOIN borrowinfo bo on ov.id=bo.id\n" +
                "LEFT JOIN book b on bo.fk_book=b.id\n" +
                "LEFT JOIN booktype bt on b.fk_booktype=bt.id\n" +
                "LEFT JOIN reader r on bo.fk_reader=r.id\n" +
                "LEFT JOIN readertype rt on r.fk_readertype=rt.id\n" +
                "LEFT JOIN admin a on ov.fk_admin=a.id where bo.id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(OverDueDetailsVO.class), overdueinfoDo.getId());
    }

    /**
     * 通过借阅Id获取图书逾期信息
     */
    public OverdueinfoDo getOverDueInfoById(OverdueinfoDo overdueinfoDo) {
        try {
            String sql = "select * from overdueinfo where id=?";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(OverdueinfoDo.class), overdueinfoDo.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新逾期罚款信息(支付状态、操作管理员)
     */
    public int updateOverDueInfo(OverdueinfoDo overdueinfoDo) {
        String sql = "update overdueinfo set isPay=?,fk_admin=?";
        return jt.update(sql, overdueinfoDo.getIsPay(), overdueinfoDo.getFkAdmin());
    }
    /**
     * 更新逾期罚款信息（罚金金额）
     */
    public int updateOverDueForfeit(OverdueinfoDo overdueinfoDo) {
        String sql = "update overdueinfo set forfeit=? where id=?";
        return jt.update(sql, overdueinfoDo.getForfeit(),overdueinfoDo.getId());
    }

    /**
     * 插入一条新得逾期信息
     */
    public int addOverDueInfo(OverdueinfoDo overdueinfoDo) {
        String sql = "insert into overdueinfo values(?,?,0,null)";
        return jt.update(sql, overdueinfoDo.getId(), overdueinfoDo.getForfeit());
    }
}
