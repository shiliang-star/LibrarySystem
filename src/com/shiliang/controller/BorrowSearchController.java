package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BorrowinfoDo;
import com.shiliang.domain.query.BorrowSearchQuery;
import com.shiliang.domain.vo.BorrowSearchListVO;
import com.shiliang.service.BorrowSearchService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-28 15:56
 * @description 借阅查询
 */
@Controller
public class BorrowSearchController {

    BorrowSearchService searchService=new BorrowSearchService();

    @RequestMapping("/admin/borrowSearchController_list")
    public ModelAndView list(BorrowSearchQuery query) {
        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage())+"");
        PageBean<BorrowSearchListVO> pb = searchService.pageSearch(query, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("pb", pb);
        modelAndView.addParam("query", query);
        modelAndView.setViewName("/admin/borrowSearch.jsp");
        return modelAndView;

    }

    /**
     * 续借图书功能
     */
    @RequestMapping("/admin/borrowSearchController_renewBook")
    public void renewBook(int borrowId) throws IOException {
        BorrowinfoDo borrowinfoDo=new BorrowinfoDo(borrowId);
        int state = searchService.renewBook(borrowinfoDo);
        RequestContextHolder.getResponse().getWriter().print(state);
    }
}
