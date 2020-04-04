package com.shiliang.service;

import com.shiliang.dao.ReaderTypeManageDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.ReadertypeDo;

/**
 * @author SL
 * @create 2020-03-17 20:14
 * @description 读者类型管理服务层
 */
public class ReaderTypeManageService {

    ReaderTypeManageDao dao=new ReaderTypeManageDao();



    public PageBean<ReadertypeDo> pageSearch(int currentPage, int pageSize) {

        return dao.pageSearch(currentPage, pageSize);

    }


    public Integer updateReaderTypeById(ReadertypeDo readertypeDo) {

        try {
            return dao.updateReaderTypeById(readertypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReadertypeDo getReaderTypeByID(ReadertypeDo readertypeDo) {
        try {
            return dao.getReaderTypeByID(readertypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addReaderType(ReadertypeDo readertypeDo) {
        try {
            return dao.addReaderType(readertypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ReadertypeDo getReaderTypeByName(ReadertypeDo readertypeDo) {
        try {
            return dao.getReaderTypeByName(readertypeDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
