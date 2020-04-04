package com.shiliang.service;

import com.shiliang.dao.ReaderDao;
import com.shiliang.domain.data.ReaderDo;

/**
 * @author SL
 * @create 2020-03-09 23:37
 * @description 读者服务层
 */
public class ReaderService {

    private ReaderDao dao=new ReaderDao();

    /**
     * 通过读者证件号查询读者信息
     * @param readerDo
     * @return
     */
    public ReaderDo findReaderByPaperNo(ReaderDo readerDo) {
        return dao.findReaderByPaperNo(readerDo);
    }

    /**
     * 更新读者个人信息(电话 邮箱 )
     */
    public int updateReaderInfo(ReaderDo readerDo) {
        try {
            return dao.updateReaderInfo(readerDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int updateReaderPassword(ReaderDo readerDo) {
        try {
            return dao.updateReaderPassword(readerDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
