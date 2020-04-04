package com.shiliang.service;

import com.shiliang.dao.BookTypeManageDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookTypeQuery;

/**
 * @author SL
 * @create 2020-03-11 15:52
 * @description 图书类型管理服务层
 */
public class BookTypeManageService {

    BookTypeManageDao dao = new BookTypeManageDao();

    /**
     * 分页查询图书分类
     *
     * @param pageSize
     * @return
     */
    public PageBean<BooktypeDo> pageSearch(BookTypeQuery query, int pageSize) {
        return dao.pageSearch(query, pageSize);
    }

    /**
     * 通过id来删除图书分类类型
     *
     * @param booktypeDo
     * @return
     */
    public Integer deleteBookTypeById(BooktypeDo booktypeDo) {
        try {
            return dao.deleteBookTypeById(booktypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BooktypeDo getBookTypeById(BooktypeDo booktypeDo) {
        try {
            return dao.getBookTypeById(booktypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer updateBookTypeInfo(BooktypeDo booktypeDo) {
        try {
            return dao.updateBookTypeInfo(booktypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BooktypeDo getBooktypeByName(BooktypeDo booktypeDo) {
        try {
            return dao.getBooktypeByName(booktypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer addBookType(BooktypeDo booktypeDo) {
        try {
            return dao.addBookType(booktypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
