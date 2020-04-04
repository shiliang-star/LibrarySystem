package com.shiliang.controller;

import com.shiliang.dao.BorrowManageDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.OverdueinfoDo;
import com.shiliang.domain.query.ForfeitQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.OverDueDetailsVO;
import com.shiliang.domain.vo.OverDueManageListVO;
import com.shiliang.service.OverDueManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-28 22:26
 * @description 逾期处理
 */
@Controller
public class OverDueManageController {
    OverDueManageService service=new OverDueManageService();

    @RequestMapping("/admin/overDueManageController_list")
    public ModelAndView list(ForfeitQuery query) {

        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage())+"");
        PageBean<OverDueManageListVO> pb = service.pageSearch(query, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("pb", pb);
        modelAndView.addParam("query", query);
        modelAndView.setViewName("/admin/overdueManage.jsp");
        return modelAndView;
    }

    /**
     * 获取图书逾期详细信息
     */
    @RequestMapping("/admin/overDueManageController_getForfeitInfo")
    public void getForfeitInfo(int borrowId) throws IOException {
        OverDueDetailsVO overDueDetailsVO = service.getgetForfeitInfoById(new OverdueinfoDo(borrowId));
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(overDueDetailsVO).toString());

    }

    /**
     * 支付罚金得功能
     */
    @RequestMapping("/admin/overDueManageController_payForfeit")
    public void payForfeit(int borrowId) throws IOException {
        AdminDetailsVo admin = (AdminDetailsVo) RequestContextHolder.getSession().getAttribute("admin");
        int state = service.payForfeit(new OverdueinfoDo(borrowId), admin);
        RequestContextHolder.getResponse().getWriter().print(state);
    }
}
