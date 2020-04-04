package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookManageQuery;
import com.shiliang.domain.vo.BookDetailsVo;
import com.shiliang.domain.vo.BookManageListVo;
import com.shiliang.service.BookManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author SL
 * @create 2020-04-03 16:41
 * @description 图书查询（读者端）
 */
@Controller
public class BookSearchController {
    BookManageService service = new BookManageService();

    @RequestMapping("/reader/bookSearchController_findBookByPage")
    public ModelAndView findBookByPage(HttpServletRequest request, BookManageQuery query) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {

//        BookManageQuery query = new BookManageQuery();
        String currentPage = request.getParameter("currentPage");
        query.setCurrentPage(DataUtils.getPageCode(currentPage) + "");

//        BeanUtils.copyProperties(query, request.getParameterMap());

        int pageSize = 5;

        PageBean<BookManageListVo> pb = service.pageSearch(query, pageSize);

//        request.setAttribute("pb", pb);
//        request.setAttribute("query", query);
//        request.getRequestDispatcher("bookManage.jsp").forward(request, response);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("pb", pb);
        modelAndView.addParam("query", query);
        modelAndView.setViewName("book.jsp");
        return modelAndView;
    }


    /**
     * 获取图书管理分类下拉框
     */
    @RequestMapping("/reader/bookSearchController_getAllBookTypes")
    public void getAllBookTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<BooktypeDo> allBookTypes = service.getAllBookTypes();
        JSONArray js = JSONArray.fromObject(allBookTypes);

        response.getWriter().print(js);

    }

    /**
     * 获取图书信息
     */
    @RequestMapping("/reader/bookSearchController_getBookInfo")
    public void getBookInfo(BookDo bookDo) throws IOException {
        BookManageService service = new BookManageService();
        BookDetailsVo bookInfoById = service.getBookInfoById(bookDo);
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(bookInfoById));

    }
}
