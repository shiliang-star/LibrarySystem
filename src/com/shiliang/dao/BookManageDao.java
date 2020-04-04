package com.shiliang.dao;

import com.shiliang.T;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookManageQuery;
import com.shiliang.domain.vo.BookDetailsVo;
import com.shiliang.domain.vo.BookManageListVo;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-12 22:38
 * @description 图书管理数据层
 */
public class BookManageDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());


    /**
     * 分页查询
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<BookManageListVo> pageSearch(BookManageQuery query, int pageSize) {
        PageBean<BookManageListVo>  pb=new PageBean<>();
        pb.setPageSize(pageSize);
        pb.setCurrentPage(Integer.parseInt(query.getCurrentPage()));

        StringBuilder sb=new StringBuilder();
        String sql_count = "select count(*) from book b where 1=1 and num>0  ";
        String sql_data  =" select  b.*,bt.name booktype from book b,booktype bt where b.fk_booktype=bt.id and num>0 ";

        if (DataUtils.isValid(query.getAuthor())) {
            sb.append("and b.author like '%" + query.getAuthor() + "%' ");
        }
        if (DataUtils.isValid(query.getBookName())) {
            sb.append("and b.name  like '%" + query.getBookName() + "%' ");
        }
        if (DataUtils.isValid(query.getISBN())) {
            sb.append("and b.ISBN like '%" + query.getISBN() + "%' ");
        }
        if (DataUtils.isValid(query.getPress())) {
            sb.append("and b.press like '%" + query.getPress() + "%' ");
        }
        if (query.getBookTypeId()!=null &&!"".equals(query.getBookTypeId()) && !"-1".equals(query.getBookTypeId())) {
            sb.append("and b.fk_booktype=" + query.getBookTypeId()+"  ");
        }
        sql_count += sb.toString();
        sql_data += sb.toString();
        sql_data += "limit ?,?";

//        System.out.println(sql_count);
//        System.out.println(sql_data);


        Integer count = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(count);

        int start = (Integer.parseInt(query.getCurrentPage())-1) * pageSize;
        List<BookManageListVo> listVos = jt.query(sql_data, new BeanPropertyRowMapper<>(BookManageListVo.class), start, pageSize);
        pb.setList(listVos);

        return pb;

    }

    /**
     * 获取所有的图书分类信息
     */
    public List<BooktypeDo> getAllBookTypes() {
        String sql = "select * from booktype";
        return jt.query(sql,new BeanPropertyRowMapper<>(BooktypeDo.class));
    }


    /**
     * 添加图书
     */
    public Integer addBook(BookDo bookDo) {
        String sql = "insert into book values(null,?,?,?,?,?,?,?,?,?,?,?) ";
        Object[] params = {bookDo.getName(), bookDo.getIsbn(), bookDo.getAuthor(), bookDo.getNum(), bookDo.getCurrentNum(), bookDo.getPress(), bookDo.getDescription(), bookDo.getPrice(), bookDo.getPutdate(), bookDo.getFk_booktype(), bookDo.getFk_admin()};
        return jt.update(sql, params);
    }


    /**
     * 通过图书ISBN来判断图书是否重复
     */
    public BookDo getBookByISBN(BookDo bookDo) {
        try {
            String sql = "select * from book where isbn=? and num>0 ";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BookDo.class), bookDo.getIsbn());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过图书id来获取图书信息
     */
    public BookDo getBookInfoById(BookDo bookDo) {
        String sql = "select * from book where id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BookDo.class), bookDo.getId());
    }


    /**
     * 修改图书信息
     */
    public Integer updateBook(BookDo bookDo) {
        String sql = "update book set ISBN=?,name=?,fk_booktype=?,author=?,press=?,price=?,description=? where id=?";
        Object[] params = {bookDo.getIsbn(), bookDo.getName(), bookDo.getFk_booktype(), bookDo.getAuthor(), bookDo.getPress(), bookDo.getPrice(), bookDo.getDescription(), bookDo.getId()};
        return jt.update(sql, params);
    }

    /**
     * 添加图书数量
     */
    public Integer addBookNum(BookDo bookDo) {
        String sql = "update book set num=?,currentNum=? where id=?";
        return jt.update(sql,bookDo.getNum(),bookDo.getCurrentNum(),bookDo.getId());
    }


    /**
     * 查询图书的信息封装到list集合中，用于导出
     */

    public List<BookDetailsVo> exportBook() {
        String sql = "select  b.*,bt.name booktype,a.name adminName,b.name bookname from book b,booktype bt,admin a where b.fk_booktype=bt.id and b.fk_admin=a.id and num>0 ";
        return jt.query(sql, new BeanPropertyRowMapper<>(BookDetailsVo.class));
    }


    /**
     * 批量添加图书
     */
    public int[] batchAddBook(List<Object[]> params) {
        String sql = "insert into book(`id`, `name`, `ISBN`, `author`, `num`, `currentNum`, `press`, " +
                "`description`, `price`, `putdate`, `fk_booktype`, `fk_admin`) values(null,?,?,?,?,?,?,?,?,?,?,?) ";

        int[] ints = jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Object[] objects = params.get(i);
                preparedStatement.setString(1, (String) objects[0]);
                preparedStatement.setString(2, (String) objects[1]);
                preparedStatement.setString(3, (String) objects[2]);
                preparedStatement.setInt(4, (Integer) objects[3]);
                preparedStatement.setInt(5, (Integer) objects[4]);
                preparedStatement.setString(6, (String) objects[5]);
                preparedStatement.setString(7, (String) objects[6]);
                preparedStatement.setDouble(8, (Double) objects[7]);
                preparedStatement.setDate(9, (java.sql.Date) objects[8]);
                preparedStatement.setInt(10, (Integer) objects[9]);
                preparedStatement.setInt(11, (Integer) objects[10]);

            }

            @Override
            public int getBatchSize() {
                return params.size();
            }

        });
        return ints;
    }

    /**
     * 更新图书的在管数量
     */
    public int updateBookInfo(BookDo bookDo) {
        Connection connection = null;
        try {
            String sql = "update book set currentNum=? where id=?";
            connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookDo.getCurrentNum());
            ps.setInt(2, bookDo.getId());
            int executeUpdate = ps.executeUpdate();
            return executeUpdate;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 逻辑删除图书信息：将图书的总数量和在馆数量设置为0
     * @param bookInfoById
     * @return
     */
    public int deleteBook(BookDo bookInfoById) {
        String sql = "update book set num=?,currentNum=? where id=?";
        return jt.update(sql, bookInfoById.getNum(), bookInfoById.getCurrentNum(), bookInfoById.getId());
    }
}
