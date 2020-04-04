package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookTypeQuery;
import com.shiliang.service.BookTypeManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author SL
 * @create 2020-03-11 15:50
 * @description 图书类型的管理
 */
@Controller
public class BookTypeManageController extends BaseController {

    BookTypeManageService service = new BookTypeManageService();

    @RequestMapping("/admin/bookTypeManageController_list")
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {


        BookTypeQuery query = new BookTypeQuery();

        String currentPage = request.getParameter("currentPage");
        String typeName = request.getParameter("typeName");
        query.setCurrentPage(currentPage);
        query.setTypeName(typeName);


//        BeanUtils.copyProperties(query,request.getParameterMap());

        int pageSize = 5;

        PageBean<BooktypeDo> pb = service.pageSearch(query, pageSize);

        request.setAttribute("pb", pb);
        request.getSession().setAttribute("query", query);
        request.getRequestDispatcher("bookTypeManage.jsp").forward(request, response);
    }

    @RequestMapping("/admin/bookTypeManageController_deleteBookType")
    public void deleteBookType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        BooktypeDo booktypeDo = new BooktypeDo();
        booktypeDo.setId(Integer.parseInt(id));

        Integer count = service.deleteBookTypeById(booktypeDo);
        int resultcode = -1;//删除失败
        if (count > 0) {
            resultcode = 1;//删除成功
        }
        response.getWriter().print(resultcode);

    }

    @RequestMapping("/admin/bookTypeManageController_getBookTypeById")
    public void getBookTypeById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        BooktypeDo booktypeDo = new BooktypeDo();
        booktypeDo.setId(Integer.parseInt(id));


        BooktypeDo booktypeDo1 = service.getBookTypeById(booktypeDo);
        JSONObject jsonObject = JSONObject.fromObject(booktypeDo1);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping("/admin/bookTypeManageController_updateBookTypeInfo")
    public void updateBookTypeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BooktypeDo booktypeDo = new BooktypeDo();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        booktypeDo.setId(Integer.parseInt(id));
        booktypeDo.setName(name);

        Integer count = service.updateBookTypeInfo(booktypeDo);

        int resultCode = -1;//修改失败
        if (count > 0) {
            resultCode = 1;//修改成功
        }
        response.getWriter().print(resultCode);

    }

    /**
     * 首先根据AJAX请求过来的图书分类信息名称来查询数据库，看看是否存在相同的图书分类信息
     *        查询到该图书分类信息
     *                        返回该图书信息分类已存在
     *        没有查询到该图书分类信息
     *                          进行添加到数据库
     *                          返回添加成功
     * @param request
     * @param response
     */
    @RequestMapping("/admin/bookTypeManageController_addBookType")
    public void addBookType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        BooktypeDo booktypeDo=new BooktypeDo();
        booktypeDo.setName(name);

        BooktypeDo booktypeByName = service.getBooktypeByName(booktypeDo);

        int resultCode=-1;//添加失败
        if (booktypeByName != null) {
            resultCode = -2;//图书信息分类已存在
        }else {

            Integer count = service.addBookType(booktypeDo);
            if (count > 0) {
                resultCode = 1;//添加成功
            }
        }
        response.getWriter().print(resultCode);
    }
}
