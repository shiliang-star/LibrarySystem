package com.shiliang.service;

import com.shiliang.dao.BorrowManageDao;
import com.shiliang.dao.OverDueInfoDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BorrowinfoDo;
import com.shiliang.domain.data.OverdueinfoDo;
import com.shiliang.domain.query.ForfeitQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.OverDueDetailsVO;
import com.shiliang.domain.vo.OverDueManageListVO;

/**
 * @author SL
 * @create 2020-03-28 22:28
 * @description 逾期处理业务逻辑层
 */
public class OverDueManageService {
    OverDueInfoDao overDueInfoDao=new OverDueInfoDao();
    BorrowManageDao borrowManageDao=new BorrowManageDao();


    /**
     * 实现逾期信息列表
     * @param query
     * @param pageSize
     * @return
     */
    public PageBean<OverDueManageListVO> pageSearch(ForfeitQuery query, int pageSize) {
        try {
            return overDueInfoDao.pageSearch(query, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过借阅编号获取图书详细逾期信息
     * @param overdueinfoDo
     * @return
     */
    public OverDueDetailsVO getgetForfeitInfoById(OverdueinfoDo overdueinfoDo) {
        try {
            return overDueInfoDao.getgetForfeitInfoById(overdueinfoDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付罚款
     */
    public int payForfeit(OverdueinfoDo overdueinfoDo, AdminDetailsVo admin) {
        //得到借阅状态
        BorrowinfoDo bIf = borrowManageDao.getBorrowInfoById(new BorrowinfoDo(overdueinfoDo.getId()));
        if (bIf != null) {
            Integer state = bIf.getState();
            //如果读者当前借阅状态为未归还（逾期未归还、续借逾期未归还）
            if (state == 1 || state == 4) {
                return -1;
            }

            OverdueinfoDo overdue = overDueInfoDao.getOverDueInfoById(overdueinfoDo);
            //得到当前支付状态
            Integer isPay = overdue.getIsPay();
            if (isPay == 1) {
                return -2;
            }
            overdue.setFkAdmin(admin.getId());
            overdue.setIsPay(1);
            //更新罚款信息
            overDueInfoDao.updateOverDueInfo(overdue);
            return 1;
        }


        return 0;
    }
}
