package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.query.BorrowSearchQuery;
import com.shiliang.domain.vo.BorrowSearchListVO;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-28 16:12
 * @description 图书借阅查询数据层
 */
public class BorrowSearchDao {

    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());

    /**
     * 分页和条件查询图书借阅信息
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<BorrowSearchListVO> pageSearch(BorrowSearchQuery query, int pageSize) {
        String sql_data = "select\n" +
                "bo.id borrowId,\n" +
                "b.isbn ISBN,\n" +
                "b.name bookName,\n" +
                "r.paperNo paperNO,\n" +
                "r.name readerName,\n" +
                "bo.borrowDate,\n" +
                "ba.backDate,\n" +
                "bo.endDate  \n" +
                "from borrowinfo bo\n" +
                "LEFT JOIN book b on bo.fk_book=b.id\n" +
                "LEFT JOIN reader r on bo.fk_reader=r.id\n" +
                "LEFT JOIN backinfo ba on bo.id=ba.id where 1=1  ";
        String sql_count = "select\n" +
                "count(*) \n" +
                "from borrowinfo bo\n" +
                "LEFT JOIN book b on bo.fk_book=b.id\n" +
                "LEFT JOIN reader r on bo.fk_reader=r.id\n" +
                "LEFT JOIN backinfo ba on bo.id=ba.id where 1=1  ";

        PageBean<BorrowSearchListVO> pb = new PageBean<>();
        pb.setCurrentPage(Integer.parseInt(query.getCurrentPage()));
        pb.setPageSize(pageSize);

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
        sql_data += sb.toString() + " ORDER BY bo.id DESC limit ?,?";


        Integer totalCount = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(totalCount);

        int start = (Integer.parseInt(query.getCurrentPage()) - 1) * pageSize;
        List<BorrowSearchListVO> borrowSearchListVOS = jt.query(sql_data, new BeanPropertyRowMapper<>(BorrowSearchListVO.class), start, pageSize);
        pb.setList(borrowSearchListVOS);

        return pb;
    }


}
