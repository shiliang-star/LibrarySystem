package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.OverdueinfoDo;
import com.shiliang.domain.query.ForfeitQuery;
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
 * @create 2020-04-04 18:49
 * @description 逾期信息（读者端）
 */
@Controller
public class OverDueInfoController {


    /**
     * 分页查询逾期信息
     * @return
     */
    @RequestMapping("/reader/overDueInfoController_findMyForfeitInfoByPage")
    public ModelAndView findMyForfeitInfoByPage(ForfeitQuery query) {
        OverDueManageService service=new OverDueManageService();
        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage())+"");
        PageBean<OverDueManageListVO> pb = service.pageSearch(query, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("pb", pb);
        modelAndView.addParam("query", query);
        modelAndView.setViewName("overdue.jsp");
        return modelAndView;
    }
    /**
     * 获取图书逾期详细信息
     */
    @RequestMapping("/reader/overDueInfoController_getForfeitInfo")
    public void getForfeitInfo(int borrowId) throws IOException {
        OverDueManageService service=new OverDueManageService();
        OverDueDetailsVO overDueDetailsVO = service.getgetForfeitInfoById(new OverdueinfoDo(borrowId));
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(overDueDetailsVO).toString());

    }



}
