package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookManageQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
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
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-12 22:36
 * @description 图书管理
 */
@Controller
public class BookManageController extends BaseController {
    BookManageService service = new BookManageService();


    @RequestMapping("/admin/bookManageController_list")
    public ModelAndView list(HttpServletRequest request, BookManageQuery query) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {

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
        modelAndView.setViewName("bookManage.jsp");
        return modelAndView;
    }




    /**
     * 获取图书管理分类下拉框
     */
    @RequestMapping("/admin/bookManageController_getAllBookTypes")
    public void getAllBookTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<BooktypeDo> allBookTypes = service.getAllBookTypes();
        JSONArray js = JSONArray.fromObject(allBookTypes);

        response.getWriter().print(js);

    }


    @RequestMapping("/admin/bookManageController_addBook")
    public void addBook(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {

        AdminDetailsVo vo = (AdminDetailsVo) request.getSession().getAttribute("admin");

        BookDo book = new BookDo();
        book.setPutdate(new java.sql.Date(System.currentTimeMillis()));
        book.setCurrentNum(Integer.parseInt(request.getParameter("num")));
        book.setFk_booktype(Integer.parseInt(request.getParameter("bookTypeId")));
        book.setFk_admin(vo.getId());


        BeanUtils.copyProperties(book, request.getParameterMap());

        //根据ISBN判断图书是否重复
        BookDo bookByISBN = service.getBookByISBN(book);

        //设置返回码
        int resultCode = -1;//添加失败

        if (bookByISBN == null) {
            //添加图书
            Integer count = service.addBook(book);
            if (count > 0) {
                resultCode = 1;//添加成功
            }
        } else {
            resultCode = -2;//添加失败，ISBN重复
        }

        response.getWriter().print(resultCode);

//        System.out.println(book);

    }


    /**
     * 获取图书详细信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/admin/bookManageController_getBookInfo")
    public void getBookInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {


        BookDo bookDo = new BookDo();
        String id = request.getParameter("id");

        bookDo.setId(Integer.parseInt(id));


        BookDetailsVo bookInfo = service.getBookInfoById(bookDo);

        JSONObject js = JSONObject.fromObject(bookInfo);

        response.getWriter().print(js);


    }

    /**
     * 修改图书信息
     * @param request
     * @param response
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/admin/bookManageController_updateBook")
    public void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        BookDo book=new BookDo();
        book.setFk_booktype(Integer.parseInt(request.getParameter("bookTypeId")));

        BeanUtils.copyProperties(book,request.getParameterMap());

//        System.out.println(book);

        Integer count = service.updateBook(book);

        int resultCode = -1;
        if (count > 0) {
            resultCode = 1;
        }
        response.getWriter().print(resultCode);
    }

    /**
     * 添加图书数量
     */
    @RequestMapping("/admin/bookManageController_addBookNum")
    public void addBookNum(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BookDo book=new BookDo();
        book.setId(Integer.parseInt(request.getParameter("id")));
        book.setNum(Integer.parseInt(request.getParameter("addNum")));

        //添加图书数量
        Integer count = service.addBookNum(book);

        int resultCode = -1;

        if (count > 0) {
            resultCode = 1;
        }

        response.getWriter().print(resultCode);
    }

    /**
     * 导出图书
     */
    @RequestMapping("/admin/bookManageController_exportBook")
    public void exportBook(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String fileName = service.exportBook();

        response.getWriter().print(fileName);

    }

    /**
     * 批量添加图书：
     *   获取参数：获取传入的文件名
     *   调用service来进行批量添加
     */
    @RequestMapping("/admin/bookManageController_batchAddBook")
    public void batchAddBook(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName = request.getParameter("fileName");
        AdminDetailsVo admin = (AdminDetailsVo) request.getSession().getAttribute("admin");

        JSONObject jsonObject = service.batchAddBook(fileName, admin);

        response.getWriter().print(jsonObject.toString());

    }

    /**
     * 删除图书功能实现
     */
    @RequestMapping("/admin/bookManageController_deleteBook")
    public void deleteBook(int id) throws IOException {
        int state=service.deleteBook(new BookDo(id));
        RequestContextHolder.getResponse().getWriter().print(state);
    }
}
