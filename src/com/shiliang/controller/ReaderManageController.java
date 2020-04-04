package com.shiliang.controller;

import com.shiliang.dao.AdminDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.domain.query.ReaderQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.ReaderDetailsVo;
import com.shiliang.domain.vo.ReaderManageListVo;
import com.shiliang.service.ReaderManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
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
import java.sql.Date;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-18 18:01
 * @description 对读者进行管理操作
 */
@Controller
public class ReaderManageController extends BaseController {

    ReaderManageService service=new ReaderManageService();


    /**
     * 实现查询数据(分页)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/admin/readerManageController_list")
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {


        ReaderQuery query = new ReaderQuery();
        query.setCurrentPage(DataUtils.getPageCode(request.getParameter("currentPage")) + "");

        BeanUtils.copyProperties(query, request.getParameterMap());

        int pageSize = 5;

        PageBean<ReaderManageListVo> pb = service.pageSearch(query, pageSize);


        request.setAttribute("pb", pb);
        request.setAttribute("query", query);

        request.getRequestDispatcher("/admin/readerManage.jsp").forward(request, response);
    }


    /**
     * 获取所有的读者类型
     * @param request
     * @param response
     */
    @RequestMapping("/admin/readerManageController_getAllReaderTypes")
    public void getAllReaderTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {


        List<ReadertypeDo>  readerTypes=service.getAllReaderTypes();

        JSONArray jsonArray = JSONArray.fromObject(readerTypes);

        response.getWriter().print(jsonArray);


    }

    /**
     * 获取读者的详细信息
     */
    @RequestMapping("/admin/readerManageController_getReaderInfo")
    public void getReaderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");

        ReaderDetailsVo vo=new ReaderDetailsVo();
        vo.setId(Integer.parseInt(id));


        ReaderDetailsVo readerInfo = service.getReaderInfoById(vo);


        JSONObject jsonObject = JSONObject.fromObject(readerInfo);


        response.getWriter().print(jsonObject);

    }

    /**
     *修改读者个人信息
     */
    @RequestMapping("/admin/readerManageController_updateReader")
    public void updateReader(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {

        ReaderDo readerDo = new ReaderDo();

        String readerTypeId = request.getParameter("readerTypeId");

        BeanUtils.copyProperties(readerDo, request.getParameterMap());

        readerDo.setFkReadertype(Integer.parseInt(readerTypeId));

        Integer count = service.updateReaderInfo(readerDo);

        int resultCode = -1;

        if (count > 0) {
            resultCode = 1;
        }


        response.getWriter().print(resultCode);


    }

    /**
     * 添加读者
     */
    @RequestMapping("/admin/readerManageController_addReader")
    public void addReader(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {

        AdminDetailsVo admin = (AdminDetailsVo) request.getSession().getAttribute("admin");


        ReaderDo readerDo=new ReaderDo();

        String readerTypeId = request.getParameter("readerTypeId");

        BeanUtils.copyProperties(readerDo, request.getParameterMap());

        readerDo.setFkAdmin(admin.getId());
        readerDo.setCreateTime(new Date(System.currentTimeMillis()));

        //设置读者初始密码为 123456
        String password = Md5Utils.md5("123456");
        readerDo.setPassword(password);
        readerDo.setFkReadertype(Integer.parseInt(readerTypeId));

        ReaderDo readerByPaperNo = service.getReaderByPaperNo(readerDo);


        int resultCode = -1;

        if (readerByPaperNo != null) {
            resultCode = -2;//证件号码已存在
        }else {
            Integer count = service.addReader(readerDo);

            if (count > 0) {
                resultCode = 1;
            }

        }



        response.getWriter().print(resultCode);

    }
    /**
     * 导出读者信息
     */
    @RequestMapping("/admin/readerManageController_exportReader")
    public void exportReader(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String fileName = service.exportReader();

        response.getWriter().print(fileName);


    }


    /**
     * 批量添加读者
     *
     */
    @RequestMapping("/admin/readerManageController_batchAddReader")
    public void batchAddReader(String fileName) throws IOException {


        AdminDetailsVo admin = (AdminDetailsVo) RequestContextHolder.getSession().getAttribute("admin");


        JSONObject jsonObject = service.batchAddBook(fileName, admin);


        RequestContextHolder.getResponse().getWriter().print(jsonObject);

    }
}
