package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookTypeQuery;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author SL
 * @create 2020-03-11 15:52
 * @description 分页查询
 */
public class BookTypeManageDao {

    JdbcTemplate jt=new JdbcTemplate(JDBCUtil.getDataSource());

    public PageBean<BooktypeDo> pageSearch(BookTypeQuery query, int pageSize) {
        String sql_data = "select * from booktype ";
        String sql_count = "select count(*) from booktype ";

        StringBuilder sb=new StringBuilder();
        if (query != null) {
            if (DataUtils.isValid(query.getTypeName())) {
                sb.append("where name like '%"+query.getTypeName()+"%'  " );
            }
        }
        sql_count += sb.toString();
        sql_data +=sb.toString();
        sql_data +="limit ?,?";
//        System.out.println(sql_count);
//        System.out.println(sql_data);
        PageBean<BooktypeDo> pb = new PageBean<>();
        pb.setCurrentPage(query.getCurrentPage());
        pb.setPageSize(pageSize);

        Integer count = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(count);

        int begin = (query.getCurrentPage() - 1) * pageSize;
        List<BooktypeDo> booktypeDoList = jt.query(sql_data, new BeanPropertyRowMapper<>(BooktypeDo.class), begin, pageSize);
        pb.setList(booktypeDoList);

        return pb;
    }


    /**
     * 通过id来删除图书分类类型
     *
     * @param booktypeDo
     * @return
     */
    public Integer deleteBookTypeById(BooktypeDo booktypeDo) {
        String sql = "delete from booktype where id= ?";
        return jt.update(sql, booktypeDo.getId());
    }

    /**
     * 通过id来获取图书分类信息
     */
    public BooktypeDo getBookTypeById(BooktypeDo booktypeDo) {
        String sql = "select * from booktype where id=?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BooktypeDo.class), booktypeDo.getId());
    }

    /**
     * 修改图书类型信息
     * @param booktypeDo
     * @return
     */
    public Integer updateBookTypeInfo(BooktypeDo booktypeDo) {
        String sql = "update booktype set name=? where id =?";
        return jt.update(sql, booktypeDo.getName(), booktypeDo.getId());
    }

    /**
     * 根据图书分类名称来查询图书分类信息，判断是否重复
     */
    public BooktypeDo getBooktypeByName(BooktypeDo booktypeDo) {
        try {
            String sql = "select * from booktype where name=?";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(BooktypeDo.class), booktypeDo.getName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加图书分类信息
     */
    public Integer addBookType(BooktypeDo booktypeDo) {
        String sql = "insert into booktype values (null ,?)";
        return jt.update(sql, booktypeDo.getName());
    }




}
