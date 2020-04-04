package com.shiliang;

import com.shiliang.service.BorrowManageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author SL
 * @create 2020-03-29 17:36
 * @description 定时检阅图书信息 判断是否有逾期图书信息
 */
public class CheckBorrowInfo implements Job {
    BorrowManageService borrowManageService=new BorrowManageService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean checkBorrowInfo = borrowManageService.checkBorrowInfo();
        if (checkBorrowInfo) {
            System.out.println("执行定时检查任务");
        }
    }
}
