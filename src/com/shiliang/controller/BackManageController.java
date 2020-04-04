package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BackinfoDo;
import com.shiliang.domain.query.BackListQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.BackDetailsVO;
import com.shiliang.domain.vo.BackListVO;
import com.shiliang.service.BackManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-27 20:29
 * @description 图书归还
 */
@Controller
public class BackManageController {
    BackManageService service = new BackManageService();

    /**
     * 分页查询和条件查询图书借阅信息
     *
     * @return
     */
    @RequestMapping("/admin/backManageController_list")
    public ModelAndView list(BackListQuery query) {

        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage()) + "");
        PageBean<BackListVO> pb = service.pageSearch(query, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("query", query);
        modelAndView.addParam("pb", pb);
        modelAndView.setViewName("/admin/backManage.jsp");

        return modelAndView;
    }

    /**
     * 获取图书归还信息
     */
    @RequestMapping("/admin/backManageController_getBackInfo")
    public void getBackInfo(int id) throws IOException {
        BackDetailsVO backInfoById = service.getBackInfoById(new BackinfoDo(id));
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(backInfoById).toString());

    }

    /**
     * 归还图书
     */
    @RequestMapping("/admin/backManageController_backBook")
    public void backBook(int borrowId) throws IOException {
        AdminDetailsVo admin = (AdminDetailsVo) RequestContextHolder.getSession().getAttribute("admin");
        int resultCode = service.backBook(borrowId, admin);
        RequestContextHolder.getResponse().getWriter().print(resultCode);
    }

}
