package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BorrowinfoDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.BorrowDetailsVO;
import com.shiliang.domain.vo.BorrowInfoVO;
import com.shiliang.service.BorrowManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;
import sun.security.provider.MD5;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-26 13:28
 * @description 图书借阅管理
 */
@Controller
public class BorrowManageController {
    BorrowManageService service = new BorrowManageService();


    /**
     * 分页查询图书借阅信息
     *
     * @param currentPage
     * @return
     */
    @RequestMapping("/admin/borrowManageController_list")
    public ModelAndView list(String currentPage) {

        int pageCode = DataUtils.getPageCode(currentPage);
        PageBean<BorrowInfoVO> pb = service.pageSearch(pageCode, 5);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("pb", pb);
        modelAndView.setViewName("/admin/borrowManage.jsp");
        return modelAndView;
    }



    /**
     * 通过图书借阅编号获取图书详细借阅信息
     */
    @RequestMapping("/admin/borrowManageController_getBorrowInfoById")
    public void getBorrowInfoById(int id) throws IOException {

        AdminDetailsVo admin = (AdminDetailsVo) RequestContextHolder.getSession().getAttribute("admin");
        BorrowinfoDo borrowinfoDo = new BorrowinfoDo(id);
        BorrowDetailsVO borrowInfoById = service.getBorrowInfoById(borrowinfoDo, admin);
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(borrowInfoById).toString());

    }

    /**
     * 借阅图书功能实现
     */
    @RequestMapping("/admin/borrowManageController_borrowBook")
    public void borrowBook(ReaderDo readerDo, String ISBN) throws IOException {
        AdminDetailsVo admin = (AdminDetailsVo) RequestContextHolder.getSession().getAttribute("admin");
        readerDo.setPassword(Md5Utils.md5(readerDo.getPassword()));

        JSONObject jsonObject=service.addBorrow(readerDo,ISBN,admin);
        //用于回显
        RequestContextHolder.getRequest().setAttribute("readerDo",readerDo);
        RequestContextHolder.getRequest().setAttribute("ISBN",ISBN);
        RequestContextHolder.getResponse().getWriter().print(jsonObject.toString());

    }
}
