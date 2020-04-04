package com.shiliang.service;

import com.shiliang.dao.*;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BorrowinfoDo;
import com.shiliang.domain.data.OverdueinfoDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.domain.query.BorrowSearchQuery;
import com.shiliang.domain.vo.BorrowSearchListVO;

import java.util.Calendar;
import java.util.Date;

/**
 * @author SL
 * @create 2020-03-28 16:23
 * @description 图书借阅查询业务逻辑层
 */
public class BorrowSearchService {
    BorrowSearchDao borrowSearchDao = new BorrowSearchDao();
    BorrowManageDao borrowManageDao=new BorrowManageDao();
    ReaderTypeManageDao readerTypeManageDao = new ReaderTypeManageDao();
    ReaderManageDao readerManageDao=new ReaderManageDao();
    OverDueInfoDao overDueInfoDao=new OverDueInfoDao();

    public PageBean<BorrowSearchListVO> pageSearch(BorrowSearchQuery query, int pageSize) {

        return borrowSearchDao.pageSearch(query, pageSize);
    }

    /**
     * 图书续借功能
     *        只能续借一次
     *        已经还的书无法续借
     * @param=
     * @return
     */
    public int renewBook(BorrowinfoDo borrowinfoDo) {
      //获取图书的借阅信息
        BorrowinfoDo bInfo = borrowManageDao.getBorrowInfoById(borrowinfoDo);
        if (bInfo != null) {
            //获取图书的借阅状态
            Integer state = bInfo.getState();
            //获取到图书的借阅状态，如果图书为续借状态，则不可续借
            if (state == 3 || state == 4) {
                return -2;
            }
            //如果图书当前状态为归还状态，不可续借
            if (state == 2 || state == 5) {
                return -1;
            }

            //获取读者的可续借天数
            ReaderDo readerById = readerManageDao.getReaderById(new ReaderDo(bInfo.getFkReader()));
            ReadertypeDo readerTypeByID = readerTypeManageDao.getReaderTypeByID(new ReadertypeDo(readerById.getFkReadertype()));
            Integer renewDays = readerTypeByID.getRenewDays();

            System.out.println("借阅时间："+bInfo.getBorrowDate().toLocaleString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bInfo.getEndDate());
            calendar.add(Calendar.DAY_OF_MONTH, renewDays);
            Date endDate = calendar.getTime();
            System.out.println("续借前的截至日期"+bInfo.getEndDate().toLocaleString());
            System.out.println("续借后的截至时间："+endDate.toLocaleString());

            long endDateTime = endDate.getTime();
            long currentTime = System.currentTimeMillis();
            //如果当前时间超过最大续借日期，则超续借期不可续借
            if (currentTime > endDateTime) {
                return -3;
            }

            //如果当前图书借阅状态为逾期未归还，则要清楚逾期信息和罚金，并设置状态为续借未归还
            if (state == 1) {
                overDueInfoDao.deleteOverDueInfoById(new OverdueinfoDo(bInfo.getId()));
                bInfo.setOverday(0);
            }

            bInfo.setState(3);
            bInfo.setEndDate(new java.sql.Date(endDate.getTime()));
            int count = borrowManageDao.updateBorrowInfos(bInfo);
            if (count > 0) {
                return 1;
            }

        }

        return 0;
    }
}
