package com.shiliang.dao;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.domain.query.ReaderQuery;
import com.shiliang.domain.vo.ReaderDetailsVo;
import com.shiliang.domain.vo.ReaderManageListVo;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-18 18:18
 * @description 对读者数据进行一系列操作
 */
public class ReaderManageDao {

    JdbcTemplate jt = new JdbcTemplate(JDBCUtil.getDataSource());


    /**
     * 实现分页查询和条件查询读者数据
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<ReaderManageListVo> pageSearch(ReaderQuery query, int pageSize) {

        String sql_data = "select r.*,r.name readerName,rt.name readerTypeName from reader r,readertype rt where r.fk_readertype=rt.id  ";
        String sql_count = "select count(*) from reader r where 1=1 ";


        StringBuilder sb=new StringBuilder();

        PageBean<ReaderManageListVo> pb=new PageBean<>();
        pb.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage()));
        pb.setPageSize(pageSize);

        if (DataUtils.isValid(query.getPaperNo())) {
            sb.append("and r.paperNO like '%" + query.getPaperNo() + "%' ");
        }
        if (DataUtils.isValid(query.getReaderName())) {
            sb.append("and r.name like '%" + query.getReaderName() + "%' ");
        }
        if (query.getReaderTypeId() != null && !"".equals(query.getReaderTypeId()) && !"-1".equals(query.getReaderTypeId())) {
            sb.append("and r.fk_readertype=" + query.getReaderTypeId()+"  ");
        }


        sql_count += sb.toString();
        sql_data += sb.toString();
        sql_data += "limit ?,?";

        int begin = (DataUtils.getPageCode(query.getCurrentPage()) - 1) * pageSize;

        Integer count = jt.queryForObject(sql_count, Integer.class);
        pb.setTotalCount(count);

        List<ReaderManageListVo> listVos = jt.query(sql_data, new BeanPropertyRowMapper<>(ReaderManageListVo.class), begin, pageSize);
        pb.setList(listVos);



        return pb;

    }


    /**
     * 获取所有的读者类型数据
     */
    public List<ReadertypeDo> getAllReaderTypes() {
        String sql = "select * from readertype";

        return jt.query(sql, new BeanPropertyRowMapper<>(ReadertypeDo.class));

    }


    /**
     * 获取读者的详细信息
     * @return
     */
    public ReaderDetailsVo getReaderInfoById(ReaderDetailsVo vo) {
        String sql = "select r.id,r.paperNO,r.name readerName,r.email,r.phone,  rt.name readerType,a.`name` adminName from reader r,readertype rt,admin a where r.fk_readertype=rt.id and a.id=r.fk_admin and r.id=?";

        return jt.queryForObject(sql,new BeanPropertyRowMapper<>(ReaderDetailsVo.class),vo.getId());

    }
 /**
     * 通过id获取读者的详细信息
     * @return
     */
    public ReaderDo getReaderById(ReaderDo readerDo) {
        String sql = "select * from reader where id=?";

        return jt.queryForObject(sql,new BeanPropertyRowMapper<>(ReaderDo.class),readerDo.getId());

    }

    /**
     * 修改读者个人信息
     * @param readerDo
     * @return
     */
    public Integer updateReaderInfo(ReaderDo readerDo) {
        String sql = "update reader set name=?,phone=?,email=?,fk_readertype=? where id=?";
        return jt.update(sql, readerDo.getName(), readerDo.getPhone(), readerDo.getEmail(), readerDo.getFkReadertype(), readerDo.getId());
    }

    /**
     * 添加读者
     * @param readerDo
     * @return
     * INSERT INTO `librarysystem`.`reader` (`id`, `name`, `paperNO`, `phone`, `email`, `createTime`,
     * `fk_readertype`, `fk_admin`) VALUES ('1', '4QrcOUm6Wau+VuBX8g+IPg==', '学生时亮', '21706032032', '18511898646', '111@qq.com',
     * '2018-03-21 23:26:40', '2', '1');
     */
    public Integer addReader(ReaderDo readerDo) {
        String sql = "insert into reader values(null,?,?,?,?,?,?,?,?)";
        Object[] params = {readerDo.getPassword(), readerDo.getName(), readerDo.getPaperNo(),readerDo.getPhone(), readerDo.getEmail(), readerDo.getCreateTime(), readerDo.getFkReadertype(), readerDo.getFkAdmin()};
        return jt.update(sql, params);
    }

    /**
     * 通过证件号码来查询读者信息
     * @param readerDo
     * @return
     */
    public ReaderDo getReaderByPaperNo(ReaderDo readerDo) {
        try {
            String sql = "select * from reader where paperNO=?";
            return jt.queryForObject(sql, new BeanPropertyRowMapper<>(ReaderDo.class), readerDo.getPaperNo());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询读者的信息封装到list集合中，用于导出
     */
    public List<ReaderDetailsVo> exportReader() {
        String sql = "select r.paperNO,r.name readerName,r.email,r.phone,  rt.name readerType,a.`name` adminName from reader r,readertype rt,admin a where r.fk_readertype=rt.id and a.id=r.fk_admin";
        return jt.query(sql, new BeanPropertyRowMapper<>(ReaderDetailsVo.class));
    }

    public int[] batchAddBook(List<Object[]> params) {
        String sql = "insert into reader values(null,?,?,?,?,?,?,?,?)";
        return jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, (String) params.get(i)[0]);
                preparedStatement.setString(2, (String) params.get(i)[1]);
                preparedStatement.setString(3, (String) params.get(i)[2]);
                preparedStatement.setString(4, (String) params.get(i)[3]);
                preparedStatement.setString(5, (String) params.get(i)[4]);
                preparedStatement.setDate(6, (Date) params.get(i)[5]);
                preparedStatement.setInt(7, (Integer) params.get(i)[6]);
                preparedStatement.setInt(8, (Integer) params.get(i)[7]);
            }

            @Override
            public int getBatchSize() {
                return params.size();
            }
        });
    }
}
