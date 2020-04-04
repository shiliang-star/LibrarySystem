package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BackinfoDo;
import com.shiliang.domain.query.BackListQuery;
import com.shiliang.domain.vo.BackDetailsVO;
import com.shiliang.domain.vo.BackListVO;
import com.shiliang.service.BackManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-04-04 17:09
 * @description 借阅信息（读者端）
 */
@Controller
public class BorrowInfoController {


    /**
     * 分页查询图书借阅信息
     *
     * @return
     */
    @RequestMapping("/reader/borrowInfoController_findMyBorrowInfoByPage")
    public ModelAndView findMyBorrowInfoByPage(BackListQuery query) {
        BackManageService service = new BackManageService();
        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage()) + "");
        PageBean<BackListVO> pb = service.pageSearch(query, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("query", query);
        modelAndView.addParam("pb", pb);
        modelAndView.setViewName("borrow.jsp");
        return modelAndView;
    }

    /**
     * 获取图书归还信息
     */
    @RequestMapping("/reader/borrowInfoController_getBackInfo")
    public void getBackInfo(int id) throws IOException {
        BackManageService service = new BackManageService();
        BackDetailsVO backInfoById = service.getBackInfoById(new BackinfoDo(id));
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(backInfoById).toString());

    }
}
